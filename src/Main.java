import java.io.File;

public class Main {
    public static void main(String[] args) {
        Test.JAVA("../tests");
        // Test.C("../tests");
        // Test.PYTHON3("../tests");

    }

    private static class Test {
        static void JAVA(String path) {
            Code labcode = new JavaCode(path);
            Compiler compiler = new JavaCompiler((JavaCode)labcode);
            Runner runner = new JavaRunner(compiler);
            Evaluator evaluator = new JavaEvaluator(runner);
    
            evaluator.setCompileGrade(10);
            evaluator.setRegexGrade(30, 2);
            evaluator.setTestGrade(60, 4);
    
            evaluator.specifyRegex("DNA", 
                new Regex(".*public\\s\\+static\\s\\+void\\s\\+main\\s*(.*).*", 
                "a main method")
            );
            evaluator.specifyRegex("Sequence", 
                new Regex(".*public\\s\\+boolean\\s\\+compareSequence\\s*(.*).*", 
                "a compare sequence method")
            );
    
            // evaluator.setTestData(
            //     new File(labcode.getBasePath()+"data1.txt"), 
            //     new File(labcode.getBasePath()+"data1.out")
            // );
            // evaluator.setTestData(
            //     new File(labcode.getBasePath()+"data2.txt"), 
            //     new File(labcode.getBasePath()+"data2.out")
            // );
            // evaluator.setTestData(
            //     new File(labcode.getBasePath()+"data3.txt"), 
            //     new File(labcode.getBasePath()+"data3.out")
            // );
            // evaluator.setTestData(
            //     new File(labcode.getBasePath()+"data4.txt"), 
            //     new File(labcode.getBasePath()+"data4.out")
            // );
    
            evaluator.setTestData("johndoe\njohndoe\njohnnoe\n", "No Mutation detected!\n");
            evaluator.setTestData("syed\nabcd\nefgh\n", "Mutation detected!\n");
            evaluator.setTestData("jajja\nhajja\niaiia\n", "Mutation detected!\n");
            evaluator.setTestData("test\ntae0\n1est\n", "No Mutation detected!\n");
    
            compiler.writeScript();
            runner.writeScript();
            evaluator.writeScript();
        }
    
        // Compling, Running and Evaluating C Code
        static void C(String path) {
            Code code = new CCode(path);
            Compiler compiler = new CCompiler((CCode)code, "run");
            Runner runner = new CRunner(compiler);
            CEvaluator evaluator = new CEvaluator(runner);
    
            evaluator.setCompileGrade(30);
            evaluator.setRegexGrade(40, 4);
            evaluator.setTestGrade(40, 4);
    
            evaluator.specifyRegex("main", 
                new Regex(".*int\\s\\+main\\s*(.*).*", "a main function"), 
                new Regex(".*sayHello\\s*(.*).*", "a sayHello method call")
            );
    
            evaluator.specifyRegex("util", new Regex(".*printf\\s*(.*).*", "a printf call"));
            evaluator.specifyRegex("util", new Regex(".*void\\s*sayHello\\s*(.*)\\s*{.*", "a sayHello function"));
    
            evaluator.setTestData("syed\n", "Welcome syed.\n");
            evaluator.setTestData("mule\n", "Welcome mule.\n");
            evaluator.setTestData("john\n", "Welcome john.\n");
            evaluator.setTestData("water\n", "Welcome water.\n");
    
            compiler.writeScript();
            runner.writeScript();
            evaluator.writeScript();
        } 
    
        // Running and Evaluating Python3 Code
        static void PYTHON3(String path) {
            PythonCode code = new PythonCode(path);
            Runner runner = new PythonRunner(code, "app");
            PythonEvaluator evaluator = new PythonEvaluator(runner);
    
            evaluator.setCompileGrade(30);
            evaluator.setRegexGrade(40, 4);
            evaluator.setTestGrade(40, 4);
    
            evaluator.specifyRegex("app", 
                new Regex(".*def\\s\\+main\\s*(.*).*", "a main function"), 
                new Regex(".*print\\s*(.*).*", "a print call")
            );
    
            evaluator.specifyRegex("libr", new Regex(".*class\\s\\+Person.*", "a Person class"));
            evaluator.specifyRegex("libr", new Regex(".*def\\s\\+__init__\\s*(.*).*", "a constructor"));
    
            evaluator.setTestData("syed\n", "Name = syed.\n");
            evaluator.setTestData("mule\n", "Name = mule.\n");
            evaluator.setTestData("john\n", "Name = john.\n");
            evaluator.setTestData("water\n", "Name = water.\n");
    
            runner.writeScript();
            evaluator.writeScript();  
        }
    }
    
    // Compling, Running and Evaluating Java Code

}