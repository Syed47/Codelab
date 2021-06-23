
public abstract class Language {

    private String compiler;
    private String runner;
    private String extension;

    protected Language(String compiler, String runner, String ext) {
        this.compiler = compiler;
        this.runner = runner;
        this.extension = ext;
    }

    protected String getCompiler()  { return this.compiler;  }
    protected String getRunner()    { return this.runner;    }
    protected String getExtension() { return this.extension; }

}

class Java extends Language { Java() { super("javac", "java", ".java"); } }
class C extends Language { C() { super("gcc", "./", ".c"); } }
class Python3 extends Language { Python3() { super(null, "python3", ".py"); } }