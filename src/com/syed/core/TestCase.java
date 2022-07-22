package com.syed.core;

public class TestCase {
    private final String input;
    private final String output;
//    private List<Regex> comparisons;
    public TestCase(String in, String out) {
        this.input = in;
        this.output = out;
//        this.comparisons = new ArrayList<>();
//        System.out.println(Arrays.toString(comparisons) + " " + comparisons.length);
//        if (comparisons.length == 0) {
//            this.comparisons.addAll(Arrays.asList(comparisons));
//        }
    }

    public String getInput() { return this.input; }
    public String getOutput() { return this.output; }

//    public List<Regex> getComparisons() {
//        return this.comparisons;
//    }

}


