import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Exam extends JFrame implements ActionListener {
	JPanel container;
	JLabel timer;
	JLabel question;
	JCheckBox ans1;
	JCheckBox ans2;
	JCheckBox ans3;
	JCheckBox ans4;
	JPanel circles;
	JPanel buttons;
	JButton next;
	JButton save;
	JButton exit;
	JButton back;
	
	Question[] questions;
	int current = 0;
	String[] a1 = {"1", "2", "3", "4"};
	String[] a2 = {"1", "2", "3", "4"};
	String[] a3 = {"1", "2", "3", "4"};
	String[] a4 = {"1", "2", "3", "4"};
	String[] a5 = {"1", "2", "3", "4"};
	String[] a6 = {"1", "2", "3", "4"};
	String[] a7 = {"1", "2", "3", "4"};
	String[] a8 = {"1", "2", "3", "4"};
	String[] a9 = {"1", "2", "3", "4"};
	String[] a10 = {"1", "2", "3", "4"};
	int score = 0;
	int time = 60;
	
	public static void main(String[] args) {
		new Exam();
	}
	
	public Exam() {
		//sets up basics for frame
		super("Computer Based Examination");
		setSize(800, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//sets up container for all jcomponenets
		container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.setBackground(Color.WHITE);

		//adds the timer to the top
		timer = new JLabel();
		container.add(timer);
		
		//creates all the questions
		questions = new Question[10];
		questions[0] = new Question("1. ", a1, 2);
		questions[1] = new Question("2. ", a2, 2);
		questions[2] = new Question("3. ", a3, 2);
		questions[3] = new Question("4. ", a4, 2);
		questions[4] = new Question("5. ", a5, 2);
		questions[5] = new Question("6. ", a6, 2);
		questions[6] = new Question("7. ", a7, 2);
		questions[7] = new Question("8. ", a8, 2);
		questions[8] = new Question("9. ", a9, 2);
		questions[9] = new Question("10. ", a10, 2);
		
		//sets up the first question and answers on the panel
		question = new JLabel(questions[current].question);
		ans1 = new JCheckBox(questions[current].answers[0]);
		ans1.addActionListener(this);
		ans2 = new JCheckBox(questions[current].answers[1]);
		ans2.addActionListener(this);
		ans3 = new JCheckBox(questions[current].answers[2]);
		ans3.addActionListener(this);
		ans4 = new JCheckBox(questions[current].answers[3]);
		ans4.addActionListener(this);
		container.add(question);
		container.add(ans1);
		container.add(ans2);
		container.add(ans3);
		container.add(ans4);
		
		//sets up the circles for questions done and saved
		circles = new Circles();
		container.add(circles);
		
		//sets up the buttons on the bottom
		buttons = new JPanel();
		next = new JButton("Next");
		next.addActionListener(this);
		buttons.add(next);
		save = new JButton("Save");
		save.addActionListener(this);
		buttons.add(save);
		exit = new JButton("Exit");
		exit.addActionListener(this);
		buttons.add(exit);
		back = new JButton("Previous");
		back.addActionListener(this);
		buttons.add(back);
		buttons.setBackground(Color.WHITE);
		container.add(buttons);
		
		add(container);
		
		//thread to count down from a minute
		(new Thread() {
			public void run() {
				while(time > 0)
				{
					//updates every second
					timer.setText("Time: " + time + " sec");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					time--;
				}
				//then shows score
				JOptionPane.showMessageDialog(null, "You got " + score + " out of 10");
				
			}
		}).start();
		
		setVisible(true);
	}
	
	//updates the current question and answers
	public void fillQuestion()
	{
		question.setText(questions[current].question);
		ans1.setText(questions[current].answers[0]);
		ans2.setText(questions[current].answers[1]);
		ans3.setText(questions[current].answers[2]);
		ans4.setText(questions[current].answers[3]);
		ans1.setSelected(questions[current].chosenAns == 0);
		ans2.setSelected(questions[current].chosenAns == 1);
		ans3.setSelected(questions[current].chosenAns == 2);
		ans4.setSelected(questions[current].chosenAns == 3);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(next)) {
			//when next is chosen
			if(current < 9) {
				//mark as completed
				questions[current].completed = true;
				//if the selection is right increase the score
				if(questions[current].chosenAns == questions[current].correctAns)
				{
					score++;
				}
				//go to the next question
				current++;
				fillQuestion();
			}
			invalidate();
			repaint();
			circles.repaint();
		}
		else if(e.getSource().equals(save)) {
			//if the save button is pressed
			//mark as saved
			questions[current].saved = true;
			invalidate();
			repaint();
			circles.repaint();
		}
		else if(e.getSource().equals(exit)) {
			//if the exit button is pressed
			//closes the program
			System.exit(0);
		}
		else if(e.getSource().equals(back)) {
			//if the previous button is pressed
			if(current > 0) {
				//go to the last question
				current--;
				fillQuestion();
			}
			invalidate();
			repaint();
			circles.repaint();
		}
		else if(e.getSource().equals(ans1) && (questions[current].saved || !questions[current].completed)) {
			//if the 1st checkbox is selected make sure its the only
			ans1.setSelected(true);
			ans2.setSelected(false);
			ans3.setSelected(false);
			ans4.setSelected(false);
			if(ans1.isSelected()) {
				questions[current].chosenAns = 0;
			}
		}
		else if(e.getSource().equals(ans2) && (questions[current].saved || !questions[current].completed)) {
			//if the 2nd checkbox is selected make sure its the only
			ans2.setSelected(true);
			ans1.setSelected(false);
			ans3.setSelected(false);
			ans4.setSelected(false);
			if(ans2.isSelected()) {
				questions[current].chosenAns = 1;
			}
		}
		else if(e.getSource().equals(ans3) && (questions[current].saved || !questions[current].completed)) {
			//if the 3rd checkbox is selected make sure its the only
			ans3.setSelected(true);
			ans2.setSelected(false);
			ans1.setSelected(false);
			ans4.setSelected(false);
			if(ans3.isSelected()) {
				questions[current].chosenAns = 2;
			}
		}
		else if(e.getSource().equals(ans4) && (questions[current].saved || !questions[current].completed)) {
			//if the 4th checkbox is selected make sure its the only
			ans4.setSelected(true);
			ans2.setSelected(false);
			ans3.setSelected(false);
			ans1.setSelected(false);
			if(ans4.isSelected()) {
				questions[current].chosenAns = 3;
			}
		}
	}
	
	class Circles extends JPanel {
		
		public void paint(Graphics g) {
			//clear panel
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 800, 50);
			int x = 10;
			for(int i = 0; i < questions.length; i++) {
				if(!questions[i].saved)
				{
					//if its not saved draw a small circle
					g.setColor(Color.BLACK);
					g.drawOval(x, 0, 25, 25);
					if(questions[i].completed) {
						//if its completed fill with green
						g.setColor(Color.GREEN);
						g.fillOval(x, 0, 25, 25);
					}
					x += 35;
				}
				else
				{
					//if it is saved draw a large circle
					g.setColor(Color.BLACK);
					g.drawOval(x, 0, 50, 50);
					//fill with yellow
					g.setColor(Color.YELLOW);
					g.fillOval(x, 0, 50, 50);
					x += 60;
				}
			}
		}
	}
	
}
