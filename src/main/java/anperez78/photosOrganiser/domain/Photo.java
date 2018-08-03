package anperez78.photosOrganiser.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "photos")

public class Photo {

    @Id
    private String md5HashHex;
    private String photoFilename;
    private String photoFilepath;
    private List<String> tags;

    public Photo(String md5HashHex, String photoFilename, String photoFilepath, List<String> tags) {
        this.md5HashHex = md5HashHex;
        this.photoFilename = photoFilename;
        this.photoFilepath = photoFilepath;
        this.tags = tags;
    }

    public String getMd5HashHex() {
        return md5HashHex;
    }

    public String getPhotoFilename() {
        return photoFilename;
    }

    public String getPhotoFilepath() {
        return photoFilepath;
    }

    public List<String> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "md5HashHex='" + md5HashHex + '\'' +
                ", photoFilename='" + photoFilename + '\'' +
                ", photoFilepath='" + photoFilepath + '\'' +
                ", tags=" + tags +
                '}';
    }
}
