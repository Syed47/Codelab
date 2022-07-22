package com.syed.core;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class CodeEvaluator {

    private final Code code;
    private final CodeRunner runner;
    private final HashMap<String, ArrayList<Regex>> regex;
    private final ArrayList<TestCase> testCase;
    private final Test test;
    private Grade cmplGrade, regGrade, tcGrade;
    public CodeEvaluator(CodeRunner runner, Test test) {
        this.runner = runner;
        this.test = test;
        this.code = this.runner.getCompiler().getCode();
        this.regex = new HashMap<>();
        this.testCase = new ArrayList<>();
        for (String name : this.getFileTitles()) {
            regex.put(name, new ArrayList<>());
        }
    }

    public void setTestCases(TestCase... tests) {
        for (TestCase test : tests) {
            if (test.getInput() == null || test.getOutput() == null) {
                io.ERROR("Test input and/or output cannot be null");
                return;
            }
            this.testCase.add(test);
        }
    }

    public void specifyRegex(Regex... regexes) {
        if (this.getCode().getCount() > 0) {
            this.specifyRegex(this.code.getMainFile(), regexes);
        }
    }

    public void specifyRegex(String filetitle, Regex... regexes) {
        if (filetitle.endsWith(this.code.getLanguage().getExtension())) {
            filetitle = io.fileTitle(filetitle);
        }
        if (!this.regex.containsKey(filetitle)) {
            io.ERROR(filetitle + " is not in the code files" );
            return;
        }
        for (Regex r : regexes) { 
            this.regex.get(filetitle).add(r); 
        }
    }

    public ArrayList<TestCase> getTests() {
        return new ArrayList<>(this.testCase);
    }

    public Test getTest() {
        return this.test;
    }

    public String[] getFileTitles() {
        return this.code.getFileTitles();
    }

    public void setCompileGrade(int grade) {
        this.cmplGrade = new Grade(grade, 1);
    }

    public void setRegexGrade(int total, int count) {
        this.regGrade = new Grade(total, count);
    }

    public void setTestGrade(int total, int count) {
        this.tcGrade = new Grade(total, count);
    }

    public Code getCode() {
        return this.code;
    }

    public ArrayList<TestCase> getTestIOs() {
        return this.testCase;
    }

    public HashMap<String, ArrayList<Regex>> getRegex() {
        return this.regex;
    }

    public Grade getCmplGrade() {
        return this.cmplGrade;
    }

    public Grade getRegGrade() {
        return this.regGrade;
    }

    public Grade getTcGrade() {
        return this.tcGrade;
    }

    public String mainfile() {
        return this.code.getMainFile();
    }

    public String runfile() {
        return this.runner.getRunFile();
    }

    public void writeScript() {
        this.writeScript(this.code.getBasePath()+"vpl_evaluate.sh");
    }

    public void writeScript(String to) {
        io.writeToFile(to, this.scriptify());
    }

    // implement for every evaluator
    protected abstract String scriptify();
}