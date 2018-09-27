package anperez78.photosOrganiser.dto;

public class ExifItemDto {

    private String tagName;
    private String tagDescription;

    public ExifItemDto(String tagName, String tagDescription) {
        this.tagName = tagName;
        this.tagDescription = tagDescription;
    }

    public String getTagName() {
        return tagName;
    }

    public String getTagDescription() {
        return tagDescription;
    }
}
