package app.storage;

import app.ui.LabSessionTableData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.syed.core.io;


public class QuestionLevelJSONDataStorage {
    // local use only !!
    public LabLevelJSONDataStorage labData;

	public String questionNumber;
	public String labNumber;
	public String title;
	public String course;
	public ArrayList<String> files = new ArrayList<>();
	public String qid;

	// hidden question attributes
	public boolean hiddenQuestion;
	public String group, startDate, startHour, startMinute;
    public ArrayList<LabSessionTableData> sessions;

	public String lengthHour = "0", lengthMinute = "0";
	public LocalDateTime pgStart, pgEnd;

    public QuestionLevelJSONDataStorage(LabLevelJSONDataStorage labData) {
        this.labData = labData;
        sessions = new ArrayList<>();
    }

    private String getQuestionNumberStr() {
	    return questionNumber;
    }

    private String getLabNumberStr() {
	    return labNumber;
    }

	public int getQuestionNumber() {
	    return Integer.parseInt(questionNumber);
    }

    public int getLabNumber() {
	    return Integer.parseInt(labNumber);
    }

    public String getTitle() {
	    return title;
    }

    public String getCourse() {
	    return course;
    }

    public String[] getFiles() {
	    String[] copyFileNames = new String[files.size()];
	    int i = 0;
	    for (String f : files) { copyFileNames[i++] = f; }
	    return copyFileNames;
    }

    public String getQid() {
	    return qid; // implement
    }

    public boolean isHiddenQuestion() {
	    return hiddenQuestion;
    }

    public LocalDateTime getStart() {
        String[] ddmmyyyy = startDate.split("/");
        int day = Integer.parseInt(ddmmyyyy[0]);
        int month = Integer.parseInt(ddmmyyyy[1]);
        int year = Integer.parseInt(ddmmyyyy[2]);
        int hour = Integer.parseInt(startHour);
        int minute = Integer.parseInt(startMinute);

        return LocalDateTime.of(year, month, day, hour, minute);
    }

    public int getLength() {
	    return Integer.parseInt(lengthMinute) + (Integer.parseInt(lengthHour) * 60);
    }

    public List<LabSessionTableData> getSessions() {
	    return sessions;
    }

	public LocalDateTime getPGStart() {
	    return labData.getPGStart();
    }

	public LocalDateTime getPGEnd() {
	    return labData.getPGEnd();
    }

	public String getGroup() {
        return group;
    }

    public String missingValues() {
//	    boolean isReady = labData.isReady() &&
//                            getQuestionNumber() != 0 &&
//                            getLabNumber() != 0 &&
//                            getTitle() != null &&
//                            getCourse() != null &&
//                            getFiles() != null;
//        System.out.println("QL"+isReady);
//	    if (isHiddenQuestion()) {
//            return isReady && getLength() != 0 &&
//                    getSessions() != null &&
//                    getPGStart() != null &&
//                    getPGEnd() != null;
//        }
        String missing = null;
        String lmv = labData.missingValues();
        if (lmv != null) {
            missing = lmv;
        } else if (getQuestionNumberStr() == null || getQuestionNumberStr().isBlank() || getQuestionNumber() <= 0) {
            missing = "Question number not specified";
        } else if (getLabNumberStr() == null || getLabNumberStr().isBlank() || getLabNumber() <= 0) {
            missing = "Lab number not specified";
        } else if (getTitle() == null || getTitle().isBlank()) {
            missing = "Title is not specified";
        } else if (getCourse() == null || getCourse().isBlank()) {
            missing = "Course is not specified";
        } else if (getFiles() == null || getFiles().length == 0) {
            missing = "Code must have at-least 1 file";
        } else if (isHiddenQuestion()) {
            if (getLength() == 0) {
                missing = "Lab length cannot be 0";
            } else if (getSessions() == null || getSessions().isEmpty()) {
                missing = "Please specify lab sessions. e.g. blue";
            } else if (getPGStart() == null) {
                missing = "Specify Personal Grade start time";
            } else if (getPGEnd() == null) {
                missing = "Specify Personal Grade end time";
            }
        }

        io.DEBUG(missing);
	    return missing;
    }


    public void print() {
        io.ECHO("Question Number: "+getQuestionNumber());
        io.ECHO("Lab Number: " + getLabNumber());
        io.ECHO("Title: "+ getTitle());
        io.ECHO("Course: "+getCourse());
        io.ECHO("ID: "+ getQid());
        io.ECHO("Files: "+Arrays.toString(getFiles()));
        io.ECHO("Access Start: "+ labData.getAccessStart());
        io.ECHO("Access End: "+ labData.getAccessEnd());
        io.ECHO("CA Eval Start: "+ labData.getCAEvalStart());
        io.ECHO("CA Eval End: "+ labData.getCAEvalEnd());
        if (isHiddenQuestion()) {
            io.ECHO("Hidden: " + isHiddenQuestion());
            io.ECHO("Group: " + getGroup());
            io.ECHO("Length: " + getLength());
            io.ECHO("Hidden Q Start: " + getStart());
            io.ECHO("PG Start: " + labData.getPGStart());
            io.ECHO("PG End: " + labData.getPGEnd());
            for (LabSessionTableData s : sessions) {
                io.ECHO(s.getDate() + " | "+s.getTime());
            }
        }
    }
}

