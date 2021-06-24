
public class JavaCompiler extends Compiler {

    public JavaCompiler(Code codefiles) {
        super(new Java(), codefiles, null, null);
        String mainfile = this.locateMainFile();
        this.setMainFile(mainfile);
        this.setOutFile(mainfile);
    }

    public JavaCompiler(Code codefiles, String mainfile) {
        super(new Java(), codefiles, mainfile, mainfile);
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
        if (count == 1) {
            script.append(String.format("prog1=%s\n", titles[0]));
            script.append(
                String.format(
                    "%s \\${prog1}%s  &> grepLines.out\n", 
                    this.language.getCompiler(),
                    this.language.getExtension()
                )
            );
            script.append("EXIT_CODE=\\$?\n");
            script.append("if ((\\$EXIT_CODE > 0));then\n");
            script.append("    FAILED=true\n");
            script.append(String.format("    echo \"%s failed to compile\"\n", titles[0]));
            script.append("fi\n");
        } else {
            for (String title : titles) {
                script.append(String.format("echo \"Compiling %s\"\n", title));
                script.append(
                    String.format(
                        "%s %s%s &> grepLines.out\n", 
                        this.language.getCompiler(), 
                        title, 
                        this.language.getExtension()
                    )
                );
                script.append("EXIT_CODE=\\$?\n");
                script.append("if ((\\$EXIT_CODE > 0));then\n");
                script.append("    FAILED=true\n");
                script.append(String.format("    echo \"%s failed to compile\"\n", title));
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
}
