package com.conference.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        String filePath = "<file_path>";
        Tailer tailer = new Tailer(filePath, 1);

        executorService.execute(tailer);


    }
}
