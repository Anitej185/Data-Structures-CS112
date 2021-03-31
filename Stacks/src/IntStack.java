import java.util.Stack;

public class IntStack {
	private int[] a;
	private int top;
	
	
	public IntStack(int capacity)
	{
		a = new int[capacity];
	}
	
	void push(int item)
	{
		a[top] = item;
		top++;
	}
	
	int pop()
	{
		return a[top--];
	}
	
	boolean isEmpty()
	{
		return (top == 0);
	}
	
	int size()
	{
		return top;
	}
	
	public String foo1(int n)
	{
		IntStack ill = new IntStack(100);
		//Stack<Integer> s = new Stack<Integer>();
		String result = "";
		
		for(int i = n; i > 0; i = i/2)
		{
			ill.push(i % 2);
		}
		while(!ill.isEmpty())
		{
			result += ill.pop();
			
		}
		return result;
	}
	
	//test driver
	public static void main(String[] args)
	{
		//IntStack i = new IntStack(100);
		//i.foo1(2);
		
		//System.out.println(i.size()); // answer is 1
	}
	
	

}
