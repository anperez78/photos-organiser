package anperez78.photosOrganiser.controller;

import anperez78.photosOrganiser.domain.Photo;
import anperez78.photosOrganiser.dto.PhotoDto;
import anperez78.photosOrganiser.repository.PhotoRepository;
import anperez78.photosOrganiser.service.PhotoService;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class DonwloadPhotoController {

    @Autowired
    PhotoService photoService;

    @Autowired
    PhotoRepository photoRepository;

    @GetMapping("/api/photo/all")
    public List<PhotoDto> getAllPhotos() {

        return photoService.getAllPhotoDtos();
    }

    @GetMapping("/api/photo")
    public List<PhotoDto> getQueryPhotos(@RequestParam("tags") String tagParams) {

        List<String> tags =  Collections.list(new StringTokenizer(tagParams, "+")).stream()
                .map(token -> (String) token)
                .collect(Collectors.toList());

        return photoService.getQueryPhotoDtos(tags);
    }

    @RequestMapping("/api/photo/{md5}")
    @ResponseBody
    public HttpEntity<byte[]> getPhotoBinary(@PathVariable String md5) throws Exception {

        log.info("Requested picture >> " + md5 + " <<");

        Optional<Photo> photo = photoRepository.findById(md5);
        if (!photo.isPresent()) {
            throw new Exception("Image not found");
        }

        String photoFullPath = photo.get().getPhotoFilepath() + "/" + photo.get().getPhotoFilename();
        Path path = Paths.get(photoFullPath);
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
