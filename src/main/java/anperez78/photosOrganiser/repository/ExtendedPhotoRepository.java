package anperez78.photosOrganiser.repository;

import anperez78.photosOrganiser.domain.Media;

import java.util.List;

public interface ExtendedPhotoRepository {

    List<Media> findByTags(List<String> tags);

}
