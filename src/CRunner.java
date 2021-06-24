import java.util.Map;

public class CRunner extends Runner {
    
    public CRunner(Code codefiles, String mainfile, String runfile) {
        super(new C(), codefiles, mainfile, runfile);
    }

    public CRunner(Code codefiles, String runfile) {
        super(new C(), codefiles, null, runfile);
        this.setMainFile(this.getMainFile());
    }

    public CRunner(Code codefiles) {
        super(new C(), codefiles, null, "a.out");
        this.setMainFile(this.getMainFile());
    }
    
    @Override
    public String scriptify() {
        if (this.codefiles.getCount() == 0) {
            Util.ERROR("Cannot run 0 files");
            return null;
        }
        String compiler = this.language.getCompiler();
        String runner = this.language.getRunner();
        String extension = this.language.getExtension();

        String compileFmt = "%s \\${prog1}%s -o %s &> grepLines.out\n";
        String runFmt = "%s\\${run1}\n";

        StringBuilder script = new StringBuilder();
        script.append("# +++++++++++++++++++++++++++++++++\n");
        script.append("#! /bin/bash\n");
        script.append("cat > vpl_execution <<EEOOFF\n");
        script.append("#! /bin/bash\n");
        script.append("EXIT_CODE=-1\n");
        script.append("FAILED=false\n");
        script.append(String.format("prog1=%s\n", this.mainfile));
        script.append(String.format("run1=%s\n", this.runfile));
        script.append(String.format(compileFmt, compiler, extension, this.runfile));
        script.append("EXIT_CODE=\\$?\n");
        script.append("if ((\\$EXIT_CODE > 0));then\n");
        script.append("    FAILED=true\n");
        script.append("fi\n");
        script.append("if [ \\$FAILED = true ]; then\n");
        script.append("    echo \"Error running your program\"\n");
        script.append("    cat grepLines.out\n");
        script.append("    exit\n");
        script.append("fi\n");
        script.append(String.format(runFmt, runner));
        script.append("if ((\\$? > 0)); then\n");
        script.append("    echo \"Runtime error in your code\"\n");
        script.append("fi\n");
        script.append("EEOOFF\n");
        script.append("chmod +x vpl_execution\n");
        script.append("# +++++++++++++++++++++++++++++++++");
        return script.toString();
    }
    
    @Override 
    public void writeScript() {
        Util.writeToFile(
            String.format(
                "%s%s", 
                this.codefiles.getBasePath(), 
                "vpl_run.sh"
            ), 
            this.scriptify()
        );
    }

    @Override
    public Language getLanguage() {
        return this.language;
    }

    @Override
    public String getRunFile() {
        return this.runfile;
    }

    @Override
    public void setMainFile(String file) {
        this.mainfile = file;
    }

    @Override
    public void setRunFile(String file) {
        this.runfile = file;
    }

    @Override
    public String getMainFile() {
        String regex = ""+
            ".*int\\s+main\\s*"+
            "\\(\\s*(\\s*int\\s+\\w*\\s*\\,\\s*char\\s*(\\*\\s*\\*\\s*|\\*\\s*\\[\\s*\\]\\s+)\\w+\\s*|\\s*)\\)\\s*\\{.*";

        for (Map.Entry<String, String> pair : this.codefiles.getFileTree().entrySet()) {
            if (Util.checkRegex(regex, pair.getValue())) {
                String name = pair.getKey();
                Util.DEBUG("MAIN FILE = "+name);
                return name.substring(0, name.indexOf("."));
            }
        }
        Util.ERROR("NO file with main method found");
        return null;
    }

}
