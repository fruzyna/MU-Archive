import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileIO
{
	File file = new File("Score.txt");
	String[] topNames;
	int[] topScores;
	
	public static void main(String[] args)
	{
		new FileIO();
	}

	public FileIO()
	{
		if(!file.exists())
		{
			try {
				file.createNewFile();
				System.out.println("File is missing, creating template");
				PrintWriter pw = new PrintWriter(new FileOutputStream(file));
				for(int i = 1; i <= 5; i++)
				{
					pw.println("Player" + i + "\n");
					pw.println(0 + "\n");
					pw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			ArrayList<String> tn = new ArrayList<>();
			ArrayList<Integer> ts = new ArrayList<>();
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while((line = br.readLine()) != null)
			{
				try {
					ts.add(Integer.parseInt(line));
				} catch(Exception e)
				{
					tn.add(line);
				}
			}
			topNames = new String[tn.size()];
			topScores = new int[ts.size()];
			for(int i = 0; i < tn.size(); i++)
			{
				topNames[i] = tn.get(i);
			}
			for(int i = 0; i < ts.size(); i++)
			{
				topScores[i] = ts.get(i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Scanner kb = new Scanner(System.in);
		while(true)
		{
			printScores();
			
			System.out.print("Enter new score name: ");
			String name = kb.nextLine();
			System.out.print("Enter new " + name + "'s score: ");
			int score = Integer.parseInt(kb.nextLine());
			
			for(int i = 0; i < topScores.length; i++)
			{
				if(score > topScores[i])
				{
					String oldName = topNames[i];
					int oldScore = topScores[i];
					
					topNames[i] = name;
					topScores[i] = score;
					
					name = oldName;
					score = oldScore;
				}
			}
			
			try {
				PrintWriter pw = new PrintWriter(new FileOutputStream(file));
				for(int i = 0; i < topScores.length; i++)
				{
					pw.println(topNames[i]);
					pw.println(topScores[i]);
				}
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void printScores()
	{
		for(int i = 0; i < topNames.length; i++)
		{
			System.out.println((i+1) + ". " + topNames[i] + " - " + topScores[i]);
		}
	}
	
	public String[] getTopNames()
	{
		return topNames;
	}
	
	public int[] getTopScores()
	{
		return topScores;
	}
}
