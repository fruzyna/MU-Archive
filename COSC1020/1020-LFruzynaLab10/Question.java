
public class Question {
	String question;
	String[] answers;
	int correctAns;
	int chosenAns;
	boolean completed;
	boolean saved;
	
	public Question(String question, String[] answers, int correctAns)
	{
		this.question = question;
		this.answers = answers;
		this.correctAns = correctAns;
		chosenAns = -1;
		completed = false;
		saved = false;
	}
}
