package anperez78.photosOrganiser.dto;

import java.util.ArrayList;
import java.util.List;

public class ExifDirectoryDto {

    private String tagDirectoryName;
    private List<ExifItemDto> exifItemDtoList;

    public ExifDirectoryDto(String tagDirectoryName) {
        this.tagDirectoryName = tagDirectoryName;
        this.exifItemDtoList = new ArrayList<>();
    }

    public void addExifItem (ExifItemDto exifItemDto) {
        this.exifItemDtoList.add(exifItemDto);
    }

    public String getTagDirectoryName() {
        return tagDirectoryName;
    }

    public List<ExifItemDto> getExifItemDtoList() {
        return exifItemDtoList;
    }
}
