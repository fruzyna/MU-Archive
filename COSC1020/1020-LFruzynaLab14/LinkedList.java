public class LinkedList {
	Node head;
	
	public LinkedList(){
		head = null;
	}
	
	public void insert(int intValue, String stringValue){
		Node temp = new Node();
		temp.intData = intValue;
		temp.stringData = stringValue;
		
		if(head == null){
			temp.next = null;
			head = temp;
		}
		else{
			temp.next = head;
			head = temp;
		}
	}
	
	public boolean delete(int intValue, String stringValue){
		Node temp = head;
		
		if(temp == null)
			return false;
		
		if(temp.intData == intValue && temp.stringData.equals(stringValue)){
			head = head.next;
			return true;
		}
		
		while(temp.next != null){
			if(temp.next.intData == intValue && temp.next.stringData.equals(stringValue)){
				temp.next = temp.next.next;
				return true;
			}
			temp = temp.next;
		}
		
		return false;
			
	}
	
	
	public void print(){
		Node temp = head;
		
		while(temp != null){
			System.out.print(temp.intData + " " + temp.stringData + "-->");
			temp = temp.next;
		}
		
		System.out.println("null");
	}
	
	public boolean search(int intValue, String stringValue){
		Node temp = head;
		
		while(temp != null){
			if(temp.intData == intValue && temp.stringData.equals(stringValue)){
				return true;
			}
			temp = temp.next;
		}
		
		return false;
	}
	
	
}
