import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.zip.CRC32;

public class Checksum_Server_21MIS1118 {
    private static final int PACKET_SIZE = 1024;

    public static void main(String[] args) {
        try {
            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Server started. Waiting for client...");

            // Accept a client connection
            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");

            // Create an input stream to receive data from the client
            InputStream inputStream = socket.getInputStream();

            // Create a checksum object
            CRC32 checksum = new CRC32();

            // Receive and process packets from the client
            byte[] buffer = new byte[PACKET_SIZE];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                // Extract the checksum from the received packet
                long receivedChecksum = 0;
                for (int i = 0; i < 8; i++) {
                    receivedChecksum |= (buffer[bytesRead - 8 + i] & 0xFF) << (i * 8);
                }

                // Calculate checksum of the received data
                checksum.reset();
                checksum.update(buffer, 0, bytesRead - 8);
                long calculatedChecksum = checksum.getValue();

                // Compare the checksums
                if (receivedChecksum == calculatedChecksum) {
                    // Checksums match, process the data
                    System.out.println("Received packet: " + new String(buffer, 0, bytesRead - 8));
                } else {
                    // Checksums do not match, request retransmission
                    System.out.println("Checksum mismatch. Requesting retransmission.");
                }
            }

            // Close the connection
            socket.close();
            serverSocket.close();
            System.out.println("Connection closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
