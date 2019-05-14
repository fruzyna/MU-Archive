package cosc3550;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * GameReader
 * Reads in a level file and returns it as 2D char array
 * @author Liam Fruzyna and Katy Weathington
 */
public class GameReader {
	
	// returns a coordinate based set of blocks in form of chars
	public static char[][] readLevel(String file, boolean cheat) throws IOException {
		char blocks[][];
		ArrayList<String> lines = new ArrayList<>();
		
		// read the file line by line
	    BufferedReader reader = new BufferedReader(new FileReader(file));
	    String line = "";
	    while((line = reader.readLine()) != null) {
	    	if(line.charAt(0) != '#') // ignore lines starting with #
	    		lines.add(line);
	    }
	    reader.close();
	    
	    // dimensions of the level
	    int height = lines.size();
	    int width = lines.get(1).length();

	    System.out.println("Found a " + width + ", " + height + " block level.");
	    
	    // convert to a 2D array
	    blocks = new char[height][width];
	    for(int y = 0; y < height; y++) {
	    	line = lines.get(y);
	    	char last = ' ';
	    	int x = 0;
		    for(int i = 0; i < line.length(); i++) {
		    	char c = line.charAt(i);
		    	if(c == '!') {
		    		if(cheat)
		    			c = '_';
		    		else
		    			c = 'x';
		    	}
		    	if(last == 'd' && c >= '0' && c <= '9') {
		    		x--;
		    		blocks[y][x] = (char) (c - 10);
		    	}
		    	else
		    		blocks[y][x] = c;
		    	x++;
		    	last = c;
		    }
	    }
	    
	    // print out how it was read it to check
	    /*
	    for(int y = 0; y < blocks.length; y++) {
	    	for(int x = 0; x < blocks[y].length; x++) {
	    		System.out.print(blocks[y][x]);
	    	}
	    	System.out.println();
	    }*/
	    
	    return blocks;
	}
	
	// returns a map of rules from the rules file
	public static Map<String, Integer> readRules(String file) throws IOException {
		Map<String, Integer> rules = new HashMap<>();
		
		// read the file line by line
	    BufferedReader reader = new BufferedReader(new FileReader(file));
	    String line = "";
	    while((line = reader.readLine()) != null) {
	    	if(line.charAt(0) != '#') { // ignore lines starting with #
	    		String parts[] = line.split("=");
	    		rules.put(parts[0], Integer.parseInt(parts[1]));
	    	}
	    }
	    reader.close();
	    
	    return rules;
	}
}
