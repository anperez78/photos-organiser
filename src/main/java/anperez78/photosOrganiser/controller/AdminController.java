package anperez78.photosOrganiser.controller;

import anperez78.photosOrganiser.domain.ImportPhotosResults;
import anperez78.photosOrganiser.domain.VerifyPhotosResults;
import anperez78.photosOrganiser.service.AdminService;
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
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Value("${photos.root.folder}")
    private String photosRootFolder;

    @GetMapping("/api/admin/import")
    public ImportPhotosResults getImport() throws IOException {

        log.info ("photos root folder is " + photosRootFolder);
        final File folder = new File(photosRootFolder);
        return adminService.insertPhotosFromFolder(folder, new ArrayList<>(), new ImportPhotosResults());
    }

    @GetMapping("/api/admin/verify")
    public VerifyPhotosResults getVerify() throws IOException {

        log.info ("photos root folder is " + photosRootFolder);
        final File folder = new File(photosRootFolder);
        return adminService.verifyPhotosFromFolder(folder);
    }

}