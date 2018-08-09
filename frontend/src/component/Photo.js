import React from 'react'
import Card from '@material-ui/core/Card'
import CardActions from '@material-ui/core/CardActions'
import CardContent from '@material-ui/core/CardContent'
import CardMedia from '@material-ui/core/CardMedia'
import Button from '@material-ui/core/Button'
import Chip from '@material-ui/core/Chip';
import { withStyles } from '@material-ui/core/styles';


const styles = theme => ({
    root: {
        display: 'flex',
        justifyContent: 'center',
        flexWrap: 'wrap',
    },
    chip: {
        margin: theme.spacing.unit,
    },
});

const Photo = (props) => {

    const { classes } = props;

    console.log ('classes: ', classes)

    return(
        <div>
            { props.photo ? (
                <Card >
                    <CardMedia style={{height: 0, paddingTop: '56.25%'}}
                               image={props.photo.photoUrl}
                               title={props.photo.photoUrl}
                    />
                    <CardContent>
                        <div className={classes.root}>
                        { props.photo.tags.map(tag => (
                            <Chip label={tag} className={classes.chip} />
                        ))}
                        </div>

                    </CardContent>
                    <CardActions>
                        <Button size="small" color="primary" href={props.photo.photoUrl} target="_blank">
                            Go To photo
                        </Button>
                    </CardActions>
                </Card>
            ) : null}
        </div>
    )
}

export default withStyles(styles)(Photo);