package com.syed.javalab;

import com.syed.core.*;


public class JavaEvaluator extends CodeEvaluator {

    public JavaEvaluator(JavaRunner runner, Test test) {
        super(runner, test);
    }

    @Override
    protected String scriptify() {
        if (this.getCode().getCount() == 0) {
            io.ERROR("0 files cannot be evaluated");
            return null;
        }
        // Script variables
        String extension = this.getCode().getLanguage().getExtension();
        String compiler = this.getCode().getLanguage().getCompiler();
        String runner = this.getCode().getLanguage().getRunner();
        String[] titles = this.getFileTitles();
        int N = titles.length, index = 2;

        StringBuilder script = new StringBuilder();

        script.append("#! /bin/bash\n");
        script.append("# +++++++++++++++++++++++++++++++++\n");
        script.append("cat > vpl_execution <<EEOOFF\n");
        script.append("#! /bin/bash\n");
        script.append("\n");

        script.append("\n# ---------- PROGRAMS TESTED (WITHOUT EXTENSION) ---------\n");
        script.append("compiled=true\n");

        script.append(String.format("prog1=%s\n", io.fileTitle(this.mainfile())));
        for (String value : titles) {
            if (!value.equals(this.mainfile())) {
                script.append(String.format("prog%d=%s\n", index, value));
                index++;
            }
        }

        script.append("\n# --------------------- STARTING GRADE -------------------\n");
        script.append("grade=0\n");
        script.append("\n# --------------------- GLOBAL VARIABLES -------------------\n");

        script.append(String.format("declare -a RegexList%d=(", 1));
        for (Regex regex : this.getRegex().get(io.fileTitle(this.mainfile()))) {
            script.append("\"").append(regex.use()).append("\" ");
        }

        script.append(")\n");
        script.append(String.format("declare -a Comment%d=(", 1));
        for (Regex regex : this.getRegex().get(io.fileTitle(this.mainfile()))) {
            script.append("\"").append(regex.getComment()).append("\" ");
        }
        script.append(")\n");

        index = 2;
        for (String s : titles) {
            if (!s.equals(this.mainfile())) {
                script.append(String.format("declare -a RegexList%d=(", index));
                for (Regex regex : this.getRegex().get(s)) {
                    script.append("\"").append(regex.use()).append("\" ");
                }
                script.append(")\n");
                script.append(String.format("declare -a Comment%d=(", index));
                for (Regex regex : this.getRegex().get(s)) {
                    script.append("\"").append(regex.getComment()).append("\" ");
                }
                script.append(")\n");
                index++;
            }
        }

        script.append("\n# --------------------- SETTING VALUES FOR GRADES -------------------\n");
        script.append("numberOfRegex=");
        for (int i = 0; i < N; i++) {
            script.append(String.format("\\${#RegexList%s[*]}", i + 1));
            if (i != N - 1) {
                script.append("+");
            }
        }
        script.append("\n");
        script.append(String.format("compileGrade=%d\n", this.getCmplGrade().getTotal()));
        script.append(String.format("regexGrade=%d\n", this.getRegGrade().getTotal()));
        script.append(String.format("numberOfTestCases=%d\n", this.getTcGrade().getCount()));
        script.append(String.format("testCasesGrade=%d\n", this.getTcGrade().getTotal()));
        script.append("regex=regexGrade/numberOfRegex\n");
        script.append("testCase=testCasesGrade/numberOfTestCases\n");

        script.append("\n# ----------------- COMPILE STUDENT PROGRAM  ----------------\n");
        script.append(compiler).append(" ");
        for (String title : titles) {
            script.append(String.format("%s%s ", title, extension));
        }
        script.append(" &> grepLines.out\n");

        script.append("\n");
        script.append("\n#--- if error, assign a minimal grade ---\n");
        script.append("if ((\\$? > 0)); then\n");
        script.append("     echo \"------------------------\"\n");
        script.append("     echo \"Your program has compiler Errors. Please fix these Errors.\"\n");
        script.append("     cat grepLines.out\n");
        script.append("     echo \"------------------------\"\n");
        script.append("     compiled=false\n");
        script.append("fi\n");
        script.append("\n");
        script.append(String.format("if grep -q '.*' \\${prog1}%s\n", extension));
        script.append("then\n");
        script.append("    if [ \\${compiled} = true ] ; then\n");
        script.append("        grade=\\$((grade+compileGrade))\n");
        script.append("    fi\n");
        script.append("fi\n");
        script.append("\n");

        script.append("\n# ----------- Remove single line comments from code -------------\n");
        for (int i = 1; i <= N; i++) {
            script.append(String.format("sed -i 's|//.*||g' \\${prog%d}%s\n", i, extension));
        }

        script.append("\n# ----------- TEST THE CODE FOR PARTICULAR PATTERNS -------------\n");
        script.append("\n# ----------- TEST Code -------------\n");
        for (int i = 0; i < N; i++) {
            script.append("c=0\n");
            script.append(String.format("for reg in \\${RegexList%d[*]}; do\n", i + 1));
            script.append(String.format("    if grep -q \\$reg \\${prog%d}%s\n", i + 1, extension));
            script.append("    then\n");
            script.append("        tempRegExGrade=\\$((tempRegExGrade+regex))\n");
            script.append("        grade=\\$((grade+regex))\n");
            script.append("    else\n");
            script.append("        echo \"------------------------\"\n");
            script.append(String.format(
                    "        echo \"You have not \\${Comment%d[\\$c]} in\" \\${prog%d}%s\n",
                    i + 1, i + 1, extension)
            );
            script.append("    fi\n");
            script.append("    ((c++))\n");
            script.append("done\n");
            script.append("\n");
        }

        script.append("\n# --- create test input files ---\n");
        for (int i = 0; i < this.getTests().size(); i++) {
            script.append(String.format("cat > data%d.txt <<EOF\n", i + 1));
            script.append(this.getTestIOs().get(i).getInput());
            script.append("\nEOF\n");
        }

        script.append("\n");
        script.append("\n#--- create expected outputs, one for each input file above ---\n");

        for (int i = 0; i < this.getTests().size(); i++) {
            script.append(String.format("cat > data%d.out <<EOF\n", i + 1));
            script.append(this.getTestIOs().get(i).getOutput());
            script.append("\nEOF\n");
        }


        script.append(getGradingScript(getTest()));

        script.append("\n");
        script.append("if (( count == numberOfTestCases )); then\n");
        script.append("  if (( grade < 100 )); then\n");
        script.append("    grade=100\n");
        script.append("    echo \"------------------------\"\n");
        script.append("    echo \"As you have passed all Test Cases, you have been given full marks\"\n");
        script.append("  fi\n");
        script.append("fi\n");
        script.append("\n");
        script.append("if (( grade > 100 )); then\n");
        script.append("    grade=100\n");
        script.append("fi\n");
        script.append("\n");
        script.append("if (( grade < 1 )); then\n");
        script.append("    grade=0\n");
        script.append("fi\n");

        script.append("\n");
        script.append("echo \"------------------------\"\n");
        script.append("echo \"\"\n");
        script.append("echo \"Grade :=>> \\$grade\"\n");
        script.append("\n");

        script.append("EEOOFF\n");
        script.append("\n");
        script.append("chmod +x vpl_execution\n");

        return script.toString();

    }

    private String getGradingScript(Test test) {
        StringBuilder script = new StringBuilder();
        switch(test) {
            case NON_EXACT, NON_EXACT_CASE_SENSITIVE -> {
                script.append("\n");
                script.append(formattedAnswersFor(test));
                script.append("\n");
                script.append("count=0\n");
                script.append("if [ \\${compiled} = true ] ; then\n");
                script.append("    #---loops through the amount of test cases you specified at the top ---\n");
                script.append("    for((i=1;i<=\\$numberOfTestCases;i++))\n");
                script.append("    do\n");
                script.append("        echo \"\"\n");
                script.append("        echo \"------------------------\"\n");
                script.append("        java \\${prog1} < data\\${i}.txt &> user.out\n");
                script.append("        r=0\n");
                script.append("        ans=\"ans\\${i}[@]\"\n");
                script.append("        for reg in \\${!ans}; do\n");
                script.append(String.format("            if grep %s \"\\$reg\" user.out\n", grepOptionsFor(test)));
                script.append("            then\n");
                script.append("                ((r++))\n");
                script.append("            fi\n");
                script.append("        done\n");
                script.append("\n");
                script.append("        echo \"---------------------\"\n");
                script.append("        len=\"len\"\\$i\n");
                script.append("        if (( r == \\${!len} )); then\n");
                script.append("            # Answered Correctly\n");
                script.append("            ((count++))\n");
                script.append("            echo \"\"\n");
                script.append("            echo \"Test Case \\${i} Passed\"\n");
                script.append("            echo \"\"\n");
                script.append("            echo \"Your answer:\"\n");
                script.append("            cat user.out\n");
                script.append("            echo \"\"\n");
                script.append("            #---adds value to grade based on what % you wanted to give for testcases---\n");
                script.append("            grade=\\$((grade+testCase))\n");
                script.append("        else\n");
                script.append("            # Wrong Answer\n");
                script.append("            echo \\${r}\n");
                script.append("            echo \"Test Case \\${i} Failed\"\n");
                script.append("            echo \"\"\n");
                script.append("            echo \"Your answer:\"\n");
                script.append("            cat user.out\n");
                script.append("\n");
                script.append("            echo \"\"\n");
                script.append("            echo \"Expected answer:\"\n");
                script.append("            cat data\\${i}.out\n");
                script.append("        fi\n");
                script.append("    done\n");
                script.append("fi\n");
            }
            case EXACT, EXACT_CASE_SENSITIVE -> {
                script.append("\n\n");
                script.append("count=0\n");
                script.append("if [ \\${compiled} = true ] ; then\n");
                script.append("    #---loops through the amount of test cases you specified at the top ---\n");
                script.append("    for((i=1;i<=\\$numberOfTestCases;i++))\n");
                script.append("    do\n");
                script.append(String.format("       java %s < data\\${i}.txt &> user.out\n", this.runfile()));
                script.append("\n");
                script.append("       #--- compute difference ---\n");
                script.append("       echo \"---------------------\"\n");
                script.append(String.format("       diff -y %s user.out data\\${i}.out > diff.out\n", grepOptionsFor(test)));
                script.append("\n");
                script.append("       #--- reject if different ---\n");
                script.append("       if ((\\$? > 0)); then\n");
                script.append("          # Wrong Answer\n");
                script.append("          echo \"Test Case \\${i} Failed\"\n");
                script.append("\n");
                script.append("          echo \"\"\n");
                script.append("          echo \"Your answer:\"\n");
                script.append("          cat user.out\n");
                script.append("\n");
                script.append("          echo \"\"\n");
                script.append("          echo \"Expected answer:\"\n");
                script.append("          cat data\\${i}.out\n");
                script.append("       else\n");
                script.append("          # Answered Correctly\n");
                script.append("          echo \"\"\n");
                script.append("          echo \"Test Case \\${i} Passed\"\n");
                script.append("\n");
                script.append("          echo \"\"\n");
                script.append("          echo \"Your answer:\"\n");
                script.append("          cat user.out\n");
                script.append("\n");
                script.append("          #---adds value to grade based on what % you wanted to give for testcases---\n");
                script.append("          grade=\\$((grade+testCase))\n");
                script.append("          ((count++))\n");
                script.append("       fi\n");
                script.append("    done\n");
                script.append("fi\n");
                script.append("\n");
            }
        }


        return script.toString();
    }
    private String grepOptionsFor(Test test) {
        switch (test) {
            case NON_EXACT -> { return "-qzoPi"; }
            case NON_EXACT_CASE_SENSITIVE -> { return "-qzoP"; }
            case EXACT -> { return "-i -w --ignore-all-space"; }
            case EXACT_CASE_SENSITIVE -> { return "-w --ignore-all-space"; }
            default -> { return ""; }
        }
    }

    private String formattedAnswersFor(Test test) {
        StringBuilder script = new StringBuilder();
        switch (test) {
            case NON_EXACT, NON_EXACT_CASE_SENSITIVE -> {
                for (int i = 0; i < this.getTests().size(); i++) {
                    script.append(String.format("declare -a ans%d=(", i + 1));
                    String[] lines = this.getTests().get(i).getOutput().split("\n");
                    for (String line : lines) {
                        script.append("'").append(line).append("' ");
                    }
                    if (lines.length != 0) {
                        script.deleteCharAt(script.length()-1); // removing extra space
                    }
                    script.append(")\n");
                    script.append(String.format("len%d=\\${#ans%d[*]}\n", i + 1, i + 1));
                }
                return script.toString();
            }

            default -> {
                return "";
            }
        }



    }
}
