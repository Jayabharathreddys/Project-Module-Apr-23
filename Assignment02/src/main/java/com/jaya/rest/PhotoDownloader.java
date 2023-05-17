package com.jaya.rest;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PhotoDownloader {
    private final String SAVE_DIR = "/photos/";

    public void downloadPhotos(List<Photo> photos) throws IOException {
        OkHttpClient client = new OkHttpClient();

        for (Photo photo : photos) {
            String url = photo.getUrl();
            int albumId = photo.getAlbumId();
            int photoId = photo.getId();
            String fileName = "photo_" + photoId + ".jpg";
            String savePath = SAVE_DIR + "album_" + albumId + "/" + fileName;

            var request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                InputStream inputStream = response.body().byteStream();
                File saveDir = new File(SAVE_DIR + "album_" + albumId);

                if (!saveDir.exists()) {
                    saveDir.mkdirs();
                }

                FileOutputStream outputStream = new FileOutputStream(savePath);
                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                outputStream.close();
                inputStream.close();
            }
        }
    }
}
