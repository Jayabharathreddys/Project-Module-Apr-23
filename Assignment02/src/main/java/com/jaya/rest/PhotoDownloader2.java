package com.jaya.rest;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

public class PhotoDownloader2 {
    String SAVE_DIR = "C:///photoes2"+File.separator;
    public void downloadPhoto(List<Photo2> p) throws IOException {
        System.out.println("Entered into downloadPhotos...");

        HashMap<Integer, Integer> map = new HashMap();
        for (Photo2 x : p){
            int id = x.getId();
            String url = x.getUrl();
            int albumId = x.getAlbumId();
            String fileName = "Photo_"+id+".jpg";
            String savePath =  SAVE_DIR+"albumID_"+albumId+File.separator+fileName;
            if(map.getOrDefault(albumId, 0) <= 10) {
                startDownloading(url, albumId, savePath);
                map.put(albumId, map.getOrDefault(albumId, 0) +1);
            }
        }
    }

    private void startDownloading(String url, int albumId, String savePath) throws IOException {
        OkHttpClient ok = new OkHttpClient();
        var req =  new Request.Builder().url(url).build();
        try (Response res = ok.newCall(req).execute()){
            if (!res.isSuccessful()) {
                throw new IOException("Unexpected code " + res);
            }
            InputStream input =  res.body().byteStream();
            File dir =  new File(SAVE_DIR+"albumID_"+albumId);
            if (!dir.exists()){
                dir.mkdirs();
            }
           /* FileOutputStream outputStream = new FileOutputStream(savePath);
            byte[] newBuffer = new byte[1024];
            int byteReads;

            while ((byteReads = input.read(newBuffer))!=-1){
                outputStream.write(newBuffer,0,byteReads);
            }*/
            FileOutputStream outputStream = new FileOutputStream(savePath, true);
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = input.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            input.close();
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
