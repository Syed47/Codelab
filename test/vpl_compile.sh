#! /bin/bash
# +++++++++++++++++++++++++++++++++
cat > vpl_execution <<EEOOFF
#! /bin/bash
EXIT_CODE=-1
FAILED=false
prog1=Example3
javac \${prog1}.java  &> grepLines.out
EXIT_CODE=\$?
if ((\$EXIT_CODE > 0));then
    FAILED=true
    echo "Example3 failed to compile"
fi
if [ \$FAILED = true ]; then
    echo "Error compiling your program"
    cat grepLines.out
    exit
else
    echo "Compilation succeeded"
    echo "compiled: ==> true"
fi
EEOOFF
chmod +x vpl_execution
# +++++++++++++++++++++++++++++++++