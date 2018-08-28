package anperez78.photosOrganiser.service;

import anperez78.photosOrganiser.domain.Media;
import anperez78.photosOrganiser.domain.MediaType;
import anperez78.photosOrganiser.dto.MediaDto;
import anperez78.photosOrganiser.repository.MediaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MediaService {

    @Value("${photo.url.base}")
    private String photoUrlBase;

    @Value("${video.url.base}")
    private String videoUrlBase;

    @Autowired
    private MediaRepository mediaRepository;

    public List<MediaDto> getAllMediaDtos() {

        return mediaRepository.findAll()
                .stream()
                .map(media -> new MediaDto(getMediaUrlBase(media) + media.getMd5HashHex(), media.getMediaType(), media.getTags()))
                .collect(Collectors.toList());
    }

    public List<MediaDto> getQueryPhotoDtos(List<String> tags) {

        return mediaRepository.findByTags(tags)
                .stream()
                .map(media -> new MediaDto(getMediaUrlBase(media) + media.getMd5HashHex(), media.getMediaType(), media.getTags()))
                .collect(Collectors.toList());
    }

    private String getMediaUrlBase(Media media) {
        return media.getMediaType() == MediaType.IMAGE? photoUrlBase : videoUrlBase;
    }

}
