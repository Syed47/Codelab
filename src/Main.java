
public class Main {
    public static void main(String[] args) {

        final String path = "../tests/";
        Code code = new Code(path, ".java");
        code.print();
        code.getFilePaths();

        Compiler compiler = new Compiler(Compiler.Options.JAVA, code);
        compiler.writeScript();

    }
}


 

/*
    Shell shell = new Shell();
    try {
        shell.runCmd();
    } catch (IOException | InterruptedException e) {
        e.printStackTrace();
    }

    Scanner sc = new Scanner(System.in);
    System.out.println("child received: "+sc.nextLine());
*/