
public class CCompiler extends Compiler {

    private String runfile;

    public CCompiler(Code codefiles) {
        super(new C(), codefiles);
        this.runfile = "a.out";
    }

    public CCompiler(Code codefiles, String runfile) {
        super(new C(), codefiles);
        this.runfile = runfile;
    }

    @Override
    public String scriptify() {
        final int count = this.codefiles.getCount();
        if (count == 0) {
            Util.ERROR("Cannnot compile 0 files");
            return null;
        }
        String[] titles = this.codefiles.getFileTitles();
        StringBuilder script = new StringBuilder();
        script.append("# +++++++++++++++++++++++++++++++++\n");
        script.append("#! /bin/bash\n");
        script.append("cat > vpl_execution <<EEOOFF\n");
        script.append("#! /bin/bash\n");
        script.append("EXIT_CODE=-1\n");
        script.append("FAILED=false\n");

        script.append(String.format("prog1=%s\n", titles[0]));

        script.append(
            String.format(
                "%s \\${prog1}%s -o %s &> grepLines.out\n", 
                this.language.getCompiler(),
                this.language.getExtension(),
                this.runfile
            )
        );
        script.append("EXIT_CODE=\\$?\n");
        script.append("if ((\\$EXIT_CODE > 0));then\n");
        script.append("    FAILED=true\n");
        script.append(String.format("    echo \"%s failed to compile\"\n", titles[0]));
        script.append("fi\n");

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
        return script.toString();    }

    @Override
    public void writeScript() {
        Util.writeToFile(
            String.format(
                "%s%s", 
                this.codefiles.getBasePath(), 
                "vpl_compile.sh"
            ), 
            this.scriptify()
        );
    }

    @Override
    public Language getLanguage() {
        return this.language;
    }
    
    public String getRunFile() {
        return this.runfile;
    }

}
