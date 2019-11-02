import java.io.*;
import java.util.*;
import java.util.Map.Entry;

/** program to find compression ratio using Huffman coding algorithm
 */
public class Main {
	
	static long fileSize = 0;
	
	
	/*
	 * method to store each char of the given file alongside its frequency count in a hash map.
	 * Also determines the size of the original file in bytes by
	 * incrementing whenever a character is reached.
	 *
	 * @param  in	 	the file scanner object
	 * @return HashMap	the character->occurrence hashmap
	 */

	private static HashMap<Character, Integer> createMap(Scanner in) throws IOException{
		
		HashMap<Character,Integer> charMap = new HashMap<Character,Integer>();
		int newLineCount = 0;
		int charCount = 0;

		String line = in.nextLine();
		while(line!=null){
			
			newLineCount++;
			fileSize++;
			charCount++;
			if(!line.isEmpty()){
				//iterate through all the characters in each line
				for(int i =0; i<line.length(); i++){
					charCount++;
					fileSize++;
					char c = line.charAt(i);
					//if the char is not in the hash map then add it to it, otherwise increment its frequency count
					if(charMap.get(c)==null){
						charMap.put(c, 1);
					}else{
						int count = charMap.get(c);
						count = count + 1;
						charMap.put(c, count);
					}
				}
			}
			if(in.hasNextLine()){
				line = in.nextLine();
			}else{
				line = null;
			}
		}
		charMap.put('\n', newLineCount);
		return charMap;
	}

	
	/*
	 * method which maps each key value pair to a node in an array list using an iterator
	 */
	private static ArrayList<Node> getLeafNodes(HashMap<Character,Integer> charMap) {
		ArrayList<Node> nodeList = new ArrayList<Node>();
		
		//creates an iterator over the hashmap
		Iterator<?> it = charMap.entrySet().iterator();
		while(it.hasNext()){
			//for each element, a pair is generated from the entry and from this a node is created with the weight assigned py the pair value
			@SuppressWarnings("unchecked")
			Map.Entry<Character,Integer> pair = (Entry<Character, Integer>)it.next();
			Node n = new Node(pair.getKey());
			int f = pair.getValue();
			n.setWeight(f);
			//add each new node to the arraylist
			nodeList.add(n);
			
		}
		return nodeList;
		
	}
	
	
	/*
	 * method to obtain the weight of the internal nodes of a tree so the tree does not need to be built for efficiency
	 */
	private static long getWeight(ArrayList<Node> leafNodes){
		
		long weight = 0;
		
		//loop until there is only one node left in the list
		while(leafNodes.size() > 1){
			Node newNode = new Node('\0');
			Node x = leafNodes.get(0);
			Node y = leafNodes.get(1);
			
			//assign the new weight of the parent node to the combined weights of the 2 minimum children
			int w = x.getWeight() + y.getWeight();
			newNode.setWeight(w);
			
			//remove the 2 minimum weights
			leafNodes.remove(1);
			leafNodes.remove(0);
			
			//insert the new parents node and increment the overall weight by the new internal node weight
			leafNodes = insert(leafNodes, newNode);
			weight += newNode.getWeight();
		}
		return weight;
	}
	
	
	/*
	 * method to insert in order so the arraylist doesn't need to be sorted continuously
	 */
	
	private static ArrayList<Node> insert(ArrayList<Node> leafNodes, Node newNode){
		//if the arraylist of nodes is empty just add the node to it 
		if(leafNodes.size() == 0){
			leafNodes.add(newNode);
		}else{
			//else loop through the array list and add it at its appropriately sorted index
			boolean added = false;
			for(int i = 0; i < leafNodes.size(); i++){
				if(newNode.getWeight() < leafNodes.get(i).getWeight()){
					leafNodes.add(i, newNode);
					added = true;
					break;
				}
				
			}
			//this if statement is run incase the new node is greater than all the nodes in the array list, so it's just added at the end
			if(!added){
				leafNodes.add(newNode);
			}
			
		}
		return leafNodes;
	}
	

	
	
	public static void main(String[] args) throws IOException {

		long start = System.currentTimeMillis();
		
		String inputFileName = args[0];
		FileReader reader = new FileReader(inputFileName);
		Scanner in = new Scanner(reader);
		
		HashMap<Character, Integer> charMap = createMap(in);
		//close the readers once done with them
		
		reader.close();
		
		//create an arraylist of nodes
		
		ArrayList<Node> leafNodes = getLeafNodes(charMap);
		
		//sorts the array list of leaf nodes in increasing order of weight using collections class
		
		Collections.sort(leafNodes, new Comparator<Node>(){
			   public int compare(Node o1, Node o2){
			      return o1.getWeight() - o2.getWeight();
			   }
			});

		//gets the WPL by getting the sum of the weight of the internal nodes
		long WPL = getWeight(leafNodes);
	
		String outputFileName = args[1];
		FileWriter writer = new FileWriter(outputFileName);
		writer.write("Input file " + inputFileName + "  Huffman algorithm\n\n");
		
		double ratio = (double)WPL/(fileSize*8);
		writer.write("Original file length in bits: " + fileSize*8 + "\n");
		writer.write("Compressed file length in bits: " + WPL +"\n");
		writer.write("Compressed ratio = " + Math.round(ratio *10000.0) / 10000.0 + "\n");

		long end = System.currentTimeMillis();
		writer.write("\nElapsed time: " + (end - start) + " milliseconds" + "\n");
		writer.close();
	}

}
