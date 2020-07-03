import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args)
	{
		ArrayList<Place> places;
		try {
			places = IO.readCSV(new File("data.csv"));
			System.out.println("Average: " + average(places) + "%");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		places = new ArrayList<Place>();
		places.add(new Place("ExA","EX", overall, quality, survey, staff, health));
		places.add(new Place("ExB","EX", overall, quality, survey, staff, health));
		average(places);*/
	}
	/*
	static double[][] weight = new double[][]{
		{32.667,58.667,124.000,193.167}, //AK
		{13.333,21.333,32.667,47.333}, //AL
		{18.000,34.000,57.333,107.333},//AR
		{9.333,24,40.667,62.667},//AZ
		{24.000,46.000,67.000,101.750},//CA
		{22.000,36.667,54.667, 86.500},//CO
		{18.000,32.667,46,667,74,667},//CT
		{23.333,53.333,73.333,96.333},//DE
		{10.667, 22.667,34.000,55.333},//FL
		{4,10.667,17.333,34},//GA
		{16.667,34.667,42.667,72.667},//HI
		{8,19.333,32.667,61},//IA
		{26.667,47.333,73.333,107.667},//ID
		{14.667,30,46.667,76.667},//IL
		{12.667,27.333,43.333,70.000},//IN
		{29.333,58,84.667,142.167},//KS
		{9,22,38.667,100.583},//KY
		{6,18,29.333,49.333},//LA
		{5.333,15.333,27.333,49.333},//MA
		{16.667,36.000,59.333,96.667},//MD
		{4,13.333,21.333,28.667},//ME
		{22.667,43.333,66,104},//MI
		{10,23.333,36,58.667},//MN
		{12.000,27.333,42.667,73.333},//MO
		{6.667, 17.333, 28, 46.667},//MS
		{26,47.333,78,126.667},//MT
		{2.667,10.667,22.667,49.833},//NC
		{14.667,25.333,37.333,58.667},//ND
		{12.000,24.667,40.000,63.333},//NE
		{1.333,6.667,9.333,16},//NH
		{6,16,26.667,42},//NJ
		{7.333,42.667,83,212.167},//NM
		{24,36.5,50.667,72},//NV
		{3.333,13.333,26,52.667},//NY
		{6.667,17.333,30.667,53.333},//OH
		{31.333,58.667,90,142.667},//OK
		{13.333,29.333,45.333,67.5},//OR
		{10,23.333,42.667,75.333},//PA
		{.667,4,9.333,16.667},//RI
		{7.333,20.667,36.667,63.833},//SC
		{13,25.333,41.333,70.167},//SD
		{7.333,16.667,29.333,56.667},//TN
		{16,45.333,73.333,151.167},//TX
		{8.667,30.667,45.333,74},//UT
		{18.000,31.333,50.667,84.000},//VA
		{4,16.667,30.667,57.333},//VT
		{23.333,43.333,69.333,106},//WA
		{16,32,50,92},//WI
		{21.333,43.333,68.000,115.333},//WV
		{22.667,51.333,64.800,98},//WY
		{38.667,47.333,66.667,188.833},//DC
		{9.333,25.333,44.667,81.333},//GU
		{96,256,340,377.917},//PR
		{9.333,25.333,44.667,81.333},//VI
		};
	
	public static int healthInspectionScore(String abrv, double Weight){
		String[] ab ={"AK","AL","AR","AZ","CA","CO","CT","DE", "FL", 
				"GA", "HI","IA","ID","IL","IN","KS","KY","LA", "MA","MD","ME","MI","MN","MO","MS","MT",
				"NC","ND","NE", "NH","NJ","NM", "NV", "NY", "OH","OK",
				"OR", "PA", "RI", "SC","SD", "TN", "TX", "UT","VA","VT","WA", "WI","WV","WY", "DC","GU","PR","VI"};
		int number=-1;
		for(int i =0; i<ab.length;i++){
			if(ab[i].equals(abrv)){
				number=i;
			}
		}
		if(number==-1){
			System.out.println("Error at"+abrv+" with Weight: "+ Weight);
		}
		else{
			int temp=1;
			for(int i=weight[number].length; i>0; i--){
				if(weight[number][i-1]<Weight){
					return temp;
				}
				temp++;
			}
			return 5;
		}
		return 0;
	}
	*/
	public static int calcRating(Place place)
	{
		//int rating = rating = healthInspectionScore(place.state, place.health);
		//System.out.println(rating + " vs " + place.survey);
		int rating = place.survey;
		int score = rating;
		if(place.staff>rating && place.staff>=4){
			score+=1;
		}
		if(score>5){
			score=5;
		}
		if(place.staff==1){
			score--;
		}
		if(score<1){
			score=1;
		}
		if(place.quality==5){
			score++;
		}
		if(place.quality==1){
			score--;
		}
		if(score>5){
			score=5;
		}
		if(score<1){
			score=1;
		}
		if(score==3 && rating==1){
			score=2;
		}
		if(score > 3 && place.sff){
			score = 3;
		}
		return score;
	}
	
	public static boolean madeIt(Place place)
	{
		int rating = calcRating(place);
		System.out.println(place.toString());
		System.out.println("Rating: " + place.name + " " + rating + " vs " + place.overall);
		boolean good = (place.overall == rating);
		if(!good)
		{
			System.out.println("=======FAILED=======" + place.state);
		}
		return good;
	}
	
	public static double average(ArrayList<Place> places)
	{
		double correct = 0;
		for(Place place : places)
		{
			if(madeIt(place))
			{
				correct++;
			}
		}
		return (100 * (correct / (double)places.size()));
	}
}
