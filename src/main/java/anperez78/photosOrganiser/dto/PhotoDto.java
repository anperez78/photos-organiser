package anperez78.photosOrganiser.dto;


import java.util.List;

public class PhotoDto {

    private String photoUrl;
    private List<String> tags;

    public PhotoDto(String photoUrl, List<String> tags) {
        this.photoUrl = photoUrl;
        this.tags = tags;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
