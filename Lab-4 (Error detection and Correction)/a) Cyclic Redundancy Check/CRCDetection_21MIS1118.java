public class CRCDetection_21MIS1118 {
    public static void main(String[] args) {
        // Define the polynomial (X^5 + X^3 + 1)
        int[] polynomial = {1, 0, 0, 1, 0, 1};

        // Define the original data sequence
        int[] data = {1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1};

        // Calculate the degree of the polynomial
        int degree = polynomial.length - 1;

        // Append zeros to the original data sequence
        int[] encodedData = new int[data.length + degree];
        System.arraycopy(data, 0, encodedData, 0, data.length);

        // Initialize the CRC remainder
        int[] remainder = new int[degree];

        // Perform the CRC division
        for (int i = 0; i < data.length; i++) {
            int currentBit = encodedData[i];

            // XOR the remainder with the polynomial if the current bit is 1
            if (currentBit == 1) {
                for (int j = 0; j < degree; j++) {
                    remainder[j] ^= polynomial[j + 1];
                }
            }

            // Shift the remainder one bit to the left
            for (int j = 0; j < degree - 1; j++) {
                remainder[j] = remainder[j + 1];
            }

            // Set the last bit of the remainder to 0
            remainder[degree - 1] = 0;
        }

        // Append the CRC remainder to the original data sequence
        System.arraycopy(remainder, 0, encodedData, data.length, degree);

        // Introduce an error in the transmitted data
        encodedData[data.length + degree - 1] ^= 1;

        // Detect the error
        int[] transmittedData = encodedData.clone();
        int[] transmittedRemainder = new int[degree];

        for (int i = 0; i < transmittedData.length - degree; i++) {
            int currentBit = transmittedData[i];

            // XOR the transmitted remainder with the polynomial if the current bit is 1
            if (currentBit == 1) {
                for (int j = 0; j < degree; j++) {
                    transmittedRemainder[j] ^= polynomial[j + 1];
                }
            }

            // Shift the transmitted remainder one bit to the left
            for (int j = 0; j < degree - 1; j++) {
                transmittedRemainder[j] = transmittedRemainder[j + 1];
            }

            // Set the last bit of the transmitted remainder to 0
            transmittedRemainder[degree - 1] = 0;
        }

        // Check if an error is detected
        boolean errorDetected = false;
        for (int i : transmittedRemainder) {
            if (i != 0) {
                errorDetected = true;
                break;
            }
        }

        // Print the results
        System.out.println("Original Data: " + arrayToString(data));
        System.out.println("Encoded Data with Error: " + arrayToString(encodedData));
        System.out.println("Transmitted Data with Error: " + arrayToString(transmittedData));
        System.out.println("Error Detected: " + errorDetected);
    }

    // Helper method to convert an array of integers to a string representation
    private static String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i : array) {
            sb.append(i);
        }
        return sb.toString();
    }
}
