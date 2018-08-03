package anperez78.photosOrganiser.service;

import anperez78.photosOrganiser.domain.ImportPhotosResults;
import anperez78.photosOrganiser.domain.Photo;
import anperez78.photosOrganiser.util.ImagesUtils;
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

    @Test
    public void insertPhotosFromFolderTest() throws IOException {

        List initialSetOfPhotos = mongoTemplate.findAll(Photo.class);
        assertThat (initialSetOfPhotos.size(), is(0));

        File root = new File("/Users/antonio.perez/personal/photos-organiser/src/test/resources/assets/");

        photoService.insertPhotosFromFolder(root, new ArrayList<>(), new ImportPhotosResults());

        List finalSetOfPhotos = mongoTemplate.findAll(Photo.class);
        assertThat (finalSetOfPhotos.size(), is(4));
        System.out.println ("Photos: " + finalSetOfPhotos);

    }
}