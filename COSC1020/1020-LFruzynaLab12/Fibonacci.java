import java.util.Scanner;

public class Fibonacci {

	public static void main(String[] args) {
		/*
		//get amount of numbers to do
		Scanner kb = new Scanner(System.in);
		System.out.print("Enter count: ");
		int count = kb.nextInt();
		
		//calculate each number in sequence
		for(int i = 1; i <= count; i++) {
			System.out.print(fib(i) + ", ");
		}*/

		System.out.println("Fib of 5: " + fib(5));
		System.out.println("Fib of 15: " + fib(15));
		System.out.println("Fib of 25: " + fib(25));
	}

	public static long fib(int count) {
		if(count <= 2) {
			//if 2 or less its reached the end
			return 1;
		}
		else {
			//calculate the next number off of the last 2
			return fib(count - 1) + fib(count - 2);
		}
	}
}
