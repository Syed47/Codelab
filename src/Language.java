
public abstract class Language {

    private String compiler;
    private String runner;
    private String extension;
    private String mainRegex;

    protected Language(String cmplr, String rnr, String ext, String mr) {
        this.compiler = cmplr;
        this.runner = rnr;
        this.extension = ext;
        this.mainRegex = mr;
    }

    protected String getCompiler()  { return this.compiler;  }
    protected String getRunner()    { return this.runner;    }
    protected String getExtension() { return this.extension; }
    protected String getMainRegex() { return this.mainRegex; }

}

class Java extends Language { 
    Java() { 
        super(
            "javac", 
            "java", 
            ".java", 
            ".*public\\s+static\\s+void\\s+main\\s*" +
            "\\(\\s*String(\\s*\\[\\]\\s+\\w+|\\s+\\w+\\[\\]).*\\{.*"
        ); 
    } 
}

class C extends Language { 
    C() { 
        super(
            "gcc", 
            "./", 
            ".c",
            ".*int\\s+main\\s*" +
            "\\(\\s*(\\s*int\\s+\\w*\\s*\\,\\s*char\\s*" +
            "(\\*\\s*\\*\\s*|\\*\\s*\\[\\s*\\]\\s+)\\w+\\s*|\\s*)\\)\\s*\\{.*"
        ); 
    } 
}
class Python3 extends Language { 
    Python3() { 
        super(
            null, 
            "python3", 
            ".py",
            ".*if\\s+__name__\\s+==\\s+\"__main__\"\\:.*"
        ); 
    }
}
