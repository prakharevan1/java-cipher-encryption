public class Cipher {
    public static void main(String[] args) {
        String message = "Geeksforgeeks";
        String key = "YAAI";
        String encryptedMessage = encrypt(message, key);
        String decryptedMessage = decrypt(encryptedMessage, key);

        System.out.println("Message: " + message);
        System.out.println("Key: " + key);
        System.out.println("Encrypted message: " + encryptedMessage);
        System.out.println("Decrypted message: " + decryptedMessage);
    }

    public static String encrypt(String message, String key) {
        String newKey = keyGen(message, key);
        String encrypted = vigenere(message, newKey);
        encrypted = atBash(encrypted);
        return encrypted;
    }

    public static String decrypt(String encryptedMessage, String key) {
        String newKey = keyGen(encryptedMessage, key);
        String decrypted = decryptVigenere(atBash(encryptedMessage), newKey);
        return decrypted;
    }

    public static String vigenere(String message, String key) {
        String newKey = keyGen(message, key);
        String encryptedString = "";
        for (int i = 0; i < message.length(); i++) {
            int currentKeyChar = newKey.charAt(i);
            int currentMessageChar = message.charAt(i);
            int encryptedCharNum = 0;
            boolean isLowerCase = false;

            if (isSpecialCharacter((char) currentMessageChar)) {
                encryptedString += message.charAt(i);
                continue;
            }

            if (Character.isLowerCase(currentMessageChar)) {
                isLowerCase = true;
                currentMessageChar -= 32;
            }

            // convert to range of [0-25] from ascii
            currentKeyChar -= 'A';
            currentMessageChar -= 'A';
            encryptedCharNum = (currentKeyChar + currentMessageChar) % 26;

            // convert back to ASCII
            encryptedCharNum += 'A';
            // append to string
            if (isLowerCase) {
                encryptedString += Character.toLowerCase((char) encryptedCharNum);
                continue;
            }
            encryptedString += (char) encryptedCharNum;
        }
        return encryptedString;
    }

    public static String decryptVigenere(String encryptedMessage, String key) {
        String decryptedString = "";
        String newKey = keyGen(encryptedMessage, key);
        for (int i = 0; i < encryptedMessage.length(); i++) {
            int currentKeyChar = newKey.charAt(i);
            int currentEncryptedMessageChar = encryptedMessage.charAt(i);
            boolean isLowerCase = false;

            if (isSpecialCharacter((char) currentEncryptedMessageChar)) {
                decryptedString += encryptedMessage.charAt(i);
            }

            if (Character.isLowerCase((char) currentEncryptedMessageChar)) {
                isLowerCase = true;
                currentEncryptedMessageChar -= 32;
            }

            int decryptedCharNum = 0;

            // convert to range of [0-25]
            currentKeyChar -= 'A';
            currentEncryptedMessageChar -= 'A';

            // add 26 so negative wraps around
            decryptedCharNum = (currentEncryptedMessageChar - currentKeyChar + 26) % 26;

            // convert back to ASCII
            decryptedCharNum += 'A';

            if (isLowerCase) {
                decryptedString += Character.toLowerCase((char) decryptedCharNum);
                continue;
            }
            decryptedString += (char) decryptedCharNum;
        }

        return decryptedString;
    }

    public static String keyGen(String message, String key) {
        String newKey = "";
        for (int i = 0; i < message.length(); i++) {
            // LMAO -> LMAOLMAO
            if (i > key.length() - 1) {
                newKey += key.charAt(i % key.length());
                continue;
            }
            newKey += key.charAt(i);
        }
        return newKey;
    }

    public static String atBash(String message) {
        String encryptedMessage = "";
        for (int i = 0; i < message.length(); i++) {
            char currentCharacter = message.charAt(i);

            if (isSpecialCharacter(currentCharacter)) {
                encryptedMessage += message.charAt(i);
                continue;
            }

            boolean lowerCase = false;
            if (currentCharacter > 90) {
                lowerCase = true;
                currentCharacter -= 32;
            }

            // convert the character to a range of [0-25]
            int encryptedCharNum = (int) (currentCharacter - 'A');

            // map it to its opposite letter on the alphabet spectrum (A -> Z, B -> Y, ...)
            encryptedCharNum = 25 - encryptedCharNum;

            // convert back to ASCII
            encryptedCharNum += 'A';
            char encryptedCharacter = (char) encryptedCharNum;

            if (lowerCase) {
                encryptedMessage += String.valueOf(encryptedCharacter).toLowerCase();
                continue;
            }
            encryptedMessage += String.valueOf(encryptedCharacter).toUpperCase();
        }
        return encryptedMessage;
    }

    public static boolean isSpecialCharacter(char character) {
        return character < 'A' || character > 'z';
    }
}
