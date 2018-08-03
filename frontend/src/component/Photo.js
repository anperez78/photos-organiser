import React from 'react'
import Card from '@material-ui/core/Card'
import CardActions from '@material-ui/core/CardActions'
import CardContent from '@material-ui/core/CardContent'
import CardMedia from '@material-ui/core/CardMedia'
import Button from '@material-ui/core/Button'
import Typography from '@material-ui/core/Typography'

const Photo = (props) => {

    return(
        <div>
            { props.photo ? (
                <Card >
                    <CardMedia style={{height: 0, paddingTop: '56.25%'}}
                               image={props.photo.photoUrl}
                               title={props.photo.photoUrl}
                    />
                    <CardContent>
                        <Typography gutterBottom variant="headline" component="h2">
                            {props.photo.photoUrl}
                        </Typography>
                        <Typography component="p">
                            {props.photo.photoUrl}
                        </Typography>
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

export default Photo;