import java.io.*;
import java.net.Socket;
import java.util.Random;
import java.util.zip.CRC32;

public class Client4_21MIS1118 {
    public static void main(String[] args) {
        try {
            // Establish a connection with the server
            Socket socket = new Socket("localhost", 1234);
            System.out.println("Connected to the server.");

            // Prepare data to be sent
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter the message to send: ");
            String message = reader.readLine();

            // Create an output stream to send data to the server
            OutputStream outputStream = socket.getOutputStream();

            // Create a checksum object
            CRC32 checksum = new CRC32();

            // Calculate checksum of the message
            checksum.reset();
            checksum.update(message.getBytes());
            long messageChecksum = checksum.getValue();

            // Create a packet with message and checksum
            String packet = message + "|" + messageChecksum;

            // Introduce data loss by randomly flipping some bits
            Random random = new Random();
            char[] corruptedPacket = packet.toCharArray();
            for (int i = 0; i < packet.length(); i++) {
                if (random.nextDouble() < 0.2) { // 20% chance of flipping each bit
                    corruptedPacket[i] = (corruptedPacket[i] == '0') ? '1' : '0';
                }
            }

            // Send the corrupted packet to the server
            outputStream.write(new String(corruptedPacket).getBytes());

            // Close the connection
            socket.close();
            System.out.println("Connection closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
