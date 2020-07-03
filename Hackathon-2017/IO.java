import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class IO
{
	
	public static ArrayList<Place> readCSV(File file) throws IOException
	{
		ArrayList<Place> places = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		line = line.replace("\"", "");
		String[] headers = line.split(",");
		//System.out.println("Headers: " + line);
		for(int i = 0; (line = br.readLine()) != null; i++)
		{
			line = line.replace("\"", "");
			String[] points = line.split(",");

			String name = "";
			String state = "";
			int overall = 0;
			int survey = 0;
			int quality = 0;
			int staff = 0;
			double health = 99999;
			boolean sff = false;
			
			for(int j = 0; j < headers.length; j++)
			{
				String value = points[j];
				//System.out.print(value + ",");
				if(!value.equals(""))
				{
					switch(headers[j])
					{
					case "provnum":
						name = value;
						break;
					case "STATE":
						state = value;
						break;
					case "overall_rating":
						overall = Integer.parseInt(value);
						break;
					case "survey_rating":
						survey = Integer.parseInt(value);
						break;
					case "quality_rating":
						quality = Integer.parseInt(value);
						break;
					case "staffing_rating":
						staff = Integer.parseInt(value);
						break;
					case "WEIGHTED_ALL_CYCLES_SCORE":
						health = Double.parseDouble(value);
						break;
					case "SFF":
						sff = value.equals("Y");
						break;
					}
				}
			}
			//System.out.println("");
			if(overall != 0 && survey != 0 && quality != 0 && staff != 0 && health != 99999)
			{
				Place place = new Place(name, state, overall, quality, survey, staff, health, sff);
				//System.out.println(place.toString());
				places.add(place);
			}
		}
		br.close();
		
		return places;
	}
}
