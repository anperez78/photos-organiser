import React, { Component } from 'react'
import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';
import Photo from '../component/Photo'
import Viewer from 'react-viewer';
import 'react-viewer/dist/index.css';



class PhotoList extends Component {

    state = {
        photos: [],
        searchString: '',

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

    render() {
        return (
            <div>
                { this.state.photos ? (
                    <div>
                        <TextField style={{padding: 24}}
                                   id="searchInput"
                                   placeholder="Search for Photos"
                                   margin="normal"
                                   onChange={this.onSearchInputChange}
                                   onKeyDown={this.onSearchInputKeyDown}
                        />
                        <Grid container spacing={24} style={{padding: 24}}>
                            { this.state.photos.map(currentPhoto => (
                                <Grid item xs={12} sm={6} lg={4} xl={3}>
                                    <Photo photo={currentPhoto} />
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