
public class Compiler {
    public enum Options {
        JAVA { @Override public String toString() { return "javac"; } },
        C { @Override public String toString() { return "gcc"; } }
    }
    private String basePath;
    private String[] files;
    private Options compiler;
    private boolean compiled;
    private java.util.EnumMap<Options, String> langs; // if i have to use other compilers

    public Compiler(Options compiler, String basePath) {
        this.basePath = basePath;
        this.compiler = compiler;
        this.compiled = false;
        this.langs = new java.util.EnumMap<>(Options.class);
        this.langs.put(Options.JAVA, ".java");
        this.langs.put(Options.C, ".c");
        this.files = Util.getFilePaths(this.basePath, this.langs.get(this.compiler));
    }

    public String scriptify() {
        if (this.files.length == 0) Util.ERROR("Cannnot compile 0 files");
        String[] codeFiles = Util.getFileNames(this.basePath, this.langs.get(this.compiler));
        StringBuilder script = new StringBuilder();
        script.append("# +++++++++++++++++++++++++++++++++\n");
        script.append("#! /bin/bash\n");
        script.append("cat > vpl_execution <<EEOOFF\n");
        script.append("#! /bin/bash\n");
        script.append("EXIT_CODE=-1\n");
        script.append("FAILED=false\n");
        if (codeFiles.length == 1) {
            script.append(String.format("prog1=%s\n", codeFiles[0]));
            script.append(String.format("%s \\${prog1}  &> grepLines.out\n", this.compiler));
            script.append("EXIT_CODE=\\$?\n");
            script.append("if ((\\$EXIT_CODE > 0));then\n");
            script.append("    FAILED=true\n");
            script.append(String.format("    echo \"%s failed to compile\"\n", codeFiles[0]));
            script.append("fi\n");
        } else {
            for (String file : codeFiles) {
                script.append(String.format("echo \"Compiling %s\"\n", file));
                script.append(
                    String.format("%s %s &> grepLines.out\n", this.compiler, file)
                );
                script.append("EXIT_CODE=\\$?\n");
                script.append("if ((\\$EXIT_CODE > 0));then\n");
                script.append("    FAILED=true\n");
                script.append(String.format("    echo \"%s failed to compile\"\n", file));
                script.append("fi\n");
            }
        }
        script.append("if [ \\$FAILED = true ]; then\n");
        script.append("    echo \"Error compiling your program\"\n");
        script.append("    cat grepLines.out\n");
        script.append("    exit\n");
        script.append("else\n");
        script.append("    echo \"Compilation succeeded\"\n");
        script.append("    echo \"compiled: ==> true\"\n");
        script.append("fi\n");
        script.append("EEOOFF\n");
        script.append("chmod +x vpl_execution\n");
        script.append("# +++++++++++++++++++++++++++++++++");
        return script.toString();
    }

    public void writeScript() {
        Util.writeToFile(String.format("%s%s", this.basePath, "vpl_compile.sh"), scriptify());
    }

    // public boolean run() {
    //     for (String file : this.files) {
    //         String command = String.format("%s %s", this.compiler, file);
    //         Util.DEBUG(String.format("[Executed]: %s\n", command));
    //         this.compiled = new Shell().execute(command);
    //     }
    //     return this.compiled;
    // }

    public String getBasePath() {
        return this.basePath;
    }

    public String[] getFiles() {
        return java.util.Arrays.copyOfRange(this.files, 0, this.files.length);
    }
}
