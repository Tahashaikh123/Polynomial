package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
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
	public static Node add(Node poly1, Node poly2) {
		Node front = null;
		Node pointer = null;
		Node ptr = poly1;
		Node ptr2 = poly2;
		while (ptr !=null && ptr2!=null) {
				if (ptr.term.degree==ptr2.term.degree) {
					if (ptr.term.coeff+ptr2.term.coeff==0) {
						ptr = ptr.next;
						ptr2=ptr2.next;
					}
					else {
						Node newNode = new Node(ptr.term.coeff+ptr2.term.coeff, ptr.term.degree ,null);
						if (front == null) {
							front = newNode;
							pointer = newNode;
						}
						else {
							pointer.next = newNode;
							pointer = newNode;
						}
					ptr=ptr.next;
					ptr2=ptr2.next;
					}
				}
				else if (ptr.term.degree > ptr2.term.degree) {		
					Node newNode = new Node(ptr2.term.coeff, ptr2.term.degree,null);
					if (front ==null) {
						front = newNode;
						pointer = newNode;
					}
					else {
						pointer.next=newNode;
						pointer = newNode;
					}
					ptr2 = ptr2.next;
				}
				else {
					Node newNode = new Node(ptr.term.coeff, ptr.term.degree,null);
					if (front ==null) {
						front = newNode;
						pointer = newNode;
					}
					else {
						pointer.next=newNode;
						pointer = newNode;
					}
					ptr=ptr.next;
				}
			}
			while (ptr!=null) {
				Node newNode = new Node(ptr.term.coeff,ptr.term.degree,null);
				if (front ==null) {
					front = newNode;
					pointer = newNode;
				}
				else {
					pointer.next=newNode;
					pointer = newNode;
				}
				ptr=ptr.next;
			}
			while (ptr2!=null) {
				Node newNode = new Node(ptr2.term.coeff,ptr2.term.degree,null);
				if (front ==null) {
					front = newNode;
					pointer = newNode;
				}
				else {
					pointer.next=newNode;
					pointer = newNode;
				}
				ptr2=ptr2.next;
			}
			
			Node fullLL = front;
			Node prev = null;
			while (fullLL!=null) {
				if (fullLL.term.coeff==0) {
					if (prev==null) {
						front = front.next;
					}
					else {
						prev.next = fullLL.next;
					}
				}
				prev = fullLL;
				fullLL = fullLL.next;
			}
			return front;

		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
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
	public static Node multiply(Node poly1, Node poly2) {
		Node front = null;
		Node ptr = poly1;
		while (ptr!=null) {
			Node ptr2=poly2;
			while (ptr2!=null) {
				boolean coUsed = false;
				int co  = ptr.term.degree + ptr2.term.degree;
				for (Node pointer = front; pointer!=null; pointer = pointer.next) {
					if (pointer.term.degree==co) {
						pointer.term.coeff = pointer.term.coeff+(ptr.term.coeff*ptr2.term.coeff);
						coUsed = true;
						break;
					}
				}
				if (coUsed ==true) {
					ptr2 = ptr2.next;
				}
				else {
					Node pointer = front;
					Node prev = front;
					while (pointer!=null) {
						if (co<pointer.term.degree) {
							break;
						}
						else {
							prev = pointer;
							pointer = pointer.next;
						}
					}
					Node newNode = new Node(ptr.term.coeff*ptr2.term.coeff,ptr.term.degree+ptr2.term.degree,null);
					if (front ==null) {
						front = newNode;
					}
					else {
						prev.next = newNode;
						newNode.next = pointer;
						
					}
					ptr2 = ptr2.next;
				}
			}
			ptr = ptr.next;


		}
		return front;
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		
		float sum = 0;
		Node ptr = poly;
		float expSum = 0;
		while (ptr!=null) {
			expSum = (float) Math.pow(x, ptr.term.degree);
			sum = ptr.term.coeff*expSum + sum;
			ptr = ptr.next;
			}
			
		
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		return sum;
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
