public class Cipher {
    public static void main(String[] args) {
        String message = "GEEKSFORGEEKS";
        String key = "AYUSH";
        String encrypted = vigenere(message, key);
        String decrypted = decryptVigenere(encrypted, key);
        System.out.println(encrypted);
        System.out.println(decrypted);
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
}
