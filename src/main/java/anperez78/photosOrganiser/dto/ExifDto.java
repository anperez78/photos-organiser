package anperez78.photosOrganiser.dto;


import java.util.ArrayList;
import java.util.List;

public class ExifDto {

    private List<ExifDirectoryDto> exifDirectoryDtoList;

    public ExifDto() {
        this.exifDirectoryDtoList = new ArrayList<>();
    }

    public void addExifDirectory(ExifDirectoryDto exifDirectoryDto) {
        this.exifDirectoryDtoList.add(exifDirectoryDto);
    }

    public List<ExifDirectoryDto> getExifDirectoryDtoList() {
        return exifDirectoryDtoList;
    }
}
