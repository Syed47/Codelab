
public class Main {
    public static void main(String[] args) {

        // Compling and Running Java Code
        Compiler compiler = new JavaCompiler(new JavaCode("../tests"));
        Runner runner = new JavaRunner(compiler);
        compiler.writeScript();
        runner.writeScript();


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