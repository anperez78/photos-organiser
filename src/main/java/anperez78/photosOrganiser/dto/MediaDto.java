package anperez78.photosOrganiser.dto;


import anperez78.photosOrganiser.domain.MediaType;

import java.util.List;

public class MediaDto {

    private String mediaId;
    private MediaType mediaType;
    private List<String> tags;

    public MediaDto(String mediaId, MediaType mediaType, List<String> tags) {
        this.mediaId = mediaId;
        this.mediaType = mediaType;
        this.tags = tags;
    }

    public String getMediaId() {
        return mediaId;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public List<String> getTags() {
        return tags;
    }

}
