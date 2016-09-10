package net.syrotskyi.projects.socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final long TIME_TO_WORK = TimeUnit.SECONDS.toMillis(10);
    private static final int CLIENTS_NUMBER = 10;
    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 5050;
        ExecutorService executor = Executors.newFixedThreadPool(CLIENTS_NUMBER);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Server started");
            logger.info("port: " + port);
            long startTime = new Date().getTime();
            while (new Date().getTime() < startTime + TIME_TO_WORK) {
                executor.submit(new MessageProcessor(serverSocket.accept()));
            }
        }
    }
}
