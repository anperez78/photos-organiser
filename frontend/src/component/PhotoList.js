import React, { Component } from 'react'
import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';
import Photo from '../component/Photo'

class PhotoList extends Component {

    state = {
        photos: [],
        searchString: ''
    }

    constructor() {
        super()
        this.getAllPhotos()
    }

    getAllPhotos = () => {
        fetch('/api/photo/all')
            .then(response => response.json())
            .then(data => {
                this.setState({photos: data})
                console.log(this.state.photos)

            })
            .catch((error) => {
                console.log("Error occurred while fetching photos")
                console.error(error)
            })
    };

    getTaggedPhotos = () => {
        console.log ('my searchString: ' + this.state.searchString);
        fetch('/api/photo?tags=' + this.state.searchString)
            .then(response => response.json())
            .then(data => {
                console.log('data: ', data)
                this.setState({photos: data})
            })
            .catch((error) => {
                console.log("Error occurred while fetching photos")
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
                    console.log('getTaggedPhotos')
                    this.getTaggedPhotos()
                }
                else {
                    console.log('getAllPhotos')
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