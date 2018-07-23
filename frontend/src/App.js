import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import Viewer from 'react-viewer';
import 'react-viewer/dist/index.css';

class App extends Component {

    constructor() {
        super();

        this.state = {
            visible: false,
        };
    }

  state = {};

  componentDidMount() {
      //setInterval(this.hello, 250);
      this.hello;
  }

  hello = () => {
      fetch('/api/hello')
          .then(response => response.text())
          .then(message => {
              this.setState({message: message});
          });
  };

  render() {
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h1 className="App-title">{this.state.message}</h1>
        </header>
        <p className="App-intro">
          This is a test.
        </p>
        <img src="api/picture/123"/>
        <div>
            <button onClick={() => { this.setState({ visible: !this.state.visible }); } }>show</button>
            <Viewer
                visible={this.state.visible}
                onClose={() => { this.setState({ visible: false }); } }
                images={[{src: 'api/picture/123', alt: 'alsdf'} ]}
            />
        </div>
      </div>
    );
  }
}

export default App;
