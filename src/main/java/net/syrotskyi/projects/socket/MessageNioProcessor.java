package net.syrotskyi.projects.socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;

public class MessageNioProcessor implements Runnable {

    private final SocketChannel socketChannel;
    private static final Logger logger = LogManager.getLogger(MessageNioProcessor.class.getName());

    public MessageNioProcessor(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        try {
            ByteBuffer buf = ByteBuffer.allocate(64);
            int bytesRead = 0;

            while ((bytesRead = socketChannel.read(buf)) != -1) {
                String input = new String(buf.array());

                buf.flip();
                while (buf.hasRemaining()) {
                    socketChannel.write(buf);
                }
                buf.clear();
                if ("Bye.".equals(input)) {
                    break;
                }
            }

        }catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }
}
