import React from 'react'
import Card from '@material-ui/core/Card'
import CardActions from '@material-ui/core/CardActions'
import CardContent from '@material-ui/core/CardContent'
import CardMedia from '@material-ui/core/CardMedia'
import Chip from '@material-ui/core/Chip';
import { withStyles } from '@material-ui/core/styles';
import ReactPlayer from 'react-player'


const styles = theme => ({
    content: {
        height: 35
    },
    chip: {
        margin: theme.spacing.unit,
    },
});

const Photo = (props) => {

    const { classes } = props;
    let mediaCard;

    if (props.photo.mediaType == 'VIDEO') {

        mediaCard = <CardMedia title={props.photo.mediaUrl} >
                        <ReactPlayer url={props.photo.mediaUrl} controls="true" />
                    </CardMedia>;
    } else {
        mediaCard = <CardMedia style={{height: 0, paddingTop: '56.25%'}}
                               image={props.photo.mediaUrl}
                               title={props.photo.mediaUrl} />;
    }

    return(
        <div>
            { props.photo ? (
                <Card >
                    {mediaCard}
                    <CardContent className={classes.content}>
                        { props.photo.tags.map(tag => (
                            <Chip label={tag} className={classes.chip} />
                        ))}
                        { props.photo.mediaType }
                        This is a test 9
                    </CardContent>
                    <CardActions>
                    </CardActions>
                </Card>
            ) : null}
        </div>
    )
}

export default withStyles(styles)(Photo);