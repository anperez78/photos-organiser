package anperez78.photosOrganiser.service;

import anperez78.photosOrganiser.domain.Media;
import anperez78.photosOrganiser.dto.MediaDto;
import anperez78.photosOrganiser.util.ImagesUtils;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
@Import({MediaService.class, ImagesUtils.class})
public class MediaServiceIntegrationTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MediaService mediaService;


    @After
    public void tearDown() throws InterruptedException {

        mongoTemplate.dropCollection(Media.class);
    }

    @Test
    public void getQueryPhotoDtosTest() {

        List<String> photo01Tags = Arrays.asList("2015", "Spain", "Menorca");
        Media media01 = new Media("123", "media01.jpg", "/home/test", photo01Tags);

        List<String> photo02Tags = Arrays.asList("2015", "Belarus", "Minsk");
        Media media02 = new Media("124", "media02.jpg", "/home/test", photo02Tags);

        mongoTemplate.insert(media01);
        mongoTemplate.insert(media02);

        List<String> queryTags = Arrays.asList("2015");
        List<MediaDto> mediaDtos = mediaService.getQueryPhotoDtos(queryTags);
        assertThat (mediaDtos.size(), is(2));

        queryTags = Arrays.asList("2015", "Spain");
        mediaDtos = mediaService.getQueryPhotoDtos(queryTags);
        assertThat (mediaDtos.size(), is(1));

    }
}