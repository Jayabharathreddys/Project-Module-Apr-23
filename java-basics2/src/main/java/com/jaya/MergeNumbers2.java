package com.jaya;
import java.io.*;
import java.util.*;

public class MergeNumbers2 {

    public static void main(String[] args) {
        try {
            List<Integer> numbers = new ArrayList<>();

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

            // Sort the numbers
            Collections.sort(numbers);

            // Open output file for writing on a separate thread
            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\scaler\\out.txt"));
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

