import java.util.*;

class Example3 {
    public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        System.out.println(input.toUpperCase());
        System.out.println(input.substring(0,1).toUpperCase()+input.substring(1).toLowerCase());
        System.out.println("The length is " + input.length());
        for (char c : input.toUpperCase().toCharArray()) {
            System.out.print(c + "\n");
        }
        System.out.println();
    }
}