package controles;
import java.security.SecureRandom;
import java.util.Random;

public class CodeGenerator {

    // Define the characters allowed in the code
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    // Define the length of the code
    private static final int CODE_LENGTH = 6;

    // Method to generate a random code
    public static String generateRandomCode() {
        Random random = new SecureRandom();
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return code.toString();
    }

    public static void main(String[] args) {
        // Example usage
        String randomCode = generateRandomCode();
        System.out.println("Random Code: " + randomCode);
    }


}
