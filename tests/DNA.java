import java.util.Scanner;


public class DNA {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String child = sc.nextLine();
        String p1 = sc.nextLine();
        String p2 = sc.nextLine();

        String output = new Sequence().compareSequence(child, p1, p2) ? "Mutation" : "No Mutation";
        System.out.println(output + " detected!");
    }
}
