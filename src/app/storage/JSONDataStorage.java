package app.storage;

import com.syed.core.io;

public class JSONDataStorage {

    public CourseLevelJSONDataStorage courseData = new CourseLevelJSONDataStorage();
    public LabLevelJSONDataStorage labData = new LabLevelJSONDataStorage();
    public QuestionLevelJSONDataStorage questionData = new QuestionLevelJSONDataStorage(labData);


    public String missingValues() {
//        boolean course = courseData.missingValues() == null,
//        lab = labData.missingValues() == null,
//        question = questionData.missingValues() == null;
//
//        System.out.println(course);
//        System.out.println(lab);
//        System.out.println(question);
//        return course && lab && question;
        String missing = null;
        String course = courseData.missingValues(),
                lab = labData.missingValues(),
                question = questionData.missingValues();
        if (course != null) {
            missing = course;
        } else if (lab != null) {
            missing = lab;
        } else if (question != null) {
            missing = question;
        }
        io.DEBUG(missing);
        return missing;
    }

    public void print() {
        io.ECHO("------------------\n");
        courseData.print();
        io.ECHO("------------------\n");
        labData.print();
        io.ECHO("------------------\n");
        questionData.print();
        io.ECHO("------------------\n");
    }
}

