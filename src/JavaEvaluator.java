
public class JavaEvaluator extends Evaluator {
    
    JavaEvaluator(Runner runner) {
        super(runner);
    }

    @Override
    protected String scriptify() {
        if (this.getCode().getCount() == 0) {
            Util.ERROR("0 files cannot be evaluated");
            return null;
        }
        // Script variables
        String extension = this.getCode().getLanguage().getExtension();
        String[] titles = this.getFileTitles();
        int N = titles.length;

        StringBuilder script = new StringBuilder();

        script.append("#! /bin/bash\n");
        script.append("\n");
        script.append("cat > vpl_execution <<EEOOFF\n");
        script.append("#! /bin/bash\n");
        script.append("\n");

        script.append("\n# ---------- PROGRAMS TESTED (WITHOUT EXTENSION) ---------\n");
        script.append("compiled=true\n");

        for (int i = 0; i < N; i++) {
            script.append(String.format("prog%d=%s\n", i+1, titles[i]));    
        }
        script.append("\n# --------------------- STARTING GRADE -------------------\n");
        script.append("grade=0\n");
        script.append("\n");  

        script.append("\n# --------------------- GLOBAL VARIABLES -------------------\n");

        for (int i = 0; i < N; i++) {
            script.append(String.format("declare -a RegexList%d=(", i+1));
            for (Regex regex : this.regex.get(titles[i])) {
                script.append("\""+regex.use()+"\" ");
            }
            script.append(")\n");
            script.append(String.format("declare -a Comment%d=(", i+1));
            for (Regex regex : this.regex.get(titles[i])) {
                script.append("\""+regex.getComment()+"\" ");
            }
            script.append(")\n");
        }     

        script.append("\n# --------------------- SETTING VALUES FOR GRADES -------------------\n");
        script.append("numberOfRegex=");
        for (int i = 0; i < N; i++) {
            script.append(String.format("\\${#RegexList%s[*]}", i+1));
            if (i != N-1) { script.append("+"); }
        }
        script.append("\n");
        script.append(String.format("compileGrade=%d\n", this.cmplGrade.getTotal()));
        script.append(String.format("regexGrade=%d\n", this.regGrade.getTotal()));
        script.append(String.format("numberOfTestCases=%d\n", this.tcGrade.getCount()));
        script.append(String.format("testCasesGrade=%d\n", this.tcGrade.getTotal()));
        script.append("regex=regexGrade/numberOfRegex\n");
        script.append("testCase=testCasesGrade/numberOfTestCases\n");

        script.append("\n# ----------------- COMPILE STUDENT PROG  ----------------\n");
        script.append("javac ");
        for (int i = 0; i < N; i++) {
            script.append(String.format("\\${prog%d}%s ", i+1, extension));
        }
        script.append("&> grepLines.out\n");

        script.append("\n");
        script.append("\n#--- if error, assign a mi&imal grade ---\n");
        script.append("if ((\\$? > 0)); then\n");
        script.append("     echo \"------------------------\"\n");
        script.append("     echo \"Your program has compiler Errors. Please fix these Errors.\"\n");
        script.append("     cat grepLines.out\n");
        script.append("     echo \"------------------------\"\n");
        script.append("     compiled=false\n");
        script.append("fi\n");
        script.append("\n");
        script.append("if grep -q '.*' \\${prog1}.java\n");
        script.append("then\n");
        script.append("    if [ \\${compiled} = true ] ; then\n");
        script.append("        grade=\\$((grade+compileGrade))\n");
        script.append("    fi\n");
        script.append("fi\n");
        script.append("\n");

        script.append("\n# ----------- Remove single line comments from code -------------\n");
        for (int i = 0; i < N; i++) {
            script.append(String.format("sed -i 's|//.*||g' \\${prog%d}%s\n", i+1, extension));
        }

        script.append("\n# ----------- TEST THE CODE FOR PARTICULAR PATTERNS -------------\n");
        script.append("\n# ----------- TEST Code -------------\n");
        for (int i  = 0; i < N; i++) {
            script.append("c=0\n");
            script.append(String.format("for reg in \\${RegexList%d[*]}; do\n", i+1));
            script.append(String.format("    if grep -q \\$reg \\${prog%d}%s\n",i+1, extension));
            script.append("    then\n");
            script.append("        tempRegExGrade=\\$((tempRegExGrade+regex))\n");
            script.append("        grade=\\$((grade+regex))\n");
            script.append("    else\n");
            // script.append("        echo \\$reg\n"); // !!
            script.append("        echo \"------------------------\"\n");
            script.append(String.format(
                "        echo \"You have not \\${Comment%d[\\$c]} in\" \\${prog%d}%s\n", 
                i+1, i+1, extension)
            );
            script.append("    fi\n");
            script.append("    ((c++))\n");
            script.append("done\n");
            script.append("\n");
        }

        script.append("\n# --- create test input files ---\n");
        script.append("cat > data1.txt <<EOF\n");
        script.append("syed\n");
        script.append("syed\n");
        script.append("syed\n");
        script.append("EOF\n");
        script.append("\n");
        script.append("\n#--- create expected outputs, one for each input file above ---\n");
        script.append("cat > data1.out <<EOF\n");
        script.append("No Mutation detected!\n");
        script.append("EOF\n");
        script.append("\n");
        script.append("count=0\n");
        script.append("if [ \\${compiled} = true ] ; then\n");
        script.append("    #---loops through the amount of test cases you specified at the top ---\n");
        script.append("    for((i=1;i<=\\$numberOfTestCases;i++))\n");
        script.append("    do\n");
        script.append("       java \\${prog1} < data1.txt &> user.out\n");
        script.append("\n");
        script.append("       #--- compute difference ---\n");
        script.append("       echo \"---------------------\"\n");
        script.append("       diff -y -w --ignore-all-space user.out data1.out > diff.out\n");
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
        script.append("if (( count == numberOfTestCases )); then\n");
        // script.append("  if (( grade < 100 )); then\n");
        // script.append("    grade=100\n");
        script.append("    echo \"------------------------\"\n");
        script.append("    echo \"As you have passed all Test Cases, you have been given full marks\"\n");
        // script.append("  fi\n");
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
}
