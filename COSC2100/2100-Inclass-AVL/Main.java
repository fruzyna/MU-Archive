

public class Main {
	public static void main(String[] args){
		Main pp= new Main();
		int[] digits = {2,2,2};
		
		pp.phoneCodes(digits, 0, new char[digits.length]);
	}
	public void phoneCodes(int[] digits,int pointerDigit,char[] answer){
		
		if(digits.length-pointerDigit==0){
			for(int i =0; i<pointerDigit;i++){
				System.out.print("" + answer[i]);
			}
			System.out.print("\t");
			return;
		}
		else
		{
			if(digits[pointerDigit] == 0)
			{
				answer[pointerDigit] = '0';
				phoneCodes(digits, pointerDigit + 1, answer);
			}
			else if(digits[pointerDigit] == 1)
			{
				answer[pointerDigit] = '1';
				phoneCodes(digits, pointerDigit + 1, answer);
			}
			else if(digits[pointerDigit] == 2)
			{
				answer[pointerDigit] = 'a';
				phoneCodes(digits, pointerDigit + 1, answer);
				answer[pointerDigit] = 'b';
				phoneCodes(digits, pointerDigit + 1, answer);
				answer[pointerDigit] = 'c';
				phoneCodes(digits, pointerDigit + 1, answer);
			}
			else if(digits[pointerDigit] == 3)
			{
				answer[pointerDigit] = 'd';
				phoneCodes(digits, pointerDigit + 1, answer);
				answer[pointerDigit] = 'e';
				phoneCodes(digits, pointerDigit + 1, answer);
				answer[pointerDigit] = 'f';
				phoneCodes(digits, pointerDigit + 1, answer);
			}
			else if(digits[pointerDigit] == 4)
			{
				answer[pointerDigit] = 'g';
				phoneCodes(digits, pointerDigit + 1, answer);
				answer[pointerDigit] = 'h';
				phoneCodes(digits, pointerDigit + 1, answer);
				answer[pointerDigit] = 'i';
				phoneCodes(digits, pointerDigit + 1, answer);
			}
			else if(digits[pointerDigit] == 5)
			{
				answer[pointerDigit] = 'j';
				phoneCodes(digits, pointerDigit + 1, answer);
				answer[pointerDigit] = 'k';
				phoneCodes(digits, pointerDigit + 1, answer);
				answer[pointerDigit] = 'l';
				phoneCodes(digits, pointerDigit + 1, answer);
			}
			else if(digits[pointerDigit] == 6)
			{
				answer[pointerDigit] = 'm';
				phoneCodes(digits, pointerDigit + 1, answer);
				answer[pointerDigit] = 'n';
				phoneCodes(digits, pointerDigit + 1, answer);
				answer[pointerDigit] = 'o';
				phoneCodes(digits, pointerDigit + 1, answer);
			}
			else if(digits[pointerDigit] == 7)
			{
				answer[pointerDigit] = 'p';
				phoneCodes(digits, pointerDigit + 1, answer);
				answer[pointerDigit] = 'q';
				phoneCodes(digits, pointerDigit + 1, answer);
				answer[pointerDigit] = 'r';
				phoneCodes(digits, pointerDigit + 1, answer);
				answer[pointerDigit] = 's';
				phoneCodes(digits, pointerDigit + 1, answer);
			}
			else if(digits[pointerDigit] == 8)
			{
				answer[pointerDigit] = 't';
				phoneCodes(digits, pointerDigit + 1, answer);
				answer[pointerDigit] = 'u';
				phoneCodes(digits, pointerDigit + 1, answer);
				answer[pointerDigit] = 'v';
				phoneCodes(digits, pointerDigit + 1, answer);
			}
			else if(digits[pointerDigit] == 9)
			{
				answer[pointerDigit] = 'w';
				phoneCodes(digits, pointerDigit + 1, answer);
				answer[pointerDigit] = 'x';
				phoneCodes(digits, pointerDigit + 1, answer);
				answer[pointerDigit] = 'y';
				phoneCodes(digits, pointerDigit + 1, answer);
				answer[pointerDigit] = 'z';
				phoneCodes(digits, pointerDigit + 1, answer);
			}
			return;
		}
	}
}