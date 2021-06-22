import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CodeTester {

    // private String[] codeFiles;
    // private String[] regex;
    // private int compileGrade, regexGrade, testCaseGrade;
    // private int finalGrade;
    // private HashMap<String, String> codeBuffer;

    // public CodeTester(String... files) {
    //     finalGrade = compileGrade = regexGrade = testCaseGrade = 0;
    //     codeFiles = Arrays.copyOfRange(files, 0, files.length);
    //     codeBuffer = initCodeBuffer(codeFiles);
    //     printCodeBuffer();
    // }

    // public void setRegex(int n) {
    //     this.regex = new String[n];
    //     this.regexGrade = this.regexGrade/n;
    // }

    // public void setTestCases(int n) {
    //     this.testCaseGrade = this.testCaseGrade/n;
    // }

    // public void setGrades(int compile, int regex, int testCase) {
    //     this.compileGrade = compile;
    //     this.regexGrade = regex;
    //     this.testCaseGrade = testCase;
    // }

    // public void testCompile() {
    //     for (String file : codeFiles) { this.compile(file); }
    // }

    // public void testRegex(String... regexes) {
    //     if (regexes == null) {
    //         Util.ERROR("Regex are not defined (null).");
    //         return;
    //     }
    //     int grade = 0;
    //     for (String regex : regexes) {
    //         for (Map.Entry<String, String> code : codeBuffer.entrySet()) {
    //             grade += Regex.check(code.getValue(), regex) ? this.regexGrade : 0;
    //         }
    //     }
    //     this.finalGrade += grade;
    // }

    // public int getFinalGrade() {
    //     return this.finalGrade;
    // }

    // /* PRIVATE METHODS*/

    // private HashMap<String, String> initCodeBuffer(String... fileNames) {
    //     HashMap<String, String> buffer = new HashMap<>();
    //     for (String file : fileNames) {
    //         if (!buffer.containsKey(file)) {
    //             try {
    //                 String code = Util.readlines(new FileInputStream(file));
    //                 buffer.put(file, code);
    //             } catch (IOException e) {
    //                 System.err.println(e.getMessage());
    //                 System.exit(1);
    //             }
    //         }
    //     }
    //     return buffer;
    // }

    // private boolean compile(String file) {
    //     String COMPILE_CMD = String.format("javac %s", file);
    //     boolean compiled = new Shell().execute(COMPILE_CMD);
    //     this.finalGrade += compiled ? this.compileGrade : 0;
    //     return compiled;
    // }

    // private void printCodeBuffer() {
    //     for (Map.Entry<String, String> file : codeBuffer.entrySet()) {
    //         System.out.printf("[%s] ------------\n", file.getKey());
    //         System.out.println(file.getValue());
    //         System.out.println("\n-----------------\n");
    //     }
    // }
}