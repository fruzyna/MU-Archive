
public class Employee {
	String name;
	String ssn;
	String address;
	String salary;
	int department;
	String birthday;
	
	String supervisor;
	
	public Employee(String name, String ssn, String birthday, String address, String salary, String supervisor, int department) {
		this.name = name;
		this.ssn = ssn;
		this.address = address;
		this.salary = salary;
		this.department = department;
		this.birthday = birthday;
		this.supervisor = supervisor;
	}
}
