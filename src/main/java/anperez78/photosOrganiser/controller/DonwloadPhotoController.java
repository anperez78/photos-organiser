package anperez78.photosOrganiser.controller;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@RestController
@Slf4j
public class DonwloadPhotoController {

    @RequestMapping("/api/picture/{id}")
    @ResponseBody
    public HttpEntity<byte[]> getArticleImage(@PathVariable String id) throws IOException {

        log.info("Requested picture >> " + id + " <<");

        Path path = Paths.get("/Users/antonio.perez/personal/photos-organiser/frontend/public/images/test01.jpeg");
        byte[] data = Files.readAllBytes(path);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(data.length);

        return new HttpEntity<>(data, headers);
    }


    @GetMapping("/api/exif")
    public String getExifInfo() throws IOException, ImageProcessingException {

        File file = new File("/Users/antonio.perez/personal/photos-organiser/frontend/public/images/2018-07-01 11.32.58.jpg");

        Metadata metadata = ImageMetadataReader.readMetadata(file);

        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                System.out.println(tag);
            }
        }

        return "";
    }
}
