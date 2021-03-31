public class LLDemo {
	public class Node
	{
		public Node(String item)
		{
			this.item = item;
			this.next = null;
		}
		public String item; //this.item
		public Node next;
	}
	
	//insert a node to the beginning of the list
	public Node insertFront(Node head)
	{
		Node front = new Node("her");
		front.next = head;
		head = front;
		
		return head;
		
	}
	
	
	//DO THESE FOR PRACTICE
	//insert node to the end of the list
	/*public Node insertLast(Node N)
	{
	
	}
	*/
	//insert a node to the list after item
	public Node insertAfter(String item)
	{
		
	}
	
	
	public int smallestElement(Node head)  
	{  
	      
	    // Declare a min variable and initialize  
	    // it with INT_MAX value.  
	    // INT_MAX is integer type and its value  
	    // is 32767 or greater.  
	    int min = Integer.MAX_VALUE;  
	  
	    // Check loop while head not equal to NULL  
	    while (head != null)  
	    {  
	  
	        // If min is greater then head->data then  
	        // assign value of head->data to min  
	        // otherwise node point to next node.  
	        if (min > head.data)  
	            min = head.data;  
	  
	        head = head.next;  
	    }  
	    return min;  
	} 
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Node head = new Node("guna");
		//Node N = new Node("me");
		//Node M = new Node("him");
		
		//connnect N to head
		//head.next = N;
		
		//connect N to M
		//(head.next).next = M;
		
		//Node newN = new Node("bob");
		//head = insertFront(newN);
		

	}

}
