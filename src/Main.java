
public class Main {
    public static void main(String[] args) {

        // Compling and Running Java Code
        Compiler compiler = new JavaCompiler(new JavaCode("../tests"));
        Runner runner = new JavaRunner(compiler);
        Evaluator evaluator = new JavaEvaluator(runner);
        
        evaluator.specifyRegex("DNA", Regex.JAVA_MAIN_METHOD, Regex.JAVA_SYSOUT);
        evaluator.specifyRegex("Sequence", 
            new Regex(".*public\\s\\+boolean\\s\\+compareSequence\\s*\\(.*\\).*", "a compareSequence method"));

        evaluator.setCompileGrade(15);
        evaluator.setRegexGrade(45, 3);
        evaluator.setTestGrade(40, 1);

        compiler.writeScript();
        runner.writeScript();
        evaluator.writeScript(); // not implemented yet!!


        // Compling and Running C Code
        // Compiler compiler = new CCompiler(new CCode("../tests"));
        // Runner runner = new CRunner(compiler);
        // compiler.writeScript();
        // runner.writeScript();

        // Running Python3 Code
        // Runner runner = new PythonRunner(new PythonCode("../tests"));
        // runner.writeScript();

    }
}