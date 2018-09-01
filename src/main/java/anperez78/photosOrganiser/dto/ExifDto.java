package anperez78.photosOrganiser.dto;


public class ExifDto {

    private String tagName;
    private String tagDirectoryName;
    private String tagDescription;

    public ExifDto(String tagName, String tagDirectoryName, String tagDescription) {
        this.tagName = tagName;
        this.tagDirectoryName = tagDirectoryName;
        this.tagDescription = tagDescription;
    }

    public String getTagName() {
        return tagName;
    }

    public String getTagDirectoryName() {
        return tagDirectoryName;
    }

    public String getTagDescription() {
        return tagDescription;
    }
}
