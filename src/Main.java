import java.io.*;
import java.util.*;

/**
 * program to find compression ratio using Huffman coding algorithm
 */
public class Main {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        String inputFileName = "";
        String outputFileName = "";
        try {
            inputFileName = args[0];
        } catch (Exception e) {
            System.out.println("No input file given");
        }
        if (!inputFileName.isEmpty()) {
            HuffmanAlg hf = new HuffmanAlg(inputFileName);
            hf.performAlgorithm();
            long WPL = hf.getWeightedPathLength();
            try {
                outputFileName = args[1];
                FileWriter writer = new FileWriter(outputFileName);
                writer.write("Input file " + inputFileName + "  Huffman algorithm\n\n");

                double ratio = (double) WPL / (hf.getFileSize() * 8);
                writer.write("Original file length in bits: " + hf.getFileSize() * 8 + "\n");
                writer.write("Compressed file length in bits: " + WPL + "\n");
                writer.write("Compressed ratio = " + Math.round(ratio * 10000.0) / 10000.0 + "\n");

                long end = System.currentTimeMillis();
                writer.write("\nElapsed time: " + (end - startTime) + " milliseconds" + "\n");
                writer.close();
            } catch (Exception e) {
                System.out.println("No output file given");
            }
        }
    }
}
