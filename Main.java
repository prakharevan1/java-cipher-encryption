import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input string to be encrypted:");
        String message = scanner.nextLine();
        System.out.println("Input key:");
        String key = scanner.nextLine();

        String encryptedMessage = Cipher.encrypt(message, key);
        String decryptedMessage = Cipher.decrypt(encryptedMessage, key);

        System.out.println("Message: " + message);
        System.out.println("Key: " + key);
        System.out.println("Encrypted message: " + encryptedMessage);
        System.out.println("Decrypted message: " + decryptedMessage);

        scanner.close();
    }
}
