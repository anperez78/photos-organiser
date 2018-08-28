package anperez78.photosOrganiser.dto;


import anperez78.photosOrganiser.domain.MediaType;

import java.util.List;

public class MediaDto {

    private String mediaUrl;
    private MediaType mediaType;
    private List<String> tags;

    public MediaDto(String mediaUrl, MediaType mediaType, List<String> tags) {
        this.mediaUrl = mediaUrl;
        this.mediaType = mediaType;
        this.tags = tags;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
