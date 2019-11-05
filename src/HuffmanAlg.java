import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Class to represent a Huffman Tree Algorithm
 */

public class HuffmanAlg {
    static private long fileSize = 0;
    private String inFile;
    private HashMap<Character, Integer> charMap;
    private ArrayList<Node> leafNodes;

    public HuffmanAlg(String inFile) {
        this.inFile = inFile;
        this.charMap = new HashMap<>();
        this.leafNodes = new ArrayList<>();
    }

    public void createMap() throws IOException {
        FileReader reader = new FileReader(this.inFile);
        Scanner in = new Scanner(reader);
        int newLineCount = 0;
        int charCount = 0;

        String line = in.nextLine();
        while (line != null) {

            newLineCount++;
            fileSize++;
            charCount++;
            if (!line.isEmpty()) {
                //iterate through all the characters in each line
                for (int i = 0; i < line.length(); i++) {
                    charCount++;
                    fileSize++;
                    char c = line.charAt(i);
                    //if the char is not in the hash map then add it to it, otherwise increment its frequency count
                    if (this.charMap.get(c) == null) {
                        this.charMap.put(c, 1);
                    } else {
                        int count = this.charMap.get(c);
                        count = count + 1;
                        this.charMap.put(c, count);
                    }
                }
            }
            if (in.hasNextLine()) {
                line = in.nextLine();
            } else {
                line = null;
            }
        }
        this.charMap.put('\n', newLineCount);
        reader.close();
        in.close();
    }

    /*
     * method which maps each key value pair to a node in an array list using an iterator
     */
    public void getLeafNodes() {

        //creates an iterator over the hashmap
        Iterator<?> it = charMap.entrySet().iterator();
        while(it.hasNext()){
            //for each element, a pair is generated from the entry and from this a node is created with the weight assigned py the pair value
            @SuppressWarnings("unchecked")
            Map.Entry<Character,Integer> pair = (Map.Entry<Character, Integer>)it.next();
            Node n = new Node(pair.getKey());
            int f = pair.getValue();
            n.setWeight(f);
            //add each new node to the arraylist
            this.leafNodes.add(n);

        }
    }

    /*
     * method to obtain the weight of the internal nodes of a tree so the tree does not need to be built for efficiency
     */
    public long getWeight(ArrayList<Node> leafNodes){

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

    public ArrayList<Node> insert(ArrayList<Node> leafNodes, Node newNode){
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

    public ArrayList<Node> sortLeafNodes(){
        ArrayList<Node> newNodes = new ArrayList<Node>();
        newNodes = (ArrayList<Node>) this.leafNodes.clone();

        Collections.sort(newNodes, new Comparator<Node>(){
            public int compare(Node o1, Node o2){
                return o1.getWeight() - o2.getWeight();
            }
        });
        return newNodes;
    }


    public long getFileSize(){
        return fileSize;
    }
}

