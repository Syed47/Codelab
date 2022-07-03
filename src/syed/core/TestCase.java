package syed.core;

import javax.annotation.processing.SupportedAnnotationTypes;
import java.util.*;

public class TestIO {
    private final String input;
    private final String output;
    private List<Regex> comparisons;

    public TestIO(String in, String out) {
        this.input = in;
        this.output = out;
    }

    public TestIO(String in, String out, Regex... comparisons) {
        this(in, out);
        this.comparisons = new ArrayList<>();
        this.comparisons.addAll(Arrays.asList(comparisons));
    }

    public String getInput() { return this.input; }
    public String getOutput() { return this.output; }

    public List<Regex> getComparisons() {
        return this.comparisons;
    }

}


