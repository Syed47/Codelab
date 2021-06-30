

public final class Regex {

    private String regex;
    private String comment;
    
    public Regex(String regex, String comment) {
        this.regex = regex;
        this.comment = comment;
    }

    public String use() {
        return this.regex;
    }

    public String getComment() {
        return this.comment;
    }

    private Regex follow(String s) {
        this.regex = this.regex.concat(s);
        return this;
    }

    public static final Regex JAVA_MAIN_METHOD = new Regex("", "a main method")
    .follow(".*public\\s\\+static\\s\\+void\\s\\+main\\s*")
    .follow("(\\s*String\\(\\s*\\[\\s*\\]\\s\\+\\w\\+\\|\\s\\+\\w\\+\\s*\\[\\s*\\]\\)\\{1\\}\\s*).*");

    public static final Regex C_MAIN_FUNC = new Regex("", "a main function")
    .follow("\\(\\s*(\\s*int\\s+\\w*\\s*\\,\\s*char\\s*")
    .follow("(\\*\\s*\\*\\s*|\\*\\s*\\[\\s*\\]\\s+)\\w+\\s*|\\s*)\\)\\s*\\{.*");
    
    public static final Regex PYTHON3_MAIN = new Regex("", "main statement")
    .follow(".*if\\s+__name__\\s+==\\s+\"__main__\"\\:.*");

    public static final Regex JAVA_SYSOUT = new Regex("", "a System.out.print method call")
    .follow(".*System\\s*.\\s*out\\s*.\\s*print\\(f\\|ln\\)*\\s*(.*).*");
}

