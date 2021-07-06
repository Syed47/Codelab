# +++++++++++++++++++++++++++++++++
#! /bin/bash
cat > vpl_execution <<EEOOFF
#! /bin/bash
EXIT_CODE=-1
FAILED=false
prog1=Table
javac \${prog1}.java  &> grepLines.out
EXIT_CODE=\$?
if ((\$EXIT_CODE > 0));then
    FAILED=true
fi
if [ \$FAILED = true ]; then
    echo "Error running your program"
    cat grepLines.out
    exit
fi
java \${prog1}
if ((\$? > 0)); then
    echo "Runtime error in your code"
fi
EEOOFF
chmod +x vpl_execution
# +++++++++++++++++++++++++++++++++