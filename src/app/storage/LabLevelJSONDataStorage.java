package app.storage;

import app.ui.Widget;
import com.syed.core.io;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutionException;

public class LabLevelJSONDataStorage {

	public String labNumber = "0";
    public String label = "Lab";
	public String accessStartDate, accessStartHour = "00", accessStartMinute = "00";
    public String accessEndDate, accessEndHour = "00", accessEndMinute = "00";
    public String caEvalStartDate, caEvalStartHour = "00", caEvalStartMinute = "00";
    public String caEvalEndDate, caEvalEndHour = "00", caEvalEndMinute = "00";
    public String pgStartDate, pgStartHour, pgStartMinute;
    public String pgEndDate, pgEndHour, pgEndMinute;

    private String getLabNumberStr() {
        return labNumber;
    } //
    public int getLabNumber() {
        return Integer.parseInt(getLabNumberStr());
    }

    public String getLabLabel() {
        return label;
    }

    public LocalDateTime getAccessStart() {
        String[] ddmmyyyy = accessStartDate.split("/");
        int day = Integer.parseInt(ddmmyyyy[0]);
        int month = Integer.parseInt(ddmmyyyy[1]);
        int year = Integer.parseInt(ddmmyyyy[2]);
        int hour = Integer.parseInt(accessStartHour == null ? "09" : accessStartHour);
        int minute = Integer.parseInt(accessStartMinute == null ? "00" : accessStartMinute);

        return LocalDateTime.of(year, month, day, hour, minute);
    }

    public LocalDateTime getAccessEnd() {
        String[] ddmmyyyy = accessEndDate.split("/");
        int day = Integer.parseInt(ddmmyyyy[0]);
        int month = Integer.parseInt(ddmmyyyy[1]);
        int year = Integer.parseInt(ddmmyyyy[2]);
        int hour = Integer.parseInt(accessEndHour);
        int minute = Integer.parseInt(accessEndMinute);

        return LocalDateTime.of(year, month, day, hour, minute);
    }

    public LocalDateTime getCAEvalStart() {
        String[] ddmmyyyy = caEvalStartDate.split("/");
        int day = Integer.parseInt(ddmmyyyy[0]);
        int month = Integer.parseInt(ddmmyyyy[1]);
        int year = Integer.parseInt(ddmmyyyy[2]);
        int hour = Integer.parseInt(caEvalStartHour);
        int minute = Integer.parseInt(caEvalStartMinute);

        return LocalDateTime.of(year, month, day, hour, minute);
    }

    public LocalDateTime getCAEvalEnd() {
        String[] ddmmyyyy = caEvalEndDate.split("/");
        int day = Integer.parseInt(ddmmyyyy[0]);
        int month = Integer.parseInt(ddmmyyyy[1]);
        int year = Integer.parseInt(ddmmyyyy[2]);
        int hour = Integer.parseInt(caEvalEndHour);
        int minute = Integer.parseInt(caEvalEndMinute);

        return LocalDateTime.of(year, month, day, hour, minute);
    }

    public LocalDateTime getPGStart() {
        String[] ddmmyyyy = pgStartDate.split("/");
        int day = Integer.parseInt(ddmmyyyy[0]);
        int month = Integer.parseInt(ddmmyyyy[1]);
        int year = Integer.parseInt(ddmmyyyy[2]);
        int hour = Integer.parseInt(pgStartHour);
        int minute = Integer.parseInt(pgStartMinute);

        return LocalDateTime.of(year, month, day, hour, minute);
    }

    public LocalDateTime getPGEnd() {
        String[] ddmmyyyy = pgEndDate.split("/");
        int day = Integer.parseInt(ddmmyyyy[0]);
        int month = Integer.parseInt(ddmmyyyy[1]);
        int year = Integer.parseInt(ddmmyyyy[2]);
        int hour = Integer.parseInt(pgEndHour);
        int minute = Integer.parseInt(pgEndMinute);

        return LocalDateTime.of(year, month, day, hour, minute);
    }


    public void print() {
        io.ECHO("Lab Label: "+ getLabLabel());
        io.ECHO("Lab Number: " + getLabNumber());
        io.ECHO("Access Start: "+ getAccessStart().toString());
        io.ECHO("Access End: "+ getAccessEnd().toString());
        io.ECHO("CA Eval Start: "+ getCAEvalStart().toString());
        io.ECHO("CA Eval End: "+ getCAEvalEnd().toString());
//        Util.ECHO("PG Start: "+ getPGStart().toString());
//        Util.ECHO("PG End: "+ getPGEnd().toString());
    }


    public String missingValues() {
//        return (
//            getLabLabel() != null &&
//            getLabNumber() != 0 &&
//            getAccessStart() != null &&
//            getAccessEnd() != null &&
//            getCAEvalStart() != null &&
//            getCAEvalEnd() != null
//        );
        String missing = null;
        if (getLabLabel() == null || getLabLabel().isBlank()) {
            missing = "Lab label not specified";
        } else if (getLabNumberStr() == null || getLabNumberStr().isBlank()) {
            missing = "Lab number not specified";
        } else if (getAccessStart() == null) {
            missing = "Lab Access start time not specified";
        } else if (getAccessEnd() == null) {
            missing = "Lab Access end time not specified";
        } else if (getCAEvalStart() == null) {
            missing = "CA start time not specified";
        } else if (getCAEvalEnd() == null) {
            missing = "CA end time not specified";
        } else if (ChronoUnit.MINUTES.between(getAccessStart(), getAccessEnd()) <= 0) { // 0 minutes difference
            missing = "Access start must be before access end.\nPlease correct the date/time";
        } else if (ChronoUnit.MINUTES.between(getCAEvalStart(), getCAEvalEnd()) <= 0) { // 0 minutes difference
            missing = "CA start must be before access end.\nPlease correct the date/time";
        }
        io.DEBUG(missing);
        return missing;
    }

}
