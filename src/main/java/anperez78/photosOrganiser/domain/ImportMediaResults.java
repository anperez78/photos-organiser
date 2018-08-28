package anperez78.photosOrganiser.domain;

import java.util.HashMap;
import java.util.Map;

public class ImportMediaResults {

    private Map<String, String> errors;
    private Integer totalCount;
    private Integer successCount;

    public ImportMediaResults() {
        this.totalCount = 0;
        this.successCount = 0;
        this.errors = new HashMap<>();
    }

    public void addError(String key, String value) {
        this.totalCount++;
        this.errors.put(key, value);
    }

    public void addSuccess() {
        this.totalCount++;
        this.successCount++;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }
}
