#! /bin/bash

cat > vpl_execution <<EEOOFF
#! /bin/bash


# ---------- PROGRAMS TESTED (WITHOUT EXTENSION) ---------
compiled=true
prog1=BubbleSort

# --------------------- STARTING GRADE -------------------
grade=0


# --------------------- GLOBAL VARIABLES -------------------
declare -a RegexList1=(".*public\s\+static\s\+void\s\+main\s*(\s*String\(\s*\[\s*\]\s\+\w\+\|\s\+\w\+\s*\[\s*\]\)\{1\}\s*).*" ".*System\s*.\s*out\s*.\s*print\(f\|ln\)*\s*(.*).*" ".*class\s\+BubbleSort\s\+.*" )
declare -a Comment1=("a main method" "a System.out.print method call" "a BubbleSort class" )

# --------------------- SETTING VALUES FOR GRADES -------------------
numberOfRegex=\${#RegexList1[*]}
compileGrade=10
regexGrade=30
numberOfTestCases=3
testCasesGrade=60
regex=regexGrade/numberOfRegex
testCase=testCasesGrade/numberOfTestCases

# ----------------- COMPILE STUDENT PROG  ----------------
javac \${prog1}.java &> grepLines.out


#--- if error, assign a mi&imal grade ---
if ((\$? > 0)); then
     echo "------------------------"
     echo "Your program has compiler Errors. Please fix these Errors."
     cat grepLines.out
     echo "------------------------"
     compiled=false
fi

if grep -q '.*' \${prog1}.java
then
    if [ \${compiled} = true ] ; then
        grade=\$((grade+compileGrade))
    fi
fi


# ----------- Remove single line comments from code -------------
sed -i 's|//.*||g' \${prog1}.java

# ----------- TEST THE CODE FOR PARTICULAR PATTERNS -------------

# ----------- TEST Code -------------
c=0
for reg in \${RegexList1[*]}; do
    if grep -q \$reg \${prog1}.java
    then
        tempRegExGrade=\$((tempRegExGrade+regex))
        grade=\$((grade+regex))
    else
        echo "------------------------"
        echo "You have not \${Comment1[\$c]} in" \${prog1}.java
    fi
    ((c++))
done


# --- create test input files ---
cat > data1.txt <<EOF
5
3
2
4
1
EOF
cat > data2.txt <<EOF
11
9
7
5
3
1
EOF
cat > data3.txt <<EOF
-1
-2
-3
-4
-5
EOF


#--- create expected outputs, one for each input file above ---
cat > data1.out <<EOF
1
2
3
4
5
EOF
cat > data2.out <<EOF
1
3
5
7
9
11
EOF
cat > data3.out <<EOF
-5
-4
-3
-2
-1
EOF

count=0
if [ \${compiled} = true ] ; then
    #---loops through the amount of test cases you specified at the top ---
    for((i=1;i<=\$numberOfTestCases;i++))
    do
       java BubbleSort < data\${i}.txt &> user.out

       #--- compute difference ---
       echo "---------------------"
       diff -y -w --ignore-all-space user.out data\${i}.out > diff.out

       #--- reject if different ---
       if ((\$? > 0)); then
          # Wrong Answer
          echo "Test Case \${i} Failed"

          echo ""
          echo "Your answer:"
          cat user.out

          echo ""
          echo "Expected answer:"
          cat data\${i}.out
       else
          # Answered Correctly
          echo ""
          echo "Test Case \${i} Passed"

          echo ""
          echo "Your answer:"
          cat user.out

          #---adds value to grade based on what % you wanted to give for testcases---
          grade=\$((grade+testCase))
          ((count++))
       fi
    done
fi

if (( count == numberOfTestCases )); then
    echo "------------------------"
    echo "As you have passed all Test Cases, you have been given full marks"
fi

if (( grade > 100 )); then
    grade=100
fi

if (( grade < 1 )); then
    grade=0
fi

echo "------------------------"
echo ""
echo "Grade :=>> \$grade"

EEOOFF

chmod +x vpl_execution