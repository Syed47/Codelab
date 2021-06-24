
public class Main {
    public static void main(String[] args) {

        // Compling and Running Java Code
        Code code = new Code("../tests", ".java");
        Compiler compiler = new JavaCompiler(code);
        Runner runner = new JavaRunner(compiler);
        compiler.writeScript();
        runner.writeScript();


        // Compling and Running C Code
        // Code code = new Code("../tests", ".c");
        // Compiler compiler = new CCompiler(code);
        // Runner runner = new CRunner(compiler);
        // compiler.writeScript();
        // runner.writeScript();

    }
}