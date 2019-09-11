package com.example.sample.websocketupload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class WebSocketUpload {

    public static void main(String[] args) {
        // String fileName = "C:\\Users\\Administrator\\Desktop\\1.pcm";
        // String recogFileName = "C:\\Users\\Administrator\\Desktop\\1.txt";
        // new Thread(new UploadRunnable(fileName, recogFileName, "2", "fe90e80fb26d040e576d973c4eb4c9ca")).start();

        File txtDir = new File("C:\\Users\\Administrator\\Desktop\\demo2");
        File[] txtFiles = txtDir.listFiles();
        File audioDir = new File("D:\\download\\中文英文语音数据\\中文30小时\\wav\\train\\S0003");
        File[] audioFiles = audioDir.listFiles();

        for (int i = 0; i < txtFiles.length; i++) {
            System.out.println(audioFiles[i].getPath());
            System.out.println(txtFiles[i].getPath());
            new Thread(new UploadRunnable(audioFiles[i].getPath(), txtFiles[i].getPath(), "2", "fe90e80fb26d040e576d973c4eb4c9ca")).start();
        }
    }

    static class UploadRunnable implements Runnable {

        private Logger logger = LoggerFactory.getLogger(WebSocketUpload.class);

        private String fileName;
        private String recogFileName;
        private String userId;
        private String token;

        public UploadRunnable(String fileName, String recogFileName, String userId, String token) {
            super();
            this.fileName = fileName;
            this.recogFileName = recogFileName;
            this.userId = userId;
            this.token = token;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        @Override
        public void run() {
            WifiUploadClient uploadClient = new WifiUploadClient(fileName, recogFileName, userId, token);
            int startToSend = uploadClient.startToSend();
            logger.info(">>>>> START TO SEND, CODE: [{}]", startToSend);
        }

    }

}


