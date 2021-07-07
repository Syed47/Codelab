# +++++++++++++++++++++++++++++++++
#! /bin/bash
cat > vpl_execution <<EEOOFF
#! /bin/bash
EXIT_CODE=-1
FAILED=false
echo "Compiling DNA"
javac DNA.java &> grepLines.out
EXIT_CODE=\$?
if ((\$EXIT_CODE > 0));then
    FAILED=true
    echo "DNA failed to compile"
fi
echo "Compiling Sequence"
javac Sequence.java &> grepLines.out
EXIT_CODE=\$?
if ((\$EXIT_CODE > 0));then
    FAILED=true
    echo "Sequence failed to compile"
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