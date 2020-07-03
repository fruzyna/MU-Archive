import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * PhonePackage.java
 * Calculates a price for a bundle of a cell plan, phone, and features.
 * 
 * @author mail929
 * @version 1 3/1/2016
 */
public class PhonePackage implements ActionListener {
	//initialization of jcomponents
	JComboBox plan;
	JComboBox phone;
	JCheckBox vmail = new JCheckBox("Voice mail");
	JCheckBox text = new JCheckBox("Text Messaging");
	JButton compute = new JButton("Compute");
	JLabel planCost = new JLabel("45.00");
	JLabel phoneCost = new JLabel(29.95 * 1.06 + "");
	JLabel extraCost = new JLabel("0.00");
	JLabel output = new JLabel();
	
	//data used for combo boxes
	String[] planMinutes = {"300 min / month", "800 min / month", "1500 min / month"};
	double[] planCosts = {45.00, 65.00, 99.00};
	String[] phones = {"Model 100", "Model 110", "Model 200"};
	double[] phoneCosts = {29.95, 49.95, 99.95};
	
	public static void main(String[] args) {
		new PhonePackage();
	}
	
	/**
	 * Constructor for PhonePackage, create and fills out a window.
	 */
	public PhonePackage() {
		plan = new JComboBox(planMinutes);
		phone = new JComboBox(phones);
		
		//sets up jframe
		JFrame frame = new JFrame("Phone Package");
		frame.setSize(450, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(4, 3));
		
		//adds action listeners
		plan.addActionListener(this);
		phone.addActionListener(this);
		compute.addActionListener(this);
		vmail.addActionListener(this);
		text.addActionListener(this);
		
		//first row for phone place
		frame.add(new JLabel("Package"));
		frame.add(plan);
		frame.add(planCost);
		
		//second row for phone
		frame.add(new JLabel("Phone"));
		frame.add(phone);
		frame.add(phoneCost);
		
		//third row for extra features
		frame.add(vmail);
		frame.add(text);
		frame.add(extraCost);
		
		//final row for total cost
		frame.add(compute);
		frame.add(new JLabel());
		frame.add(output);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(plan)) {
			//if a new plan is chosen update its cost
			planCost.setText(planCosts[plan.getSelectedIndex()] + "");
		}
		else if(e.getSource().equals(phone)) {
			//if a new phone is chosen update its cost
			phoneCost.setText(round(phoneCosts[phone.getSelectedIndex()]*1.06) + "");
		}
		else if(e.getSource().equals(vmail) || e.getSource().equals(text)) {
			//if any checkbox is clicked
			double total = 0;
			if(vmail.isSelected()) {
				//if voicemail is chosen add $5
				total += 5.00;
			}
			if(text.isSelected()) {
				//if texting is chosen add $10
				total += 10.00;
			}
			extraCost.setText(total + "");
		}
		else if(e.getSource().equals(compute)) {
			//if the button is pressed
			double sub = 0;
			if(vmail.isSelected()) {
				sub += 5.00;
			}
			if(text.isSelected()) {
				sub += 10.00;
			}
			//calculate total cost
			output.setText(round(planCosts[plan.getSelectedIndex()] + (phoneCosts[phone.getSelectedIndex()]*1.06) + sub) + "");
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
