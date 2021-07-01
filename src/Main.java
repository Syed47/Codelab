
public class Main {
    public static void main(String[] args) {

        // Compling and Running Java Code
        Compiler compiler = new JavaCompiler(new JavaCode("../tests"));
        Runner runner = new JavaRunner(compiler);
        Evaluator evaluator = new JavaEvaluator(runner);
        
        evaluator.setCompileGrade(10);
        evaluator.setRegexGrade(30, 3);
        evaluator.setTestGrade(60, 3);
        
        evaluator.specifyRegex("DNA", Regex.JAVA_MAIN_METHOD, Regex.JAVA_SYSOUT);
        evaluator.specifyRegex("Sequence", 
            new Regex(".*public\\s\\+boolean\\s\\+compareSequence\\s*\\(.*\\).*", "a compareSequence method"));

        evaluator.setTestData("johndoe\njohndoe\njohnnoe\n", "No Mutation detected!\n");
        evaluator.setTestData("syed\nabcd\nefgh\n", "Mutation detected!\n");
        evaluator.setTestData("test\ntae0\n1est\n", "No Mutation detected!\n");

        compiler.writeScript();
        runner.writeScript();
        evaluator.writeScript();

    }
}