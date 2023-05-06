package com.jaya;//package com.jaya;

import com.jaya.greet.Greeting;
import com.jaya.http.Client;

import java.util.Arrays;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        System.out.println("args"+ Arrays.toString(args));
        String arg ="greet";
        if(args.length > 0){
            arg = args[0];
        }
        switch (arg){
            case "greet":
                System.out.println("Greeting: "+new Greeting().greeting());
                break;
            case "http":
                Client c =  new Client();
                String res = c.getUrl("https://example.com");
                System.out.println("res: "+res);
                break;
            case "rest":
                break;
        }
    }
}