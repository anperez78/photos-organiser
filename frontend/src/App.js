import React, { Component } from 'react'
import NavBar from './component/NavBar'
import PhotoList from './component/PhotoList'

class App extends Component {

    state = {
        photos: [],
    }

    constructor() {
        super()
    }

    photosFromPhotoList = (photos) => {
        this.setState({ photos: photos });
    }

    render() {
        return (
            <div>
                <NavBar />
                <PhotoList callbackFromParent={this.photosFromPhotoList}/>
            </div>
        )
    }
}
export default App;
