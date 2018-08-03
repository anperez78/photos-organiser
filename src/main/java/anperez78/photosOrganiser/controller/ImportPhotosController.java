package anperez78.photosOrganiser.controller;

import anperez78.photosOrganiser.domain.ImportPhotosResults;
import anperez78.photosOrganiser.service.PhotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@RestController
@Slf4j
public class ImportPhotosController {

    @Autowired
    private PhotoService photoService;

    @Value("${photos.root.folder}")
    private String photosRootFolder;

    @GetMapping("/api/import/all")
    public ImportPhotosResults getImportAll() throws IOException {

        log.info ("photos root folder is " + photosRootFolder);
        final File folder = new File(photosRootFolder);
        return photoService.insertPhotosFromFolder(folder, new ArrayList<>(), new ImportPhotosResults());
    }

}
