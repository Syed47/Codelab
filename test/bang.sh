rm vpl_execution
rm *.class
chmod +x *.sh
echo " << COMPILE >> "
./vpl_compile.sh && ./vpl_execution
echo " << RUN >> "
./vpl_run.sh && ./vpl_execution < "aaa"
echo " << EVALUATE >> "
./vpl_evaluate.sh && ./vpl_execution
echo " << STOP >> "
