package anperez78.photosOrganiser.service;

import anperez78.photosOrganiser.dto.PhotoDto;
import anperez78.photosOrganiser.repository.PhotoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PhotoService {

    @Value("${photos.url.base}")
    private String photosUrlBase;

    @Autowired
    private PhotoRepository photoRepository;

    public List<PhotoDto> getAllPhotoDtos() {

        return photoRepository.findAll()
                .stream()
                .map(photo -> new PhotoDto(photosUrlBase + photo.getMd5HashHex(), photo.getTags()))
                .collect(Collectors.toList());
    }

    public List<PhotoDto> getQueryPhotoDtos(List<String> tags) {

        return photoRepository.findByTags(tags)
                .stream()
                .map(photo -> new PhotoDto(photosUrlBase + photo.getMd5HashHex(), photo.getTags()))
                .collect(Collectors.toList());
    }

}
