package anperez78.photosOrganiser.service;

import anperez78.photosOrganiser.domain.ImportPhotosResults;
import anperez78.photosOrganiser.domain.Photo;
import anperez78.photosOrganiser.dto.PhotoDto;
import anperez78.photosOrganiser.util.ImagesUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
@Import({PhotoService.class, ImagesUtils.class})
public class PhotoServiceIntegrationTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PhotoService photoService;


    @After
    public void tearDown() throws InterruptedException {

        mongoTemplate.dropCollection(Photo.class);
    }

    @Test
    public void insertPhotosFromFolderTest() throws IOException {

        List initialSetOfPhotos = mongoTemplate.findAll(Photo.class);
        assertThat (initialSetOfPhotos.size(), is(0));

        File root = new File("/Users/antonio.perez/personal/photos-organiser/src/test/resources/assets/");

        photoService.insertPhotosFromFolder(root, new ArrayList<>(), new ImportPhotosResults());

        List finalSetOfPhotos = mongoTemplate.findAll(Photo.class);
        assertThat (finalSetOfPhotos.size(), is(4));

    }

    @Test
    public void getQueryPhotoDtosTest() {

        List<String> photo01Tags = Arrays.asList("2015", "Spain", "Menorca");
        Photo photo01 = new Photo("123", "photo01.jpg", "/home/test", photo01Tags);

        List<String> photo02Tags = Arrays.asList("2015", "Belarus", "Minsk");
        Photo photo02 = new Photo("124", "photo02.jpg", "/home/test", photo02Tags);

        mongoTemplate.insert(photo01);
        mongoTemplate.insert(photo02);

        List<String> queryTags = Arrays.asList("2015");
        List<PhotoDto> photoDtos = photoService.getQueryPhotoDtos(queryTags);
        assertThat (photoDtos.size(), is(2));

        queryTags = Arrays.asList("2015", "Spain");
        photoDtos = photoService.getQueryPhotoDtos(queryTags);
        assertThat (photoDtos.size(), is(1));

    }
}