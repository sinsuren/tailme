package com.conference.demo;

import java.io.File;
import java.io.RandomAccessFile;

public class Tailer implements Runnable {
    private boolean debug = false;

    private int runAfterNSeconds = 2000;
    private long lastKnownPosition = 0;

    private boolean shouldRun = true;
    private File file = null;

    public Tailer(String file, int interval) {
        this.file = new File(file);
        this.runAfterNSeconds = interval*1000;
    }

    private void printLine(String message) {
        System.out.println(message);
    }

    public void stopRunning() {
        shouldRun = false;
    }

    @Override
    public void run() {
        try {
            while(shouldRun) {
                Thread.sleep(runAfterNSeconds);
                long fileLength = file.length();

                if(fileLength > lastKnownPosition) {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                    randomAccessFile.seek(lastKnownPosition);

                    String line = null;
                    while((line = randomAccessFile.readLine())!= null) {
                        this.printLine(line);
                    }
                    lastKnownPosition = randomAccessFile.getFilePointer();
                    randomAccessFile.close();
                } else if(debug) {
                    this.printLine("Found no new line...");
                }
            }

        } catch (Exception e) {
         stopRunning();
        }
        if(debug) {
            printLine("Exiting the program....");
        }
    }
}
