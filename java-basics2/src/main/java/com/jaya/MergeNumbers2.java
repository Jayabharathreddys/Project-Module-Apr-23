package com.jaya;
import java.io.*;
import java.util.*;

public class MergeNumbers2 {

    public static void main(String[] args1) {
        try {
            List<Integer> numbers = new ArrayList<>();
            //String[] args = {"C:\\scaler\\in1.txt","C:\\scaler\\in2.txt"};
            String[] args = {"InputFiles/in1.txt","InputFiles/in2.txt"};
            // Open input files for reading on separate threads
            List<Thread> readerThreads = new ArrayList<>();
            for (String arg : args) {
                Thread readerThread = new Thread(() -> {
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(arg));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            numbers.add(Integer.parseInt(line.trim()));
                        }
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                readerThread.start();
                readerThreads.add(readerThread);
            }

            // Wait for reader threads to finish
            for (Thread readerThread : readerThreads) {
                readerThread.join();
            }

            numbers.forEach((n)-> System.out.println(n));

            System.out.println("After sorting-->");
            // Sort the numbers
            Collections.sort(numbers);

            numbers.forEach((n)-> System.out.println(n));
            String outFile = "OutputFiles/out.txt";
            // Open output file for writing on a separate thread
            BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
            Thread writerThread = new Thread(() -> {
                try {
                    for (int number : numbers) {
                        writer.write(Integer.toString(number));
                        writer.newLine();
                    }
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writerThread.start();

            // Wait for writer thread to finish
            writerThread.join();

            System.out.println("Merged and sorted numbers written to out.txt.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

