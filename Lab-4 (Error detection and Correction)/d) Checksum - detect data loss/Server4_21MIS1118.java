import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.zip.CRC32;

public class Server4_21MIS1118 {
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
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String packet;
            while ((packet = reader.readLine()) != null) {
                // Extract the message and checksum from the packet
                String[] parts = packet.split("\\|");
                String message = parts[0];
                long receivedChecksum = Long.parseLong(parts[1]);

                // Calculate checksum of the received message
                checksum.reset();
                checksum.update(message.getBytes());
                long calculatedChecksum = checksum.getValue();

                // Compare the checksums
                if (receivedChecksum == calculatedChecksum) {
                    // Checksums match, process the message
                    System.out.println("Received message: " + message);
                } else {
                    // Checksums do not match, data loss detected
                    System.out.println("Data loss detected. Requesting retransmission.");
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
