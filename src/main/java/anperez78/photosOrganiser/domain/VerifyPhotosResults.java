package anperez78.photosOrganiser.domain;

import java.util.ArrayList;
import java.util.List;

public class VerifyPhotosResults {

    private long numberOfPhotosInDB;
    private long numberOfPhotosInFolder;
    private long numberOfMissingPhotosInDB;
    private List<String> missingPhotosInDB;

    public VerifyPhotosResults() {
        this.numberOfPhotosInDB = 0;
        this.numberOfPhotosInFolder = 0;
        this.numberOfMissingPhotosInDB = 0;
        this.missingPhotosInDB = new ArrayList<>();
    }

    public void addMissingPhotoInDB(String missingPhotoInDB) {
        this.numberOfMissingPhotosInDB++;
        this.missingPhotosInDB.add(missingPhotoInDB);
    }

    public void addExistingPhotoInFolder() {
        this.numberOfPhotosInFolder++;
    }

    public long getNumberOfPhotosInDB() {
        return numberOfPhotosInDB;
    }

    public void setNumberOfPhotosInDB(long numberOfPhotosInDB) {
        this.numberOfPhotosInDB = numberOfPhotosInDB;
    }

    public long getNumberOfPhotosInFolder() {
        return numberOfPhotosInFolder;
    }

    public long getNumberOfMissingPhotosInDB() {
        return numberOfMissingPhotosInDB;
    }

    public List<String> getMissingPhotosInDB() {
        return missingPhotosInDB;
    }
}
