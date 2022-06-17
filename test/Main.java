import java.util.Scanner;


public class Main {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int n = Integer.parseInt(sc.nextLine());
		System.out.println(power2(n));
		
	}

	public static int power2(int n) {
		return n * n;
	}

}