/**
 * Person.java
 * Object container for a persons name, age, and gender
 * 
 * @author mail929
 * @version 1.0 2/8/2016
 */
public class Person {
	private String name;
	private String age;
	private String gender;
	
	/**
	 * Constructor of the Person object
	 * Takes in values and saves them in the object
	 * 
	 * @param name Name of the person
	 * @param age Age of the person
	 * @param gender Gender of the person
	 */
	public Person(String name, String age, String gender) {
		this.name = name;
		this.age = age;
		this.gender = gender;
	}
	
	/**
	 * Returns the name of the person
	 * 
	 * @return The name of the person as a String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the age of the person
	 * 
	 * @return The age of the person as a String
	 */
	public String getAge() {
		return age;
	}
	
	/**
	 * Returns the gender of the person
	 * 
	 * @return The gender of the person as a String
	 */
	public String getGender() {
		return gender;
	}
}
