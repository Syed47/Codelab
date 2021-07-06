import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Evaluator {

    protected static class TestIO { 
        String input, output;
        TestIO(String in, String out) { this.input = in; this.output = out; }
    }

    protected final Code code;
    protected final Runner runner;
    protected final HashMap<String, ArrayList<Regex>> regex;
    protected Grade cmplGrade, regGrade, tcGrade;
    protected ArrayList<TestIO> testIO;

    protected Evaluator(Runner runner) {
        this.runner = runner;
        this.code = this.runner.getCompiler().getCode();
        this.regex = new HashMap<>();
        this.testIO = new ArrayList<>();
        for (String name : this.getFileTitles()) {
            Util.DEBUG(name);
            regex.put(name, new ArrayList<>());
        }
    }

    protected void setTestData(String input, String output) {
        if (input == null || output == null) {
            Util.ERROR("Test input and/or output cannot be null");
            return;
        }
        this.testIO.add(new TestIO(input, output));
    }

    protected void setTestData(File input, File output) {
        this.setTestData(
            Util.readlines(input.getAbsolutePath()), 
            Util.readlines(output.getAbsolutePath())
        );
    }

    protected void specifyRegex(Regex... regexes) {
        if (this.getCode().getCount() > 0) {
            this.specifyRegex(this.code.getMainFile(), regexes);
        }
    }
    
    protected void specifyRegex(String filetitle, Regex... regexes) {
        if (filetitle.endsWith(this.code.getLanguage().getExtension())) {
            filetitle = Util.fileTitle(filetitle);
        }
        if (!this.regex.containsKey(filetitle)) {
            Util.ERROR(filetitle + " is not in the code files" );
            return;
        }
        for (Regex r : regexes) { 
            this.regex.get(filetitle).add(r); 
        }
        Util.ECHO("all regex added");
    }

    protected ArrayList<TestIO> getTests() {
        ArrayList<TestIO> copy = new ArrayList<>();
        this.testIO.stream().forEach(test -> copy.add(test));
        return copy;
    }

    protected String[] getFileTitles() {
        return this.code.getFileTitles();
    }

    protected void setCompileGrade(int grade) {
        this.cmplGrade = new Grade(grade, 1);
    }

    protected void setRegexGrade(int total, int count) {
        this.regGrade = new Grade(total, count);
    }

    protected void setTestGrade(int total, int count) {
        this.tcGrade = new Grade(total, count);
    }

    protected Code getCode() {
        return this.code;
    }

    protected String mainfile() {
        return this.code.getMainFile();
    }

    protected String runfile() {
        return this.runner.getRunFile();
    }
   protected void writeScript() {
        this.writeScript(this.code.getBasePath()+"vpl_evaluate.sh");
    }

    protected void writeScript(String to) {
        Util.writeToFile(to, this.scriptify());
    }

    // implement for every evaluator
    protected abstract String scriptify();
}