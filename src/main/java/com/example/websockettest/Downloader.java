package com.example.websockettest;

public class Downloader implements Runnable {
        private GreetingController greetingController;

        public Downloader(GreetingController greetingController) {
                this.greetingController = greetingController;
        }

        @Override public void run() {
                try {
                        Thread.sleep(10000);
                        greetingController.fireDownload();
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
        }
}
