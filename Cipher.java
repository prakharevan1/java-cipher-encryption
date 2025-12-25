public class Cipher {
    public static void main(String[] args) {
        String message = "GEEKSFORGEEKS";
        String key = "AYUSH";
        System.out.println(vigenere(message, key));
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
