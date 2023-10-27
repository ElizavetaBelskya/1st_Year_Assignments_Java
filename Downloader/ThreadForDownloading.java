

import ru.kpfu.itis.belskaya.DownloadingRunnable;

import java.io.*;

public class ThreadForDownloading extends Thread {

    private DownloadingRunnable downloadingRunnable;

    public ThreadForDownloading(DownloadingRunnable downloadingRunnable) {
        this.downloadingRunnable = downloadingRunnable;
    }

    public long getDownloaded() {
        return downloadingRunnable.getDownloaded();
    }


    public long findSize() throws IOException {
        return downloadingRunnable.findSize();
    }

    public void revive() {
        downloadingRunnable.revive();
    }


    public void pause() {
        downloadingRunnable.pause();
    }

    public String getStatus() throws IOException {
        return downloadingRunnable.getStatus();
    }

}
