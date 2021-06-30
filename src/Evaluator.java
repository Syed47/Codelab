import java.util.ArrayList;
import java.util.HashMap;

public abstract class Evaluator {

    protected final Code code;
    protected final HashMap<String, ArrayList<Regex>> regex;
    protected final Runner runner;
    protected Grade cmplGrade, regGrade, tcGrade;

    protected Evaluator(Runner runner) {
        this.runner = runner;
        this.code = this.runner.getCompiler().getCode();
        this.regex = new HashMap<>();
        for (String name : this.getFileTitles()) {
            regex.put(name, new ArrayList<>());
        }
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

