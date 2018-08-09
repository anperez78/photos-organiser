package anperez78.photosOrganiser.repository;

import anperez78.photosOrganiser.domain.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PhotoRepository extends MongoRepository<Photo, String> {

    List<Photo> findByTagsIn(List<String> tags);

}