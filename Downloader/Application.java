package ru.kpfu.itis.belskaya;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Application {
    private Scanner sc = new Scanner(System.in);
    private int counter = 0;
    private ArrayList<ThreadForDownloading> listOfThreads = new ArrayList<>();
    public void work() {
        while (true) {
            updateTheCounter();
            String command = sc.nextLine();
            if (command.equals("Download file")) {
                if (counter < 10) {
                    String link = sc.nextLine();
                    ThreadForDownloading thread = null;
                    try {
                        thread = new ThreadForDownloading(new DownloadingRunnable(link));
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                        continue;
                    }
                    thread.start();
                    listOfThreads.add(thread);
                    counter += 1;
                } else {
                    System.err.println("You cannot upload more than 10 files at the same time");
                }
            } else if (command.equals("Get status")) {
                for (int i = 0; i < listOfThreads.size(); i++) {
                    try {
                        System.err.println("File " + (i+1) + ": " + listOfThreads.get(i).getStatus());
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                }
            } else if (command.startsWith("Pause ")) {
                listOfThreads.get(Integer.parseInt(command.substring(6))-1).pause();
            } else if (command.startsWith("Revive ")) {
                listOfThreads.get(Integer.parseInt(command.substring(7))-1).revive();
            } else if (command.startsWith("Size ")) {
                try {
                    System.out.println(listOfThreads.get(Integer.parseInt(command.substring(5)) - 1).findSize());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                };
            } else {
                System.err.println("Unknown command");
            }
        }
    }

    public void updateTheCounter() {
        for (ThreadForDownloading thread : listOfThreads) {
            try {
                if (thread.getDownloaded() == thread.findSize()) {
                    counter -= 1;
                }
            } catch (IOException e) {
                continue;
            }
        }
    }
}
