package anperez78.photosOrganiser.service;

import anperez78.photosOrganiser.dto.MediaDto;
import anperez78.photosOrganiser.repository.MediaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    public List<MediaDto> getAllMediaDtos() {

        return mediaRepository.findAll()
                .stream()
                .map(media -> new MediaDto(media.getMd5HashHex(), media.getMediaType(), media.getTags()))
                .collect(Collectors.toList());
    }

    public List<MediaDto> getQueryPhotoDtos(List<String> tags) {

        return mediaRepository.findByTags(tags)
                .stream()
                .map(media -> new MediaDto(media.getMd5HashHex(), media.getMediaType(), media.getTags()))
                .collect(Collectors.toList());
    }

}
