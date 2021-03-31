import java.util.Stack;

public class Practice {
	// what is the big o running time as a function of n? count the operation
	// "count++" towards the running time
	//answer: o(logn)

	public void foo(int n) {
		int count = 0;
		for(int i = n; i >= 1; i = i/2)
		{
			
			for(int j = 1; j <= 1; j++)
			{
				count++;
			}
		}
	}
	
	//find output for n = 2,4,8,50
	//answer:
	
	public void foo1(int n)
	{
		Stack<Integer> s = new Stack<Integer>();
		String result = "";
		
		for(int i = n; i > 0; i = i/2)
		{
			s.push(i % 2);
		}
		while(! (s.isEmpty()))
		{
			result += s.pop();
			
		}
		System.out.println(result);;
	}
	
	public void foo2(int n)
	{
		Queue<Integer> q = new Queue<Integer>();
		String result = "";
		
		for(int i = n; i > 0; i = i/2)
		{
			q.enqueue(i % 2);
		}
		while(! (q.isEmpty()))
		{
			result += q.dequeue();
			
		}
		System.out.println(result);
	}
	
	//assuming that m = 3, and n = 5, give one example of the BEST CASE scenario where merge executes the least number of if-conditional to merge the 
	//2 arrays
	//For example, one example of the WORST CASE scenario is when the first array is [2,5,9,0,0,0,0,0] and the second array is [1,3,4,6,8]
	//The call would be merge([2,5,9,0,0,0,0,0], 3, [1,3,4,6,8], 5)
	
	
	public void merge(int[] nums1, int m, int[] nums2, int n) {
	       int i = m - 1;
	       int j = n - 1;
	       int k = m + n - 1;
    	   int count =0;

	       while(i >= 0 && j >= 0){
	    	   count++;
	           int one = nums1[i];
	           int two = nums2[j];
	           if(one >= two){
	               nums1[k] = one;
	               k--;
	               i--;
	           }else{
	               nums1[k] = two;
	               k--;
	               j--;
	           }
	       }
	       
	       while(j >= 0){
	           nums1[k--] = nums2[j--];
	       } 
	       System.out.println(count);
	   }
	
	//min value of a linked list(recursively)
	/*
	public int max() {

		if (isEmpty()) {

			return 0;

		}
		int currentMinValue = (Integer) first.item;

		return getMin(first.next, currentMinValue);
	}
    */
	
	//min value  of a linked list(normal)
	/
	public static int minValue(Node front) {
	    Node current = front;
	    int min = current.data;
	while(current != null){
	    if(min > current.data){
	        min = current.data;
	    }
	    current = current.next;
	}
	return min;
	}
	
	
	
	public static void main(String[] args) {
		Practice ani = new Practice();
		//ani.foo1(50);
		ani.foo2(50);
		//ani.merge(new int[] {0,0,0,3,0,5,0,10}, 3, new int [] {8,1,1,1,1}, 5);

	}
}
