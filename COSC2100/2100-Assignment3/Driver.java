
public class Driver
{

	public static void main(String[] args)
	{
		LispEvaluator.calculate("(+ 5 0 10)"); //15
		LispEvaluator.calculate("(+ 5 0 10 (- 7 2))"); //20
		LispEvaluator.calculate("(+ (- 6) (* 2 3 4) (/ (+ 3) (*) (- 2 3 1)))"); //16.5
		LispEvaluator.calculate("(+ (- 632) (* 21 3 4) (/ (+ 32) (*) (- 21 3 1)))"); //-378.11764705882354
		LispEvaluator.calculate("(* 3 (/ 2))");	//1.5
	}
}