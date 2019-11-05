import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Class to represent a Huffman Tree Algorithm
 */

public class HuffmanAlg {

    /**
     * Class attributes
     */
    private long fileSize;                          // size of the file in chars
    private String inFile;                          // string representing path of file to read in
    private HashMap<Character, Integer> charMap;    // character -> num occurrences hash map
    private ArrayList<Node> leafNodes;              // array list of leaf nodes for tree

    /**
     * HuffmanAlg - Constructor
     *
     * @param inFile - String - path name for file to read in
     */

    public HuffmanAlg(String inFile) {
        this.fileSize = 0;
        this.inFile = inFile;
        this.charMap = new HashMap<>();
        this.leafNodes = new ArrayList<>();
    }

    /**
     * createMap - class instance method to generate the token -> occurrences hash map
     *
     * @throws IOException - when file path invalid or failure with reading in
     */

    public void createMap() throws IOException {

        // actually setup the file scanner
        FileReader reader = new FileReader(this.inFile);
        Scanner in = new Scanner(reader);

        int newLineCount = 0;   // newline character frequency also needs stored in hashmap

        String line = in.nextLine();
        while ((line != null) && !line.isEmpty()) {
            newLineCount++;
            this.fileSize++;
            //iterate through all the characters in each line
            for (int i = 0; i < line.length(); i++) {
                this.fileSize++;
                char c = line.charAt(i);
                //if the char is not in the hash map then add it to it, otherwise increment its frequency count
                if (this.charMap.get(c) == null) {
                    this.charMap.put(c, 1);
                } else {
                    int count = this.charMap.get(c);
                    this.charMap.put(c, count + 1);
                }
            }
            line = in.hasNextLine() ? in.nextLine() : null;
        }
        this.charMap.put('\n', newLineCount);
        reader.close();     // close resources
        in.close();
    }

    /**
     * getLeafNodes - method which maps each key value pair to a node in an array list using an iterator
     */

    public void getLeafNodes() {
        Iterator it = charMap.entrySet().iterator();    // create an iterator over the hashmap
        while (it.hasNext()) {
            Map.Entry<Character, Integer> pair = (Map.Entry<Character, Integer>) it.next();
            Node node = new Node(pair.getKey());
            int freq = pair.getValue();
            node.setWeight(freq);
            this.leafNodes.add(node);
        }
    }

    /**
     * getWeight - method to obtain the weight of the internal nodes of a tree
     * so the tree does not need to be built for efficiency
     *
     * @param leafNodes - ArrayList<Node> - list of leaf nodes
     */
    public long getWeight(ArrayList<Node> leafNodes) {
        long weight = 0;
        while (leafNodes.size() > 1) {      //loop until there is only one node left in the list
            Node newNode = new Node();
            Node nodeX = leafNodes.get(0);
            Node nodeY = leafNodes.get(1);

            //assign the new weight of the parent node to the combined weights of the 2 minimum children
            int w = nodeX.getWeight() + nodeY.getWeight();
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

    /**
     * insert - method to insert in order so the arraylist doesn't need to be sorted continuously
     *
     * @param leafNodes - ArrayList<Node> - array list of leaf nodes
     * @param newNode   - Node            - new node to be inserted in order
     * @return leafNodes - ArrayList<Node> - new list of leaf nodes
     */

    public ArrayList<Node> insert(ArrayList<Node> leafNodes, Node newNode) {
        //if the arraylist of nodes is empty just add the node to it
        if (leafNodes.isEmpty()) {
            leafNodes.add(newNode);
        } else {
            //else loop through the array list and add it at its appropriately sorted index
            boolean added = false;
            for (int i = 0; i < leafNodes.size() && !added; i++) {
                if (newNode.getWeight() < leafNodes.get(i).getWeight()) {
                    leafNodes.add(i, newNode);
                    added = true;
                }
            }
            if (!added) {
                leafNodes.add(newNode);
            }
        }
        return leafNodes;
    }

    /**
     * sortLeafNodes - does a final sort on the array of leaf nodes - shouldn't require much
     *
     * @return - new array list of sorted leaf nodes
     */

    public ArrayList<Node> sortLeafNodes() {
        ArrayList<Node> newNodes = new ArrayList<Node>();
        newNodes = (ArrayList<Node>) this.leafNodes.clone();

        Collections.sort(newNodes, new Comparator<Node>() {
            public int compare(Node o1, Node o2) {
                return o1.getWeight() - o2.getWeight();
            }
        });
        return newNodes;
    }

    /**
     * getFileSize - quick getter to do calculations
     *
     * @return fileSize - long - size of file by number of chars
     */

    public long getFileSize() {
        return fileSize;
    }
}

