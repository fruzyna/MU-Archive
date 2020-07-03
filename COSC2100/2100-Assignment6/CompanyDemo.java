import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * MU COSC 2100 Assignment 6: Java Collections Framework
 * @author Liam Fruzyna
 *
 */
public class CompanyDemo {
	static HashMap<Integer, Department> departments;
	static HashMap<String, Employee> employees;
	
	public static void main(String[] args) {
		departments = new HashMap<>();
		employees = new HashMap<>();
		
		try {
			//Reads in and saves all data from departments file
			BufferedReader br = new BufferedReader(new FileReader(new File("DEPARTMENT.txt")));
			String line = br.readLine();
			while((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				//Uses the 2nd column/department number for the key
				departments.put(Integer.parseInt(parts[1]), new Department(parts[0], Integer.parseInt(parts[1]), parts[2], parts[3]));
			}
			br.close();
			
			//Reads in and saves all data from employees file
			br = new BufferedReader(new FileReader(new File("EMPLOYEE.txt")));
			line = br.readLine();
			while((line = br.readLine()) != null) {
				//Splits with " to ignore commas in address
				String[] parts = line.split("\"");
				String[] preAddress = parts[0].split(",");
				String[] postAddress = parts[2].split(",");
				//Uses the 3rd column/ssn for the key
				employees.put(preAddress[2], new Employee(preAddress[0] + " " + preAddress[1], preAddress[2], preAddress[3], parts[1], postAddress[1], postAddress[2], Integer.parseInt(postAddress[3])));
			}
			
			//runs the main program
			new CompanyDemo();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public CompanyDemo() {
		Scanner kb = new Scanner(System.in);
		boolean run = true;
		
		//runs until it is manually stopped
		while(run) {
			
			//prints menu of options
			System.out.println("1. List employee department");
			System.out.println("2. List employee supervisors");
			System.out.println("3. Quit");
			System.out.print("Option #: ");
			
			//listens for number 
			int input;
			try {
				input = kb.nextInt();
			}
			catch(InputMismatchException e) {
				//if its not a number given, use 4
				input = 4;
			}
			kb.nextLine();
			
			//cases for all options
			switch(input) {
			case 1:
				//employee department
				//prompts for employee ssn
				System.out.print("Enter employee SSN: ");
				String ssn = kb.nextLine();
				try {
					Employee e = employees.get(ssn);
					
					//prints out department info
					System.out.println(e.name + ": " + departments.get(e.department).name + " (" + e.department + ")");
				} catch(NullPointerException n) {
					System.out.println("Invalid Employee");
				}
				break;
			case 2:
				//employee supervisors
				//prompts for employee ssn
				System.out.print("Enter employee SSN: ");
				String ssnum = kb.nextLine();
				try {
					Employee em = employees.get(ssnum);
					
					//prints out supervisors using recursive method
					System.out.println(em.name + "'s Supervisors:" + getSupers(em.ssn));
				} catch(NullPointerException n) {
					System.out.println("Invalid Employee");
				}
				break;
			case 3:
				//quit, breaks while loop
				run = false;
				break;
			default:
				//other prompts again
				System.out.println("Unknown entry, please try again.");
				break;
			}
			
			//blank line to seperate instances
			System.out.println();
		}
	}
	
	public String getSupers(String ssn) {
		//gets employee from provided ssn
		Employee e = employees.get(ssn);
		
		//checks for a supervisor
		if(e.supervisor.equals("")) {
			//starts fall back
			return "";
		}
		
		//recursively calls to find supervisors supervisor
		return " " + employees.get(e.supervisor).name + "," + getSupers(e.supervisor);
	}
}
