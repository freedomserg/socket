package net.syrotskyi.projects.socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageProcessor implements Runnable {
    private final Socket clientSocket;
    private static final Logger logger = LogManager.getLogger(MessageProcessor.class.getName());

    public MessageProcessor(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String inputLine;
            String outputLine;
            while ((inputLine = reader.readLine()) != null) {
                outputLine = inputLine;
                writer.println(outputLine);
                if (outputLine.equals("Bye.")) break;
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

    }
}
