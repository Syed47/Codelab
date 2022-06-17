package syed.core;

import javax.annotation.processing.SupportedAnnotationTypes;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;

public class TestIO {
    private final String input;
    private final String output;
    private EnumSet<Test> methods;

    public TestIO(String in, String out) {
        this(in, out, Test.SINGLE_LINE, Test.MULTI_LINE);
    }

    public TestIO(String in, String out, Test ...methods) {
        this.input = in;
        this.output = out;
        this.methods = EnumSet.noneOf(Test.class);
        Collections.addAll(this.methods, methods);
    }

    public String getInput() { return this.input; }
    public String getOutput() { return this.output; }

    public EnumSet<Test> getMethods() {
        return methods;
    }
}


