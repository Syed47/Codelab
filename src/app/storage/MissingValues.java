package app.storage;

import java.util.HashMap;
import java.util.Map;

public class MissingValues {
    private final Map<String, String> fields;

    public MissingValues() {
        this.fields = new HashMap<>();
    }

    public void setFields(String field, String value) {
        this.fields.put(field, value);
    }

    // I want a method that returns the first missing value
    public void exist() {
        for (Map.Entry<String, String> p : fields.entrySet()) {
            if (p.getValue() == null) {

            }
        }
    }
}
