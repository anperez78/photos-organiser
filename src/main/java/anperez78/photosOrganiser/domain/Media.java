package anperez78.photosOrganiser.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "media")

public class Media {

    @Id
    private String md5HashHex;
    private String fileName;
    private String filePath;
    private MediaType mediaType;
    @TextIndexed
    private List<String> tags;

    public Media(String md5HashHex, String fileName, String filePath, List<String> tags) {
        this.md5HashHex = md5HashHex;
        this.fileName = fileName;
        this.filePath = filePath;
        this.tags = tags;
        this.mediaType = fileName.endsWith("mp4") ? MediaType.VIDEO: MediaType.IMAGE;
    }

    public String getMd5HashHex() {
        return md5HashHex;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    @Override
    public String toString() {
        return "Media{" +
                "md5HashHex='" + md5HashHex + '\'' +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", mediaType=" + mediaType +
                ", tags=" + tags +
                '}';
    }
}
