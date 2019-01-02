import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * COSC 1020
 * EmailMerge.java
 * Reads people and a template from appropriate file and 
 * creates new files with the template fill out with the people
 * 
 * @author Liam Fruzyna
 * @version 1.0 2/8/2016
 */
public class EmailMerge {

	/**
	 * The main method for the EmailMerge program
	 * 
	 * @param args Not used
	 */
	public static void main(String[] args) {
		try {
			//starts the constructor passing in new files or people and template files
			new EmailMerge(new File("people.txt"), new File("template.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Main loop of the EmailMerge program
	 * Processes people from people file
	 * 
	 * @param peopleFile Location of people file containing names, ages, and genders
	 * @param templateFile Location of template file
	 * @throws IOException Thrown by BuffredReader and makeEmail
	 */
	public EmailMerge(File peopleFile, File templateFile) throws IOException {
		//arraylist for containing all people data
		ArrayList<Person> people = new ArrayList<>();
		
		//reads through the people file
		BufferedReader br = new BufferedReader(new FileReader(peopleFile));
		String line = "";
		while((line = br.readLine()) != null) {
			//splits up the name, age, and gender of the person into a string array
			String[] parts = line.split(" ");
			//uses those parts to create a new person and add it to the people list
			people.add(new Person(parts[0], parts[1], parts[2]));
		}
		br.close();
		
		//parses through all the people
		for(Person person : people) {
			//creates the email file using the template file and the current person
			makeEmail(templateFile, person);
		}
	}
	
	/**
	 * Creates email file based off template and person given
	 * 
	 * @param templateFile Location of the template file
	 * @param person Person object containing name, age, and gender
	 * @throws IOException Thrown by PrintWriter and BufferedReader
	 */
	public void makeEmail(File templateFile, Person person) throws IOException {
		//creates a new file based off the persons name
		File file = getName(new File(person.getName() + ".txt"));
		
		//parses through the template file then rewrites it to the new file
		PrintWriter pw = new PrintWriter(new FileOutputStream(file));
		BufferedReader br = new BufferedReader(new FileReader(templateFile));
		String line = "";
		while((line = br.readLine()) != null) {
			//when it rewrites it it replaces the symbols with their name, age, and gender
			pw.println(line.replace("<<N>>", person.getName()).replace("<<A>>", person.getAge()).replace("<<G>>", person.getGender()));
		}
		pw.close();
	}
	
	/**
	 * Recursive function that attempts to make a file until it makes
	 * a new one that doesn't exist already
	 * 
	 * @param file The next file to be attempted to be used
	 * @return The final file to be used as a File
	 */
	public File getName(File file) {
		//if the file exists
		if(file.exists())
		{
			//parse out the name and number from the old file name
			String[] details = file.getName().split(Pattern.quote("."))[0].split("-");
			
			//increase the number by one
			int count = 0;
			if(details.length > 1)
			{
				count = Integer.parseInt(details[1]);
			}
			count++;
			
			//create the new file name and check it again
			file = getName(new File(details[0] + "-" + count + ".txt"));
		}

		//return the final file
		return file;
	}
}
