import React, { Component } from 'react'
import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';
import Photo from '../component/Photo'
import Button from '@material-ui/core/Button';


class PhotoList extends Component {

    state = {
        photos: [],
        photosRefs: [],
        searchString: '',
        currentOpenPhotoIdx: 0,

    }

    constructor() {
        super()
        this.getAllPhotos()
    }

    getAllPhotos = () => {
        fetch('/api/media/all')
            .then(response => response.json())
            .then(data => {
                this.setState({photos: data});
                this.props.callbackFromParent(data);
            })
            .catch((error) => {
                console.log("Error occurred while fetching photos [/api/media/all]")
                console.error(error)
            })
    };

    getTaggedPhotos = (searchString) => {
        console.log ('my searchString: ' + searchString);
        const convertedSearchString = searchString.replace(/\s+/g, '+')
        console.log ('my searchString converted: ' + convertedSearchString);

        fetch('/api/photo?tags=' + convertedSearchString)
            .then(response => response.json())
            .then(data => {
                this.setState({photos: data})
                this.props.callbackFromParent(data);
            })
            .catch((error) => {
                console.log("Error occurred while fetching photos [/api/photo?tags=xxx]")
                console.error(error)
            })
    };

    onSearchInputChange = (event) => {
        console.log("Search changed ..." + event.target.value)
        if (event.target.value) {
            this.setState({searchString: event.target.value})
        } else {
            this.setState({searchString: ''})
        }
    };

    onSearchInputKeyDown = (event) => {
        console.log("event.key ..." + event.key)
        switch (event.key) {
            case 'Enter':
                if (this.state.searchString.length > 0) {
                    this.getTaggedPhotos(this.state.searchString)
                }
                else {
                    this.getAllPhotos()
                }
                break
            default: break
        }
    };

    hidePhoto = () => {
        this.state.photosRefs[this.state.currentOpenPhotoIdx].handleClose();
    }

    showPhoto = () => {
        const mediaId = this.state.photos[this.state.currentOpenPhotoIdx].mediaId;
        const photoExifUrl = '/api/photo/' + mediaId + '/exif';
        this.state.photosRefs[this.state.currentOpenPhotoIdx].handleOpen(photoExifUrl);
    }

    handleKeyDown = (event) => {

        if(event.keyCode == 37){
            console.log('left arrow....');
            this.hidePhoto();
            if (this.state.currentOpenPhotoIdx > 0) {
                this.state.currentOpenPhotoIdx--;
            }
            this.showPhoto();


        }
        if(event.keyCode == 39){
            console.log('right arrow....');
            this.hidePhoto();
            if (this.state.currentOpenPhotoIdx < this.state.photos.length) {
                this.state.currentOpenPhotoIdx++;
            }
            this.showPhoto();

        }
    };

    playPhotos = () => {
        console.log('play all photos ---- ');
        this.setState({currentOpenPhotoIdx: 0});
        this.showPhoto();
    };

    render() {
        return (
            <div>
                { this.state.photos ? (
                    <div id="inner" tabindex="0" onKeyDown={this.handleKeyDown}>
                        <TextField style={{padding: 24}}
                                   id="searchInput"
                                   placeholder="Search for Photos"
                                   margin="normal"
                                   onChange={this.onSearchInputChange}
                                   onKeyDown={this.onSearchInputKeyDown}
                        />
                        <Button onClick={() => { this.playPhotos() } }>
                            Play
                        </Button>
                        <Grid container spacing={8} style={{padding: 24}}>
                            { this.state.photos.map(currentPhoto => (
                                <Grid item xs={12} sm={6} lg={4} xl={3}>
                                    <Photo photo={currentPhoto} onRef={ref => (this.state.photosRefs.push (ref))}/>
                                </Grid>
                            ))}
                        </Grid>
                    </div>
                ) : "No photos found" }
            </div>
        )
    }
}
export default PhotoList;