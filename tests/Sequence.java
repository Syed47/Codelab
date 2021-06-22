public class Sequence {
    public Sequence() {}
    public boolean compareSequence(String child, String p1, String p2) {
        int L = Math.min(child.length(), Math.min(p1.length(), p2.length()));
        for (int i = 0; i < L; i++) {
            char C = child.charAt(i);
            if (C != p1.charAt(i) && C != p2.charAt(i)) 
                return true;
        }
        return false;
    }
}
