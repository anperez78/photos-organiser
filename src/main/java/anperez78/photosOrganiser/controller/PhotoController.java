package anperez78.photosOrganiser.controller;

import anperez78.photosOrganiser.domain.Media;
import anperez78.photosOrganiser.dto.ExifDirectoryDto;
import anperez78.photosOrganiser.dto.ExifDto;
import anperez78.photosOrganiser.dto.ExifItemDto;
import anperez78.photosOrganiser.dto.MediaDto;
import anperez78.photosOrganiser.repository.MediaRepository;
import anperez78.photosOrganiser.service.MediaService;
import com.drew.imaging.ImageMetadataReader;
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
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class PhotoController {

    @Autowired
    MediaService mediaService;

    @Autowired
    MediaRepository mediaRepository;

    @GetMapping("/api/media/all")
    public List<MediaDto> getAllMedia() {

        return mediaService.getAllMediaDtos();
    }

    @GetMapping("/api/photo")
    public List<MediaDto> getQueryPhotos(@RequestParam("tags") String tagParams) {

        log.info("tagParams: " + tagParams);
        List<String> tags =  Collections.list(new StringTokenizer(tagParams, " ")).stream()
                .map(token -> (String) token)
                .collect(Collectors.toList());

        log.info("tags: " + tags);
        return mediaService.getQueryPhotoDtos(tags);
    }

    @RequestMapping("/api/photo/{md5}")
    @ResponseBody
    public HttpEntity<byte[]> getPhotoBinary(@PathVariable String md5) throws Exception {

        log.info("Requesting photo >> " + md5 + " <<");

        Optional<Media> photo = mediaRepository.findById(md5);
        if (!photo.isPresent()) {
            throw new Exception("Image not found: " + md5);
        }

        String photoFullPath = photo.get().getFilePath() + "/" + photo.get().getFileName();
        Path path = Paths.get(photoFullPath);
        byte[] data = Files.readAllBytes(path);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(data.length);

        return new HttpEntity<>(data, headers);
    }

    @RequestMapping("/api/video/{md5}")
    public StreamingResponseBody getVideoBinary(@PathVariable String md5) throws Exception {

        log.info("Requesting video >> " + md5 + " <<");

        Optional<Media> photo = mediaRepository.findById(md5);
        if (!photo.isPresent()) {
            throw new Exception("Video not found: " + md5);
        }

        String photoFullPath = photo.get().getFilePath() + "/" + photo.get().getFileName();

        final InputStream videoFileStream = new FileInputStream(photoFullPath);
        return (os) -> {
            readAndWrite(videoFileStream, os);
        };
    }


    @GetMapping("/api/photo/{md5}/exif")
    public ExifDto getPhotoExifInfo(@PathVariable String md5) throws Exception {

        log.info("Requesting EXIF photo >> " + md5 + " <<");

        Optional<Media> photo = mediaRepository.findById(md5);
        if (!photo.isPresent()) {
            throw new Exception("Image not found: " + md5);
        }

        String photoFullPath = photo.get().getFilePath() + "/" + photo.get().getFileName();
        File file = new File(photoFullPath);

        Metadata metadata = ImageMetadataReader.readMetadata(file);

        ExifDto exifDto = new ExifDto();
        for (Directory directory : metadata.getDirectories()) {
            ExifDirectoryDto exifDirectoryDto = new ExifDirectoryDto(directory.getName());
            for (Tag tag : directory.getTags()) {
                exifDirectoryDto.addExifItem(new ExifItemDto(tag.getTagName(), tag.getDescription()));
            }
            exifDto.addExifDirectory(exifDirectoryDto);
        }

        return exifDto;
    }

    private void readAndWrite(final InputStream is, OutputStream os) throws IOException {

        byte[] data = new byte[2048];
        int read = 0;
        while ((read = is.read(data)) > 0) {
            os.write(data, 0, read);
        }
        os.flush();
    }
}
