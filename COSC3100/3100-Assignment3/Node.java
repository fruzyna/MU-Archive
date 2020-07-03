import java.util.ArrayList;

public class Node {
	int value;
	ArrayList<Node> links;
	
	public Node(int value) {
		this.value = value;
		links = new ArrayList<Node>();
	}
	
	//sorts linked items in numerical order
	public void add(Node link) {
		links.add(link);
		ArrayList<Node> newList = new ArrayList<Node>();
		while(links.size() > 0) {
			Node smallest = links.get(0);
			for(int i = 1; i < links.size(); i++) {
				if(links.get(i).value < smallest.value) {
					smallest = links.get(i);
				}
			}
			newList.add(smallest);
			links.remove(smallest);
		}
		links = newList;
	}
}
