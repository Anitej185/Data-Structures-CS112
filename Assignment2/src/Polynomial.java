//package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author Anitej Thamma, ajt200@scarletmail.rutgers.edu, ajt200
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	//done
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/

		Node alter = null;

		Node n = null;
		Node sum = n;

		Node a = poly1;
		Node b = poly2;

		float c = 0;
		int d = 0;

		while (a != null || b != null) 
		{
			if (a == null) 
			{
				c = b.term.coeff;
				d = b.term.degree;

				alter = new Node(c, d, null);
				b = b.next;
			}

			else if (b == null) 
			{
				c = a.term.coeff;
				d = a.term.degree;
				alter = new Node(c, d, null);
				a = a.next;
			}

			else if (a.term.degree < b.term.degree) 
			{
				c = a.term.coeff;
				d = a.term.degree;
				alter = new Node(c, d, null);

				a = a.next;
			}

			else if (a.term.degree > b.term.degree) 
			{
				c = b.term.coeff;
				d = b.term.degree;
				alter = new Node(c, d, null);

				b = b.next;
			}
			
			else if (a.term.degree == b.term.degree) 
			{
				c = (a.term.coeff + b.term.coeff);
				d = a.term.degree;
				alter = new Node(c, d, null);

				a = a.next;
				b = b.next;
			}

			if (alter.term.coeff == 0) 
			{
				continue;
			}

			else if (n == null) 
			{
				n = alter;
				sum = n;
			}

			else 
			{
				n.next = alter;
				n = n.next;
			}
		}
		return sum;
	}
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	//done
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/

		Node product = new Node(0, 0, null);

		Node n = new Node(0, 0, null);

		Node aa = poly1;
		Node bb = poly2;

		while (aa != null) 
		{
			bb = poly2;
			Node t = n;

			while (bb != null) 
			{
				t.term.degree = (int) aa.term.degree + bb.term.degree;
				t.term.coeff = (float) aa.term.coeff * bb.term.coeff;

				n = new Node(t.term.coeff, t.term.degree, null);

				if (product == null) 
				{
					product = n;
				}

				else 
				{
					product = add(product, n);
				}
				bb = bb.next;
			}
			aa = aa.next;
		}
		return product;
	}

		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	//done
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		
		float f = 0;
	    Node n = poly;  
	       
	    while (n != null)
	       {
	           f += (n.term.coeff * (Math.pow(x, n.term.degree)));
	           n = n.next;
	       }	      
	       return f;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
