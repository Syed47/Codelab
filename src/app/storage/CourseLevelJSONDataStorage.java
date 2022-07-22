package app.storage;

import com.syed.core.io;

public class CourseLevelJSONDataStorage {
    public String title;
    public boolean visible = true;

    public String getTitle() {
        return title;
    }

    public boolean isVisible() {
        return visible;
    }

    public void print() {
        io.ECHO("Title: " + getTitle());
        io.ECHO("Visible: " + isVisible());
    }

    public String missingValues() {
        String missing = null;
        if (getTitle() == null || getTitle().isBlank()) {
            missing = "Course title not specified";
        }
        io.DEBUG(missing);
        return missing;
    }
}
