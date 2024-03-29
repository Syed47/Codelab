package app.storage;

import com.syed.core.io;

import java.util.ArrayList;
import java.util.Arrays;

public class HTMLDataStorage {

    public String css;
    public String description;
    public ArrayList<String> imagesURLs;
    public ArrayList<String> notes;
    public ArrayList<String> codeSamples;
    public int typeOfOutput; // 0 1 2
    public ArrayList<String> sampleInputs;
    public ArrayList<String> sampleOutputs;
    public String singleExpectedOutput;

    public HTMLDataStorage() {
        css = CSS.INFO_PAGE;
        description = "";
        imagesURLs = new ArrayList<>();
        notes = new ArrayList<>();
        codeSamples = new ArrayList<>();
        typeOfOutput = -1;
        sampleInputs = new ArrayList<>();
        sampleOutputs = new ArrayList<>();
        singleExpectedOutput = "";
    }


    public String missingValues() {
//        boolean ready = getDescription() != null &&
//                        getTypeOfOutput() != -1 &&
//                        getImagesUrls() != null &&
//                        getNotes() != null &&
//                        getCodeSamples() != null;
//
//        if (singleExpectedOutput.isBlank()) {
//            return ready && getSampleInputs().length > 0 && getSampleOutputs().length > 0;
//        }
//        return ready;
        String missing = null;
        if (getDescription() == null || getDescription().isBlank()) {
            missing = "Description is not provided";
        } else if (getSampleInputs().length <= 0 || getSampleInputs().length <= 0) {
            missing = "Sample input/output not specified";
        }

//        } else if (getAccessStart() == null) {
//            missing = "Lab Access start time not specified";
//        } else if (getAccessEnd() == null) {
//            missing = "Lab Access end time not specified";
//        } else if (getCAEvalStart() == null) {
//            missing = "CA start time not specified";
//        } else if (getCAEvalEnd() == null) {
//            missing = "CA end time not specified";
//        }
        io.DEBUG(missing);
        return missing;

    }

    public void print() {
        io.ECHO("----------------------------\n");
        io.ECHO("Description: "+getDescription());
        io.ECHO("TypeOfOutput: "+getTypeOfOutput());
        io.ECHO("SingleOutput: "+getSingleExpectedOutput());
        io.ECHO("Images: "+ Arrays.toString(getImagesUrls()));
        io.ECHO("Notes: "+ Arrays.toString(getNotes()));
        io.ECHO("CodeSamples: "+ Arrays.toString(getCodeSamples()));
        io.ECHO("Sample Inputs: "+ Arrays.toString(getSampleInputs()));
        io.ECHO("Sample Outputs: "+ Arrays.toString(getSampleOutputs()));
        io.ECHO("----------------------------\n");

    }

    public String getCss() {
        return css;
    }

    public String getDescription() {
        return description;
    }

    public int getTypeOfOutput() {
        return typeOfOutput;
    }

    public String getSingleExpectedOutput() {
        return singleExpectedOutput;
    }

    public String[] getImagesUrls() {
        String[] urls = new String[imagesURLs.size()];
        int i = 0;
        for (String url : imagesURLs) { urls[i++] = url; }
        return urls;
    }

    public String[] getNotes() {
        String[] n = new String[notes.size()];
        int i = 0;
        for (String url : notes) { n[i++] = url; }
        return n;
    }

    public String[] getCodeSamples() {
        String[] n = new String[codeSamples.size()];
        int i = 0;
        for (String url : codeSamples) { n[i++] = url; }
        return n;
    }

    public String[] getSampleInputs() {
        String[] n = new String[sampleInputs.size()];
        int i = 0;
        for (String url : sampleInputs) { n[i++] = url; }
        return n;
    }

    public String[] getSampleOutputs() {
        String[] n = new String[sampleOutputs.size()];
        int i = 0;
        for (String url : sampleOutputs) { n[i++] = url; }
        return n;
    }
}
