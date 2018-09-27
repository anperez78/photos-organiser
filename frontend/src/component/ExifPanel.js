import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import ExpansionPanel from '@material-ui/core/ExpansionPanel';
import ExpansionPanelSummary from '@material-ui/core/ExpansionPanelSummary';
import ExpansionPanelDetails from '@material-ui/core/ExpansionPanelDetails';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';


const styles = {
    root: {
        width: '100%',
        maxWidth: 500,
    },
    details: {
        display: 'block',
    }
};

class ExifPanel extends React.Component {

    render() {
        const { classes } = this.props;

        const exifData = this.props.exifData;

        console.log ('-------> exifData: ', exifData);

        return (
            <div className={classes.root}>

                { exifData ? (exifData.exifDirectoryDtoList.map(item => (

                    <ExpansionPanel>
                        <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
                            <Typography>{item.tagDirectoryName}</Typography>
                        </ExpansionPanelSummary>
                        <ExpansionPanelDetails className={classes.details}>
                            { item.exifItemDtoList ? (item.exifItemDtoList.map(exifItem => (
                                    <Typography variant="caption">{exifItem.tagName}: {exifItem.tagDescription}</Typography>
                                ))
                            ) : null}
                        </ExpansionPanelDetails>
                    </ExpansionPanel>
                    ))
                ) : null}

            </div>
        );
    }
}

ExifPanel.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(ExifPanel);