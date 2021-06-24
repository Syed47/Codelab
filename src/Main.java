
public class Main {
    public static void main(String[] args) {

        Code javacode = new Code("../tests", ".java");
        new JavaCompiler(javacode).writeScript();
        new JavaRunner(javacode).writeScript();

        Code ccode = new Code("../tests", ".c");
        new CCompiler(ccode).writeScript();
        new CRunner(ccode).writeScript();

    }
}