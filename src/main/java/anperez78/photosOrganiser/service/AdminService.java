package anperez78.photosOrganiser.service;

import anperez78.photosOrganiser.domain.ImportPhotosResults;
import anperez78.photosOrganiser.domain.Photo;
import anperez78.photosOrganiser.domain.VerifyPhotosResults;
import anperez78.photosOrganiser.repository.PhotoRepository;
import anperez78.photosOrganiser.util.ImagesUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AdminService {

    @Value("${photos.url.base}")
    private String photosUrlBase;

    @Autowired
    private ImagesUtils imagesUtils;

    @Autowired
    private PhotoRepository photoRepository;

    public ImportPhotosResults insertPhotosFromFolder(final File folder, List<String> tags,  ImportPhotosResults importPhotosResults) throws IOException {

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                String tag = fileEntry.getName();
                List<String> tempTags = new ArrayList<>(tags);
                tempTags.add(tag);
                importPhotosResults = insertPhotosFromFolder(fileEntry, tempTags, importPhotosResults);
            }
            else {
                String photoFilename = fileEntry.getName();
                String photoFilepath = fileEntry.getParent();
                String md5HashHex = imagesUtils.getMd5HashFromFile(fileEntry.getAbsolutePath());
                Photo photo = new Photo(md5HashHex, photoFilename, photoFilepath, tags);

                log.info(photo.toString());

                if (photoRepository.existsById(photo.getMd5HashHex())) {
                    log.warn("Photo " + photo.getMd5HashHex() + " already exists");
                    importPhotosResults.addError(
                            photo.getMd5HashHex(),
                            "Photo already exists in the DB (" + photoFilepath + "/" + photoFilename + ")");
                }
                else {
                    photoRepository.save(photo);
                    importPhotosResults.addSuccess(
                            photo.getMd5HashHex(),
                            "Photo added successfully (" + photoFilepath + "/" + photoFilename + ")");
                }
            }
        }

        return importPhotosResults;
    }

    public VerifyPhotosResults verifyPhotosFromFolder(final File folder) throws IOException {

        VerifyPhotosResults verifyPhotosResults = new VerifyPhotosResults();
        verifyPhotosResults.setNumberOfPhotosInDB(photoRepository.count());

        return checkPhotos(folder, verifyPhotosResults);

    }


    private VerifyPhotosResults checkPhotos(final File folder, VerifyPhotosResults verifyPhotosResults) throws IOException {

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                verifyPhotosResults = checkPhotos(fileEntry, verifyPhotosResults);
            }
            else {
                verifyPhotosResults.addExistingPhotoInFolder();
                String md5HashHex = imagesUtils.getMd5HashFromFile(fileEntry.getAbsolutePath());
                if (!photoRepository.existsById(md5HashHex)) {
                    verifyPhotosResults.addMissingPhotoInDB(md5HashHex);
                }
            }
        }

        return verifyPhotosResults;
    }

}