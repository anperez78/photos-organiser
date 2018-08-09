package anperez78.photosOrganiser.repository;

import anperez78.photosOrganiser.domain.Photo;

import java.util.List;

public interface ExtendedPhotoRepository {

    List<Photo> findByTags(List<String> tags);

}
