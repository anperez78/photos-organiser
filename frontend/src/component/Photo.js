import React, { Component } from 'react'
import Card from '@material-ui/core/Card'
import CardActions from '@material-ui/core/CardActions'
import CardContent from '@material-ui/core/CardContent'
import CardMedia from '@material-ui/core/CardMedia'
import Button from '@material-ui/core/Button'
import Chip from '@material-ui/core/Chip';
import { withStyles } from '@material-ui/core/styles';
import ReactPlayer from 'react-player';
import PropTypes from 'prop-types';
import Modal from '@material-ui/core/Modal';
import ExifPanel from './ExifPanel'


function getModalStyle() {
    return {
        width: '75%',
        display: 'flex',
        alignItems: 'center',
        position: 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        backgroundColor: 'white'
    };
}

const styles = theme => ({
    content: {
        height: 35
    },
    chip: {
        margin: theme.spacing.unit,
    },
    paper: {
        position: 'absolute',
        width: theme.spacing.unit * 100,
        backgroundColor: theme.palette.background.paper,
        boxShadow: theme.shadows[5],
        padding: theme.spacing.unit * 4,
    },
});


class Photo extends Component {

    state = {
        open: false,
        exif: null,
    };

    constructor() {
        super();
    };

    handleOpen = (photoExifUrl) => {
        this.getPhotoExifInfo(photoExifUrl)
        this.setState({ open: true });
    };

    handleClose = () => {
        this.setState({ open: false });
    };

    getPhotoExifInfo = (photoUrl) => {
        fetch(photoUrl)
            .then(response => response.json())
            .then(data => {
                this.setState({exif: data});
            })
            .catch((error) => {
                console.log("Error occurred while fetching photo exif [/api/photo/md5/exif]")
                console.error(error)
            })
    };

    render() {

        const {classes} = this.props;
        const mediaId = this.props.photo.mediaId;
        const photoExifUrl = '/api/photo/' + mediaId + '/exif';
        const photoUrl = '/api/photo/' + mediaId;

        let mediaCard;

        if (this.props.photo.mediaType == 'VIDEO') {

            const videoUrl = '/api/video/' + mediaId;

            mediaCard = <CardMedia>
                            <ReactPlayer url={videoUrl}
                                         controls="true"
                                         height="50%" width="50%"
                                         style={{display: 'block', marginLeft: 'auto', marginRight: 'auto'}}/>
                        </CardMedia>;
        } else {

            mediaCard = <CardMedia style={{height: 0, paddingTop: '56.25%'}}
                                   image={photoUrl} />;
        }

        return (
            <div>

                <Modal
                    open={this.state.open}
                    onClose={this.handleClose}
                >
                    <div style={getModalStyle()}>

                        <img style={{ width: '75%', height: 'auto', paddingTop: '40px', paddingRight: '20px', paddingBottom: '40px', paddingLeft: '20px'}} src={photoUrl}/>

                        <div align="left" style={{ paddingRight: '20px'}}>
                            <ExifPanel exifData={this.state.exif}/>
                        </div>

                    </div>
                </Modal>


                { this.props.photo ? (
                    <Card >
                        {mediaCard}
                        <CardContent className={classes.content}>
                            { this.props.photo.tags.map(tag => (
                                <Chip label={tag} className={classes.chip}/>
                            ))}
                        </CardContent>
                        <CardActions>
                            <Button size="small" color="primary"  onClick={() => this.handleOpen(photoExifUrl)}>
                                Show
                            </Button>
                        </CardActions>
                    </Card>
                ) : null}
            </div>
        )
    }
}

Photo.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Photo);