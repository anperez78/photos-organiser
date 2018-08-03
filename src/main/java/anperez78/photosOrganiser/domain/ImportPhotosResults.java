package anperez78.photosOrganiser.domain;

import java.util.HashMap;
import java.util.Map;

public class ImportPhotosResults {

    private Map<String, String> errors;
    private Map<String, String> success;
    private Integer total;

    public ImportPhotosResults() {
        this.total = 0;
        this.errors = new HashMap<>();
        this.success = new HashMap<>();
    }

    public void addError(String key, String value) {
        total++;
        errors.put(key, value);
    }

    public void addSuccess(String key, String value) {
        total++;
        success.put(key, value);
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public Map<String, String> getSuccess() {
        return success;
    }

    public void setSuccess(Map<String, String> success) {
        this.success = success;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
