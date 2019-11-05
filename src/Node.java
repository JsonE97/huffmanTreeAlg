
public class Node {

	private char letter; // label on incoming branch
	private int weight;

	/**
	 * Node Constructor
	 * @param c - character for the new node
	 */
	public Node(char c){
		this.letter = c;
		this.weight = 0;
	}


	/**
	 * Public Getters
	 */
	public char getLetter(){
    	return this.letter;
    }
	
	public int getWeight(){
		return this.weight;
	}


	/**
	 * Public Setters
	 */

	/**
	 *
	 * @param c - new char for the current node
	 */
	public void setLetter(char c){
    	this.letter = c;
    }

	/**
	 *
	 * @param f - new frequency for the weight of the node
	 */
	public void setWeight(int f){
		this.weight = f;
	}
}

