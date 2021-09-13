import lib.core.*;
import lib.javalab.*;
import lib.clab.*;
import lib.pythonlab.*;

public class Main {

    private static String outputPath = "./tests";

    public static void main(String[] args) {
        if (args.length > 0 && args[0] != null) {
            Util.DEBUG("Program Args #="+args.length);
            outputPath = "./"+args[0];
            Util.createPathIfNotAlready(outputPath);
        }
        Test.JAVA(outputPath);
        Test.C(outputPath);
        Test.PYTHON3( outputPath);
    }

    private static class Test {
        static void JAVA(String path) {
            Code labcode = new JavaCode(path);
            CodeCompiler compiler = new JavaCompiler((JavaCode) labcode);
            CodeRunner runner = new JavaRunner((JavaCompiler) compiler);
            CodeEvaluator evaluator = new JavaEvaluator((JavaRunner) runner);

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

            evaluator.setTestData("johndoe\njohndoe\njohnnoe", "No Mutation detected!");
            evaluator.setTestData("syed\nabcd\nefgh", "Mutation detected!");
            evaluator.setTestData("jajja\nhajja\niaiia", "Mutation detected!");
            evaluator.setTestData("test\ntae0\n1est", "No Mutation detected!");
    
            compiler.writeScript();
            runner.writeScript();
            evaluator.writeScript();
        }
    
        // Compling, Running and Evaluating C Code
        static void C(String path) {
            Code code = new CCode(path);
            CodeCompiler compiler = new CCompiler((CCode) code, "run");
            CodeRunner runner = new CRunner((CCompiler) compiler);
            CEvaluator evaluator = new CEvaluator((CRunner) runner);
    
            evaluator.setCompileGrade(30);
            evaluator.setRegexGrade(40, 4);
            evaluator.setTestGrade(40, 4);
    
            evaluator.specifyRegex("main", 
                new Regex(".*int\\s\\+main\\s*(.*).*", "a main function"), 
                new Regex(".*sayHello\\s*(.*).*", "a sayHello method call")
            );
    
            evaluator.specifyRegex("util", new Regex(".*printf\\s*(.*).*", "a printf call"));
            evaluator.specifyRegex("util", new Regex(".*void\\s*sayHello\\s*(.*)\\s*{.*", "a sayHello function"));
    
            evaluator.setTestData("syed", "Welcome syed.");
            evaluator.setTestData("mule", "Welcome mule.");
            evaluator.setTestData("john", "Welcome john.");
            evaluator.setTestData("water", "Welcome water.");
    
            compiler.writeScript();
            runner.writeScript();
            evaluator.writeScript();
        } 
    
        // Running and Evaluating Python3 Code
        static void PYTHON3(String path) {
            Code code = new PythonCode(path);
            CodeRunner runner = new PythonRunner((PythonCode) code, "app");
            PythonEvaluator evaluator = new PythonEvaluator((PythonRunner) runner);
    
            evaluator.setCompileGrade(30);
            evaluator.setRegexGrade(40, 4);
            evaluator.setTestGrade(40, 4);
    
            evaluator.specifyRegex("app", 
                new Regex(".*def\\s\\+main\\s*(.*).*", "a main function"), 
                new Regex(".*print\\s*(.*).*", "a print call")
            );
    
            evaluator.specifyRegex("libr", new Regex(".*class\\s\\+Person.*", "a Person class"));
            evaluator.specifyRegex("libr", new Regex(".*def\\s\\+__init__\\s*(.*).*", "a constructor"));
    
            evaluator.setTestData("syed", "Name = syed.");
            evaluator.setTestData("mule", "Name = mule.");
            evaluator.setTestData("john", "Name = john.");
            evaluator.setTestData("water", "Name = water.");
    
            runner.writeScript();
            evaluator.writeScript();  
        }
    }
}