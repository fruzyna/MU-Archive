import java.util.ArrayList;
import java.util.HashSet;

/*
 * COSC 2100 Collections In Class Exercise
 * by Liam Fruzyna
 */
public class Main {

	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<>();
		list.add("a");
		list.add("a");
		list.add("b");
		list.add("b");
		list.add("c");
		list.add("a");
		
		System.out.println("First method");
		ArrayList<String> answer = removeDuplicates(list);
		for(int i = 0; i < answer.size(); i++) {
			System.out.println(answer.get(i));
		}
		System.out.println();
		System.out.println("Second method");
		answer = removeDuplicates2(list);
		for(int i = 0; i < answer.size(); i++) {
			System.out.println(answer.get(i));
		}
	}
	
	public static ArrayList<String> removeDuplicates(ArrayList<String> list) {
		ArrayList<String> finalList = new ArrayList<>();
		for(int i = 0; i < list.size(); i++) {
			String item = list.get(i);
			if(!finalList.contains(item))
			{
				finalList.add(item);
			}
		}
		return finalList;
	}
	
	public static ArrayList<String> removeDuplicates2(ArrayList<String> list) {
		HashSet<String> set = new HashSet<>();
		for(int i = 0; i < list.size(); i++)
		{
			set.add(list.get(i));
		}
		ArrayList<String> finalList = new ArrayList<>();
		finalList.addAll(set);
		return finalList;
	}
	
	
}
