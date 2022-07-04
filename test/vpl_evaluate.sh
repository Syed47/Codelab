#! /bin/bash
# +++++++++++++++++++++++++++++++++
cat > vpl_execution <<EEOOFF
#! /bin/bash


# ---------- PROGRAMS TESTED (WITHOUT EXTENSION) ---------
compiled=true
prog1=Example3

# --------------------- STARTING GRADE -------------------
grade=0

# --------------------- GLOBAL VARIABLES -------------------
declare -a RegexList1=("\.*for\.*" )
declare -a Comment1=("for loop out" )

# --------------------- SETTING VALUES FOR GRADES -------------------
numberOfRegex=\${#RegexList1[*]}
compileGrade=20
regexGrade=20
numberOfTestCases=2
testCasesGrade=60
regex=regexGrade/numberOfRegex
testCase=testCasesGrade/numberOfTestCases

# ----------------- COMPILE STUDENT PROG  ----------------
javac Example3.java  &> grepLines.out


#--- if error, assign a minimal grade ---
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
joe
EOF
cat > data2.txt <<EOF
word
EOF


#--- create expected outputs, one for each input file above ---
cat > data1.out <<EOF
JOE
Joe
3
j
o
e
EOF
cat > data2.out <<EOF
WORD
Word
4
w
o
r
d
EOF
declare -a ans1=('JOE')
len1=\${#ans1[*]}
declare -a ans2=('WORD')
len2=\${#ans2[*]}


count=0
if [ \${compiled} = true ] ; then
    #---loops through the amount of test cases you specified at the top ---
    for((i=1;i<=\$numberOfTestCases;i++))
    do
        echo ""
        echo "------------------------"
        java \${prog1} < data\${i}.txt &> user.out
        r=0
        ans="ans\${i}[@]"
        for reg in \${!ans}; do
            if grep -qzoP "\$reg" user.out
            then
                ((r++))
            fi
        done

        echo "---------------------"
        len="len"\$i
        if (( r == \${!len} )); then
            # Answered Correctly
            ((count++))
            echo ""
            echo "Test Case \${i} Passed"
            echo ""
            echo "Your answer:"
            cat user.out
            echo ""
            #---adds value to grade based on what % you wanted to give for testcases---
            grade=\$((grade+testCase))
        else
            # Wrong Answer
            echo \${r}
            echo "Test Case \${i} Failed"
            echo ""
            echo "Your answer:"
            cat user.out

            echo ""
            echo "Expected answer:"
            cat data\${i}.out
        fi
    done
fi

if (( count == numberOfTestCases )); then
  if (( grade < 100 )); then
    grade=100
    echo "------------------------"
    echo "As you have passed all Test Cases, you have been given full marks"
  fi
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
