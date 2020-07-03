
public class Place
{
	String name;
	String state;
	int quality;
	int survey;
	int staff;
	int overall;
	double health;
	boolean sff;
	
	public Place(String name, String state, int overall, int quality, int survey, int staff, double health, boolean sff)
	{
		this.state = state;
		this.name = name;
		this.overall = overall;
		this.quality = quality;
		this.survey = survey;
		this.staff = staff;
		this.health = health;
		this.sff = sff;
	}
	
	public String toString()
	{
		 return (name + " in " + state + " with score of " + overall + " from quality: " + quality + " survey: " + survey + " staffing: " + staff + " health: " + health + " sff: " + sff);
	}
}
