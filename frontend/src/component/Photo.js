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
import Image from 'material-ui-image'

function getModalStyle() {
    return {
        position: 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
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
        width: theme.spacing.unit * 50,
        backgroundColor: theme.palette.background.paper,
        boxShadow: theme.shadows[5],
        padding: theme.spacing.unit * 4,
    },
});


class Photo extends Component {

    state = {
        open: false,
    };

    handleOpen = () => {
        this.setState({ open: true });
    };

    handleClose = () => {
        this.setState({ open: false });
    };

    constructor() {
        super()
    }

    render() {

        const {classes} = this.props;
        let mediaCard;

        if (this.props.photo.mediaType == 'VIDEO') {

            mediaCard = <CardMedia>
                            <ReactPlayer url={this.props.photo.mediaUrl}
                                         controls="true"
                                         height="50%" width="50%"
                                         style={{display: 'block', marginLeft: 'auto', marginRight: 'auto'}}/>
                        </CardMedia>;
        } else {
            mediaCard = <CardMedia style={{height: 0, paddingTop: '56.25%'}}
                                   image={this.props.photo.mediaUrl} />;
        }

        return (
            <div>
                <Modal
                    aria-labelledby="simple-modal-title"
                    aria-describedby="simple-modal-description"
                    open={this.state.open}
                    onClose={this.handleClose}
                >
                    <div style={getModalStyle()} className={classes.paper}>
                        <Image
                            src={this.props.photo.mediaUrl}
                        />
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
                            <Button size="small" color="primary" onClick={this.handleOpen}>
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