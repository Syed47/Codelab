package app.storage;

import java.util.*;

import com.syed.core.Regex;
import com.syed.core.Test;
import com.syed.core.TestCase;
import com.syed.core.io;


public class ScriptsDataStorage {

    public String labLanguage;
    public String mainFile;
    public String compileGrade, regexGrade, tcGrade;
    public ArrayList<String> files;
    public HashMap<String, String> code;
    public HashMap<String, List<Regex>> regexes;
    public ArrayList<TestCase> testCaseIOs;

    public Test test;

    public ScriptsDataStorage(QuestionLevelJSONDataStorage questionData) {
        files = new ArrayList<>();
        code = new HashMap<>();
        regexes = new HashMap<>();
        testCaseIOs = new ArrayList<>();
        Collections.addAll(files, questionData.getFiles());
//        questionData.print();
//        System.out.println(Arrays.toString(files.toArray(new String[0])));
    }

    public Test getTest() {
        return this.test;
    }

    public String missingValues() {
//        return  getLabLanguage() != null && getMainFile() != null &&
//                getCompileGrade() + getRegexGrade() + getTCGrade() > 0 &&
//                files.size() > 0 &&
//                regexes != null &&
//                testCaseIOs.size() > 0;
        String missing = null;
        if (getLabLanguage() == null) {
            missing = "Language is not selected for this question";
        } else if (files.isEmpty()) {
            missing = "You haven't specified code files \n(This can done in Metadata section)";
        } else if (getMainFile() == null || getMainFile().isBlank() || getMainFile().equals("Filename.ext")) { // Filename.ext check might not be required
            missing = "Main (entry point) not specified";
        } else if (regexes == null ) {
            missing = "[Error] regex is null!";
        } else if (getTotalGrade() != 100) {
            missing = "Grade(s) must sum to 100";
        }
        io.DEBUG(missing);
        return missing;
    }


    public String getLabLanguage() {
        return labLanguage;
    }

    public String getMainFile() {
        if (files.size() == 1) {
            mainFile = files.get(0);
        }
        return mainFile;
    }

    public String[] getCodeFiles() {
        String[] copy = new String[files.size()];
        int i = 0;
        for (String f : files) { copy[i++] = f; }
        return copy;
    }

    public Map<String, String> getCode() {
        // cleaning before returning
        for (Map.Entry<String, String> fc : code.entrySet()) {
            boolean exist = false;
            for (String f : getCodeFiles()) {
                if (f.equals(fc.getKey())) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                code.remove(fc.getKey());
            }
        }
        return code;
    }

    public Map<String, List<Regex>> getRegex() {
        return regexes;
    }

    public ArrayList<TestCase> getTestCaseIOs() {
        return testCaseIOs;
    }

    public int getCompileGrade() {
        int v = 0;
        try { v = Integer.parseInt(compileGrade.trim()); }
        catch (Exception ignored) {}
        return v;
    }

    public int getRegexGrade() {
        int v = 0;
        try { v = Integer.parseInt(regexGrade.trim()); }
        catch (Exception ignored) {}
        return v;
    }

    public int getTCGrade() {
        int v = 0;
        try { v = Integer.parseInt(tcGrade.trim()); }
        catch (Exception ignored) {}
        return v;
    }


    public int getTotalGrade() {
        return getCompileGrade() + getRegexGrade() + getTCGrade();
    }

    public void print() {
        io.ECHO("Lab Language: "+getLabLanguage());
        io.ECHO("MainFile: " + getMainFile());
        io.ECHO("Compile grade: "+ getCompileGrade());
        io.ECHO("Regex grade: "+ getRegexGrade());
        io.ECHO("TestCase grade: "+ getTCGrade());
        io.ECHO("Code Files: "+Arrays.toString(getCodeFiles()));
        io.ECHO("[Code]:");
        for (Map.Entry<String, String> p : getCode().entrySet()) {
            io.ECHO(p.getKey() + ": "+p.getValue());
        }
        io.ECHO("[Regex]:");
        for (Map.Entry<String, List<Regex>> p : getRegex().entrySet()) {
            io.ECHO("\t["+p.getKey() + "]:");
            for (Regex r : p.getValue()) {
                io.ECHO("\t\t"+r.getComment());
                io.ECHO("\t\t"+r.use());
            }
        }
        io.ECHO("TestCase IO:");
        for (TestCase tio : getTestCaseIOs()) {
            io.ECHO("\nInput:\n"+tio.getInput() + "\nOutput:\n"+tio.getOutput());
        }
        io.ECHO("\n------------------------------\n");
    }

}
