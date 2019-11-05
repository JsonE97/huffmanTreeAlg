import java.io.*;
import java.util.*;

/** program to find compression ratio using Huffman coding algorithm
 */
public class Main {
	
	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		String inputFileName = "";
		try {
			inputFileName = args[0];
		} catch (Exception e) {
			System.out.println("No input file given");
		}
		if (!inputFileName.isEmpty()) {
			HuffmanAlg hf = new HuffmanAlg(inputFileName);
			hf.createMap();
			//create an arraylist of nodes

			hf.getLeafNodes();
			//sorts the array list of leaf nodes in increasing order of weight using collections class

			ArrayList<Node> sortedNodes = hf.sortLeafNodes();


			//gets the WPL by getting the sum of the weight of the internal nodes
			long WPL = hf.getWeight(sortedNodes);

			String outputFileName = args[1];
			FileWriter writer = new FileWriter(outputFileName);
			writer.write("Input file " + inputFileName + "  Huffman algorithm\n\n");

			double ratio = (double)WPL/(hf.getFileSize()*8);
			writer.write("Original file length in bits: " + hf.getFileSize()*8 + "\n");
			writer.write("Compressed file length in bits: " + WPL +"\n");
			writer.write("Compressed ratio = " + Math.round(ratio *10000.0) / 10000.0 + "\n");

			long end = System.currentTimeMillis();
			writer.write("\nElapsed time: " + (end - startTime) + " milliseconds" + "\n");
			writer.close();
		}

	}

}
