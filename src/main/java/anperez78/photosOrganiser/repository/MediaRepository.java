package anperez78.photosOrganiser.repository;

import anperez78.photosOrganiser.domain.Media;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MediaRepository extends MongoRepository<Media, String>, ExtendedPhotoRepository {
}
