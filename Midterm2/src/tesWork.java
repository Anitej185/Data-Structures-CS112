
public class tesWork {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		
		//question #1 (implementing queue)
		
		
		public void enqueue(Item item) 
		{

        Node<Item> insertion = new Node<Item>(); 
        
        insertion.data = item;

        if (isEmpty()) 
        {
            insertion.next = insertion;
            last = insertion;
        } 
        else 
        {
            insertion.next = last.next;
            last.next = insertion; 
        }
        size += 1;
    }

    public Item dequeue() 
    {
    	if (isEmpty()) 
        {
           return null;
        }
        Item deletedData = last.next.data;

        last.next = last.next.next; 

        return deletedData;
    }
	
		
	//question #3.3 (counting keys; recursive BST)
		
		
		public static int countInRange(BSTNode root, int low, int high)
	    {
	        if(root == null)
	        {
	            return 0;
	        }

	        if(root.key >= low && root.key <= high)
	        {
	            return 1 + this.countInRange(root.left, low, high)+ this.countInRange(root.right, low, high);
	        }
	                  
	        else if(root.key < low)
	        {
	            return this.countInRange(root.right, low, high);
	        }
	          
	        else
	        {
	            return this.countInRange(root.left, low, high);
	        }
	    }	
	
	//question #5.2 (transformation)
		/*
		Node sLeft = h.left;
		Node sRight = h.right;

		Node xLeft = x.left;

		x.left = sLeft;
		x.right = sRight;

		h.left = x;
		h.right = xLeft;

		x=h;
		
		*/
	}

}
