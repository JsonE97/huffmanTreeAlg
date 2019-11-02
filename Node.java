
public class Node {

	private char letter; // label on incoming branch
	private int weight;
	
	/** create a new node with letter c */
	public Node(char c){
		letter = c;
		weight = 0;
	}
	
	

	// accessors
	public char getLetter(){
    	return letter;
    }
	
	public int getWeight(){
		return weight;
	}
	
	
	// mutators
	public void setLetter(char c){
    	letter = c;
    }
	
	
	public void setWeight(int f){
		weight = f;
		
	}
    
}

