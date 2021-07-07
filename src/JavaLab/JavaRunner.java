package JavaLab;

import core.Util;
import core.CodeCompiler;
import core.CodeRunner;

public class JavaRunner extends CodeRunner {

    public JavaRunner(JavaCompiler compiler) {
        super(compiler);
    }

    @Override
    protected String scriptify() {
        if (this.getCompiler().getCode().getCount() == 0) {
            Util.ERROR("Cannot run 0 files");
            return null;
        }
        String compiler  = this.getLanguage().getCompiler();
        String runner    = this.getLanguage().getRunner();
        String extension = this.getLanguage().getExtension();

        String compileFmt = "%s \\${prog1}%s  &> grepLines.out\n";
        String runFmt = "%s \\${prog1}\n";

        StringBuilder script = new StringBuilder();
        script.append("# +++++++++++++++++++++++++++++++++\n");
        script.append("#! /bin/bash\n");
        script.append("cat > vpl_execution <<EEOOFF\n");
        script.append("#! /bin/bash\n");
        script.append("EXIT_CODE=-1\n");
        script.append("FAILED=false\n");
        script.append(String.format("prog1=%s\n", this.getRunFile())); // same as mainfile in this case
        script.append(String.format(compileFmt, compiler, extension));
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
}