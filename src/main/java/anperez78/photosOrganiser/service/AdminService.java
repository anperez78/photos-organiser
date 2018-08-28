package anperez78.photosOrganiser.service;

import anperez78.photosOrganiser.domain.ImportMediaResults;
import anperez78.photosOrganiser.domain.Media;
import anperez78.photosOrganiser.domain.VerifyPhotosResults;
import anperez78.photosOrganiser.repository.MediaRepository;
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

    @Value("${photo.url.base}")
    private String photosUrlBase;

    @Value("#{'${media.import.exception}'.split(',')}")
    private List<String> photosImportExceptions;

    @Autowired
    private ImagesUtils imagesUtils;

    @Autowired
    private MediaRepository mediaRepository;

    public ImportMediaResults insertPhotosFromFolder(final File folder, List<String> tags, ImportMediaResults importMediaResults) throws IOException {

        for (final File fileEntry : folder.listFiles()) {

            if (fileEntry.isDirectory()) {

                String tag = fileEntry.getName();

                if (isImportException(tag)) {
                    continue;
                }

                List<String> tempTags = new ArrayList<>(tags);
                tempTags.add(tag);
                importMediaResults = insertPhotosFromFolder(fileEntry, tempTags, importMediaResults);
            }
            else {
                String fileName = fileEntry.getName();
                String filePath = fileEntry.getParent();
                String md5HashHex = imagesUtils.getMd5HashFromFile(fileEntry.getAbsolutePath());
                Media media = new Media(md5HashHex, fileName, filePath, tags);

                log.info(media.toString());

                if (mediaRepository.existsById(media.getMd5HashHex())) {
                    log.warn("Media " + media.getMd5HashHex() + " already exists");
                    importMediaResults.addError(
                            media.getMd5HashHex(),
                            "Media already exists in the DB (" + filePath + "/" + fileName + ")");
                }
                else {
                    mediaRepository.save(media);
                    importMediaResults.addSuccess();
                }
            }
        }

        return importMediaResults;
    }

    public VerifyPhotosResults verifyPhotosFromFolder(final File folder) throws IOException {

        VerifyPhotosResults verifyPhotosResults = new VerifyPhotosResults();
        verifyPhotosResults.setNumberOfPhotosInDB(mediaRepository.count());

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
                if (!mediaRepository.existsById(md5HashHex)) {
                    verifyPhotosResults.addMissingPhotoInDB(md5HashHex);
                }
            }
        }

        return verifyPhotosResults;
    }

    private Boolean isImportException (String folderName) {
        return photosImportExceptions.contains(folderName);
    }

}
