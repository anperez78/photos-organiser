package anperez78.photosOrganiser.dto;


public class PhotoDto {

    private String photoUrl;

    public PhotoDto(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
