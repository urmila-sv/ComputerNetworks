import java.util.Scanner;

public class HammingCodeCheck_21MIS1118 {
    public static void main(String[] args) {
        // Define the code parameters (code length and minimum Hamming distance)
        int codeLength = 7;
        int minHammingDistance = 3;

        // Read the binary string input from the user
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a binary string: ");
        String inputString = scanner.nextLine();

        // Check if the binary string satisfies the minimum Hamming distance requirement
        boolean meetsRequirement = checkHammingDistance(inputString, codeLength, minHammingDistance);

        // Print the result
        if (meetsRequirement) {
            System.out.println("The binary string meets the minimum Hamming distance requirement.");
        } else {
            System.out.println("The binary string does not meet the minimum Hamming distance requirement.");
        }
    }

    // Function to check if a binary string satisfies the minimum Hamming distance requirement
    private static boolean checkHammingDistance(String binaryString, int codeLength, int minHammingDistance) {
        // Check if the length of the binary string matches the code length
        if (binaryString.length() != codeLength) {
            return false;
        }

        // Initialize the differing bits count
        int differingBits = 0;

        // Iterate through each bit in the binary string and compare it with the predefined Hamming code
        for (int i = 0; i < codeLength; i++) {
            if (binaryString.charAt(i) != '0') {
                differingBits++;
            }
        }

        // Check if the differing bits count exceeds the minimum Hamming distance
        return differingBits <= minHammingDistance;
    }
}
