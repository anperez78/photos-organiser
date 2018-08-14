import React, { Component } from 'react'
import AppBar from '@material-ui/core/AppBar'
import Toolbar from '@material-ui/core/Toolbar'
import Typography from '@material-ui/core/Typography'
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import Viewer from 'react-viewer';


const styles = theme => ({
    root: {
        flexGrow: 1,
    },
    flex: {
        flexGrow: 1,
    },
    button: {
        margin: theme.spacing.unit,
    },
});


class NavBar extends Component {

    state = {
        visibleViewer: false,

    }

    constructor() {
        super()
    }

    render() {
        const {classes} = this.props;

        const photosForViewer = this.props.photos.map(photo => { return { src: photo.photoUrl, alt: photo.photoUrl} });

        console.log ('photosForViewer in NavBar: ', photosForViewer)

        return(
            <div className={classes.root}>
                <AppBar position="static">
                    <Toolbar>
                        <Typography variant="title" color="inherit" className={classes.flex}>
                            Fotos de Larisa y Antonio 1
                        </Typography>
                        <Button variant="contained" color="primary" className={classes.button} onClick={() => { this.setState({ visibleViewer: !this.state.visibleViewer }); } }>
                            Play
                        </Button>
                        <Viewer
                            visible={this.state.visibleViewer}
                            onClose={() => { this.setState({ visibleViewer: false }); } }
                            images={photosForViewer}
                        />
                    </Toolbar>
                </AppBar>
            </div>
        )
    }
}

NavBar.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(NavBar);