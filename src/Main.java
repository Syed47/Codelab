
public class Main {
    public static void main(String[] args) {

        Code codefiles = new Code("../tests", ".java");
        new JavaCompiler(codefiles).writeScript();
        new JavaRunner(codefiles).writeScript();
    
    }
}