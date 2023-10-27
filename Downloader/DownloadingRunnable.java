package ru.kpfu.itis.belskaya;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadingRunnable implements Runnable {
    private String PLACE_OF_STORAGE = "C:\\Users\\Elizaveta\\OneDrive\\Рабочий стол\\Прога\\Java 2 сем";
    private boolean paused = false;
    public String ANSI_RESET = "\u001B[0m";
    public String ANSI_BLUE = "\u001B[34m";
    private long downloaded = 0l;
    private URL url = null;

    public DownloadingRunnable(String link) throws IOException {
        try {
            url = new URL(link);
        } catch (MalformedURLException e) {
            throw new IOException("Link recognition error");
        }
    }
    @Override
    public void run() {
        String path = url.getPath();
        String shortName = path.substring(path.lastIndexOf("/"));
        File file = new File(PLACE_OF_STORAGE + shortName);
        try (BufferedInputStream in = new BufferedInputStream(url.openStream()); BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))){
            byte [] buffer = new byte[1024*1024];
            int b;
            while ((b = in.read(buffer, 0, 1024*1024))!= -1) {
                while (paused) {
                    Thread.sleep(1);
                }
                downloaded += b;
                out.write(buffer, 0, b);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public long findSize() throws IOException {
        long size = 0;
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            size = connection.getContentLengthLong();
        } catch (IOException e) {
            throw new IOException("It is impossible to determine the file size");
        }
        return size;
    }

    public long getDownloaded() {
        return downloaded;
    }


    public String getStatus() throws IOException {
        long nowDownloaded = getDownloaded();
        long sizeOfFile = findSize();
        return (nowDownloaded == sizeOfFile? " done": "")+ ANSI_BLUE + "\nCurrently downloaded:\n" + (int)Math.ceil((double)getDownloaded()/1024) + "Kb\n"
                + Math.ceil(((double) nowDownloaded/ (double) sizeOfFile) * 100) + "%" +
                (paused? "\nThe download is suspended" : "") + ANSI_RESET;
    }


    public void pause() {
        paused = true;
    }

    public void revive() {
        paused = false;
    }

}
