package anperez78.photosOrganiser.repository;

import anperez78.photosOrganiser.domain.Media;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
public class ExtendedPhotoRepositoryImpl implements ExtendedPhotoRepository {

    private final MongoOperations operations;

    @Autowired
    public ExtendedPhotoRepositoryImpl(MongoOperations operations) {

        Assert.notNull(operations, "MongoOperations must not be null!");
        this.operations = operations;
    }

    public List<Media> findByTags(List<String> tags) {

        log.info("findByTags: " + tags);

        TextCriteria criteria = TextCriteria
                .forDefaultLanguage()
                .caseSensitive(false)
                .matchingAny(tags.toArray(new String[tags.size()]));
        Query searchQuery = TextQuery.queryText(criteria);
        List<Media> media = operations.find(searchQuery, Media.class);

        log.info("media: " + media.size());

        return media;
    }

}
