import java.io.*;
import java.net.Socket;
import java.util.zip.CRC32;

public class Checksum_Client_21MIS1118 {
    private static final int PACKET_SIZE = 1024;

    public static void main(String[] args) {
        try {
            // Establish a connection with the server
            Socket socket = new Socket("localhost", 1234);
            System.out.println("Connected to the server.");

            // Prepare data to be sent
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter the data to send: ");
            String inputData = reader.readLine();
            byte[] data = inputData.getBytes();

            // Create an output stream to send data to the server
            OutputStream outputStream = socket.getOutputStream();

            // Create a checksum object
            CRC32 checksum = new CRC32();

            // Send packets to the server
            int offset = 0;
            while (offset < data.length) {
                int length = Math.min(PACKET_SIZE, data.length - offset);
                byte[] packet = new byte[length + 8]; // 8 bytes for checksum
                System.arraycopy(data, offset, packet, 0, length);

                // Calculate checksum
                checksum.reset();
                checksum.update(packet, 0, length);
                long packetChecksum = checksum.getValue();

                // Include the checksum in the packet
                for (int i = 0; i < 8; i++) {
                    packet[length + i] = (byte) ((packetChecksum >> (i * 8)) & 0xFF);
                }

                // Send the packet to the server
                outputStream.write(packet);

                offset += length;
            }

            // Close the connection
            socket.close();
            System.out.println("Connection closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
