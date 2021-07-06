public class Sequence {
    public Sequence() {}
    public boolean compareSequence(String child, String p1, String p2) {
        int L = minStr(child, p1, p2)[0];
        for (int i = 0; i < L; i++) {
            char C = child.charAt(i);
            if (C != p1.charAt(i) && C != p2.charAt(i)) 
                return true;
        }
        return false;
    }

    public static int[] minStr(String s1, String s2, String s3) {
        return new int[] {Math.min(s1.length(), Math.min(s2.length(), s3.length()))};
    }

    public static int minString(String[] s) {
        int min = Integer.MAX_VALUE;
        for (String ss : s) {
            if (ss.length() < min) {
                min = ss.length();
            }
        }
        return min;
    }
}
