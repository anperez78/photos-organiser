import React, { Component } from 'react'
import NavBar from './component/NavBar'
import PhotoList from './component/PhotoList'

class App extends Component {
    render() {
        return (
            <div>
                <NavBar />
                <PhotoList />
            </div>
        )
    }
}
export default App;
