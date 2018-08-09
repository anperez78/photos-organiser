package anperez78.photosOrganiser.repository;

import anperez78.photosOrganiser.domain.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhotoRepository extends MongoRepository<Photo, String>, ExtendedPhotoRepository {
}
