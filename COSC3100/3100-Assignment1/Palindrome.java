
public class Palindrome {
	
	public static void main(String args[]) {
		testPalindrome("C");
		testPalindrome("DAD");
		testPalindrome("RACECAR");
		testPalindrome("TEXTBOOK");
	}
	
	public static boolean palindrome(String s) {
		s = s.toLowerCase();
		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) != s.charAt(s.length() - (i + 1))) {
				return false;
			}
		}
		return true;
	}
	
	public static void testPalindrome(String s) {
		System.out.println(s + ": " + palindrome(s));
	}
}
