package com.jaya;

import java.io.*;
import java.util.*;

public class MergeNumbers {

    public static void main(String[] args) {
        try {
            // Open input files for reading
            BufferedReader reader1 = new BufferedReader(new FileReader("C:\\scaler\\in1.txt"));
            BufferedReader reader2 = new BufferedReader(new FileReader("C:\\scaler\\in2.txt"));

            // Open output file for writing
            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\scaler\\out.txt"));

            // Read numbers from input files
            List<Integer> numbers = new ArrayList<>();
            String line;
            while ((line = reader1.readLine()) != null) {
                numbers.add(Integer.parseInt(line.trim()));
            }
            while ((line = reader2.readLine()) != null) {
                numbers.add(Integer.parseInt(line.trim()));
            }

            // Sort the numbers
            Collections.sort(numbers);

            // Write the sorted numbers to the output file
            for (int number : numbers) {
                writer.write(Integer.toString(number));
                writer.newLine();
            }

            // Close input and output files
            reader1.close();
            reader2.close();
            writer.close();

            System.out.println("Merged and sorted numbers written to out.txt.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

