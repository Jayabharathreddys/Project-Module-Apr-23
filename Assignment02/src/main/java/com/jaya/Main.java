package com.jaya;

import com.jaya.rest.RestClient;

import java.io.IOException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        var restClient = new RestClient();
        var apiResponse = restClient.getApi().getPhotos().execute();
        apiResponse.body().forEach(photo -> System.out.println("Titles->"+photo.getTitle()));
    }
}