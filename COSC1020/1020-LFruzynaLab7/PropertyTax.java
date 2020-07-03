import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * PropertyTax.java
 * Main class for program that calculates property tax based off of actual property value
 * @author mail929
 * @version 1 3/1/2016
 */
public class PropertyTax implements ActionListener {
	//initialization of jcomponents
	JTextField actual = new JTextField();
	JButton compute = new JButton("Compute");
	JLabel assessment = new JLabel();
	JLabel tax = new JLabel();
	
	public static void main(String[] args) {
		new PropertyTax();
	}

	/**
	 * Constructor for PropertyTax, creates and fills window.
	 */
	public PropertyTax() {
		//sets up jframe
		JFrame frame = new JFrame("Property Tax Calculator");
		frame.setSize(400, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(4, 2));
		
		//adds listener to button
		compute.addActionListener(this);
		
		//first row in window for actual value
		frame.add(new JLabel("Actual Property Value"));
		frame.add(actual);
		
		//second row for compute button
		frame.add(new JLabel());
		frame.add(compute);
		
		//third row for assessment value
		frame.add(new JLabel("Assessment Value"));
		frame.add(assessment);
		
		//final row for property tax
		frame.add(new JLabel("Property Tax"));
		frame.add(tax);
		
		frame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(compute)) {
			//calculates the assessment value
			double av = round(.6 * Double.parseDouble(actual.getText()));
			assessment.setText(av + "");
			
			//calculates the property tax
			double pt = round(.0064 * av);
			tax.setText(pt + "");
		}
	}
	
	/**
	 * Used to round a double value to a set amount of decimal places.
	 * 
	 * @param value The value to be rounded
	 * @return The rounded value
	 */
	public static double round(double value) {
	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(2, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

}
