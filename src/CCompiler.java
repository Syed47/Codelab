
public class CCompiler extends Compiler {

    public CCompiler(Code codefiles) {
        this(codefiles, "a.out");
    }

    public CCompiler(Code codefiles, String outfile) {
        super(new C(), codefiles, outfile);
    }

    public CCompiler(Code codefiles, String mainfile, String outfile) {
        super(new C(), codefiles, mainfile, outfile);
    }

    @Override
    public String scriptify() {
        final int count = this.codefiles.getCount();
        if (count == 0) {
            Util.ERROR("Cannnot compile 0 files");
            return null;
        }

        StringBuilder script = new StringBuilder();
        script.append("# +++++++++++++++++++++++++++++++++\n");
        script.append("#! /bin/bash\n");
        script.append("cat > vpl_execution <<EEOOFF\n");
        script.append("#! /bin/bash\n");
        script.append("EXIT_CODE=-1\n");
        script.append("FAILED=false\n");

        script.append(String.format("prog1=%s\n", this.mainfile));

        script.append(
            String.format(
                "%s \\${prog1}%s -o %s &> grepLines.out\n", 
                this.language.getCompiler(),
                this.language.getExtension(),
                this.outfile
            )
        );
        script.append("EXIT_CODE=\\$?\n");
        script.append("if ((\\$EXIT_CODE > 0));then\n");
        script.append("    FAILED=true\n");
        script.append(
            String.format("    echo \"%s failed to compile\"\n", this.mainfile)
        );
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
        return script.toString();
    }
}
