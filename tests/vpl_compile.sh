# +++++++++++++++++++++++++++++++++
#! /bin/bash
cat > vpl_execution <<EEOOFF
#! /bin/bash
EXIT_CODE=-1
FAILED=false
echo "Compiling DNA.java"
javac DNA.java &> grepLines.out
EXIT_CODE=\$?
if ((\$EXIT_CODE > 0));then
    FAILED=true
    echo "DNA.java failed to compile"
fi
echo "Compiling Sequence.java"
javac Sequence.java &> grepLines.out
EXIT_CODE=\$?
if ((\$EXIT_CODE > 0));then
    FAILED=true
    echo "Sequence.java failed to compile"
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