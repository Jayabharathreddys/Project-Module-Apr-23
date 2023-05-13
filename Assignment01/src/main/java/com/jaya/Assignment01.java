package com.jaya;

import java.io.*;
import java.util.*;

public class Assignment01 {

    public static void main(String[] args) {

        try {
            List<Integer> numbers = new ArrayList<>();
            String[] files = {"InputFiles/in1.txt", "InputFiles/in2.txt"};
            List<Thread> readerThreads = new ArrayList<>();
            for (String file : files) {
                Thread readerThread = new Thread(() -> {
                    try {
                        BufferedReader bf = new BufferedReader(new FileReader(file));
                        String line;
                        while ((line = bf.readLine()) != null) {
                            numbers.add(Integer.parseInt(line.trim()));
                        }
                        bf.close();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                );
                readerThread.start();
                readerThreads.add(readerThread);
            }

            for (Thread read : readerThreads)
                read.join();

            numbers.forEach((n) -> System.out.println(n));
            System.out.println("After sorting...");

            Collections.sort(numbers);

            numbers.forEach((n) -> System.out.println(n));
            String out = "OutputFiles/out.txt";

            BufferedWriter bw = new BufferedWriter(new FileWriter(out));
            Thread writer = new Thread(() -> {
                try {
                    for (Integer number : numbers) {
                        bw.write(Integer.toString(number));
                        bw.newLine();
                    }
                    bw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            );
            writer.start();
            writer.join();
            System.out.println("Merged and sorted numbers written to out.txt.");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

