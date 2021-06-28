

public final class Regex {

    private String regex;
    public Regex(String regex) { this.regex = regex; }
    public String use() { return this.regex; }

    private Regex follow(String s) {
        this.regex = this.regex.concat(s);
        return this;
    }


    public static final Regex JAVA_MAIN_METHOD = new Regex("")
    .follow(".*public\\s+static\\s+void\\s+main\\s*")
    .follow("\\(\\s*String(\\s*\\[\\]\\s+\\w+|\\s+\\w+\\[\\]).*\\{.*");

    public static final Regex C_MAIN_FUNC = new Regex("")
    .follow("\\(\\s*(\\s*int\\s+\\w*\\s*\\,\\s*char\\s*")
    .follow("(\\*\\s*\\*\\s*|\\*\\s*\\[\\s*\\]\\s+)\\w+\\s*|\\s*)\\)\\s*\\{.*");
    
    public static final Regex PYTHON3_MAIN = new Regex("")
    .follow(".*if\\s+__name__\\s+==\\s+\"__main__\"\\:.*");

    public static final Regex JAVA_SYSOUT = new Regex("")
    .follow(".*System\\s*\\.\\s*out\\s*\\.\\s*print(ln|f)*\\(.*\\)\\s*\\;.*");
}

