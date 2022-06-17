import syed.core.Regex;
import syed.core.Test;
import syed.core.TestIO;
import syed.javalab.JavaCode;
import syed.javalab.JavaCompiler;
import syed.javalab.JavaEvaluator;
import syed.javalab.JavaRunner;

public class Main {
    public static void main(String[] args) {
        System.out.println(javatest());
    }

    public static boolean javatest() {
        JavaCode code = new JavaCode("test");
        JavaCompiler compiler = new JavaCompiler(code);
        JavaRunner runner = new JavaRunner(compiler);
        JavaEvaluator evaluator = new JavaEvaluator(runner);

        evaluator.setCompileGrade(20);
        evaluator.setRegexGrade(40, 2);
        evaluator.setTestGrade(60, 3);

        evaluator.specifyRegex("Main", new Regex("\\.*println\\.*", "print out"));
        evaluator.specifyRegex("Side", new Regex("\\.*for\\.*", "for loop out"));

//        evaluator.setTest("2\n3", "4");
//        evaluator.setTest("3\n9", "9");
//        evaluator.setTest("5", "25");

        evaluator.setTest(new TestIO("2", "4"));
        evaluator.setTest(new TestIO("4", "16", Test.MULTI_LINE));
        evaluator.setTest(new TestIO("3", "9", Test.CASE_SENSITIVE));

        compiler.writeScript("test/vpl_compile.sh");
        runner.writeScript("test/vpl_run.sh");
        evaluator.writeScript("test/vpl_evaluate.sh");

        return true;
    }
}
