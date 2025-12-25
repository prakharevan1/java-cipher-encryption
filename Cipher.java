public class Cipher {
    public static void main(String[] args) {
        String message = "GEEKSFORGEEKS";
        String key = "AYUSH";
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
            // get values of key and current message
            int currentKeyChar = newKey.charAt(i);
            int currentMessageChar = message.charAt(i);
            int encryptedCharNum = 0;

            // convert to range of [0-25]
            currentKeyChar -= 65;
            currentMessageChar -= 65;
            encryptedCharNum = (currentKeyChar + currentMessageChar) % 26;
            // convert back to ASCII
            encryptedCharNum += 'A';
            // append to string
            encryptedString += (char) encryptedCharNum;
        }
        return encryptedString;
    }

    public static String decryptVigenere(String encryptedMessage, String key) {
        String decryptedString = "";
        String newKey = keyGen(encryptedMessage, key);
        for (int i = 0; i < encryptedMessage.length(); i++) {
            // get values of key & curr msg
            int currentKeyChar = newKey.charAt(i);
            int currentEncryptedMessageChar = encryptedMessage.charAt(i);

            int decryptedCharNum = 0;

            // convert to range of [0-25]
            currentKeyChar -= 65; // Y, 24
            currentEncryptedMessageChar -= 65; // C, 2

            // add 26 so negative wraps around
            decryptedCharNum = (currentEncryptedMessageChar - currentKeyChar + 26) % 26;

            // convert back to ASCII
            decryptedCharNum += 'A';

            // append to string
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
            // get char
            char currentCharacter = message.charAt(i);

            // check for special characters
            if (isSpecialCharacter(currentCharacter)) {
                encryptedMessage += message.charAt(i);
                continue;
            }

            // check for lowercase characters
            boolean lowerCase = false;
            if (currentCharacter > 90) {
                lowerCase = true;
                currentCharacter -= 32;
            }
            // convert the character to a range of 0-25 corresponding to A-Z
            int encryptedCharNum = (int) (currentCharacter - 65);

            // map it to its opposite letter on the alphabet spectrum (A -> Z, B -> Y, ...)
            encryptedCharNum = 25 - encryptedCharNum;

            // convert back to ASCII
            encryptedCharNum += 'A';
            char encryptedCharacter = (char) encryptedCharNum;

            // check for uppercase or lowercase
            if (lowerCase) {
                encryptedMessage += String.valueOf(encryptedCharacter).toLowerCase();
                continue;
            }
            encryptedMessage += String.valueOf(encryptedCharacter).toUpperCase();
        }
        return encryptedMessage;
    }

    public static boolean isSpecialCharacter(char character) {
        return character < 65 || character > 122;
    }
}
