package anperez78.photosOrganiser.service;

import anperez78.photosOrganiser.domain.ImportPhotosResults;
import anperez78.photosOrganiser.domain.Photo;
import anperez78.photosOrganiser.domain.VerifyPhotosResults;
import anperez78.photosOrganiser.util.ImagesUtils;
import org.junit.After;
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
@Import({AdminService.class, ImagesUtils.class})
public class AdminServiceIntegrationTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AdminService adminService;


    @After
    public void tearDown() throws InterruptedException {

        mongoTemplate.dropCollection(Photo.class);
    }

    @Test
    public void insertPhotosFromFolderTest() throws IOException {

        List initialSetOfPhotos = mongoTemplate.findAll(Photo.class);
        assertThat (initialSetOfPhotos.size(), is(0));

        File root = new File("/Users/antonio.perez/personal/photos-organiser/src/test/resources/assets/");
        adminService.insertPhotosFromFolder(root, new ArrayList<>(), new ImportPhotosResults());

        List finalSetOfPhotos = mongoTemplate.findAll(Photo.class);
        assertThat (finalSetOfPhotos.size(), is(4));
    }

    @Test
    public void verifyPhotosFromFolder_returnsNoErrors_whenAllImagesInDB() throws IOException {

        List initialSetOfPhotos = mongoTemplate.findAll(Photo.class);
        assertThat (initialSetOfPhotos.size(), is(0));

        File root = new File("/Users/antonio.perez/personal/photos-organiser/src/test/resources/assets/");
        adminService.insertPhotosFromFolder(root, new ArrayList<>(), new ImportPhotosResults());

        VerifyPhotosResults verifyPhotosResults = adminService.verifyPhotosFromFolder(root);
        assertThat (verifyPhotosResults.getMissingPhotosInDB().size(), is(0));
        assertThat (verifyPhotosResults.getNumberOfMissingPhotosInDB(), is(0L));
        assertThat (verifyPhotosResults.getNumberOfPhotosInDB(), is(4L));
        assertThat (verifyPhotosResults.getNumberOfPhotosInFolder(), is(4L));

    }

    @Test
    public void verifyPhotosFromFolder_returnsErrors_whenMissingImagesInDB() throws IOException {

        List initialSetOfPhotos = mongoTemplate.findAll(Photo.class);
        assertThat (initialSetOfPhotos.size(), is(0));

        File root = new File("/Users/antonio.perez/personal/photos-organiser/src/test/resources/assets/");
        adminService.insertPhotosFromFolder(root, new ArrayList<>(), new ImportPhotosResults());

        Photo photoToBeDeleted = mongoTemplate.findById("EF9546DE14CFD2FE6D460912D23E0739", Photo.class);
        mongoTemplate.remove(photoToBeDeleted);

        VerifyPhotosResults verifyPhotosResults = adminService.verifyPhotosFromFolder(root);
        assertThat (verifyPhotosResults.getMissingPhotosInDB().size(), is(1));
        assertThat (verifyPhotosResults.getMissingPhotosInDB().get(0), is("EF9546DE14CFD2FE6D460912D23E0739"));
        assertThat (verifyPhotosResults.getNumberOfMissingPhotosInDB(), is(1L));
        assertThat (verifyPhotosResults.getNumberOfPhotosInDB(), is(3L));
        assertThat (verifyPhotosResults.getNumberOfPhotosInFolder(), is(4L));
    }


}