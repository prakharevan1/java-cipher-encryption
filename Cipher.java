public class Cipher {
    public static String encrypt(String message, String key) {
        String newKey = keyGen(message, key);
        String encrypted = vigenere(message, newKey);
        encrypted = atBash(encrypted);
        encrypted = emojiReplace(encrypted);
        return encrypted;
    }

    public static String decrypt(String encryptedMessage, String key) {
        String newKey = keyGen(encryptedMessage, key);
        String decrypted = decryptVigenere(atBash(decryptEmojiReplace(encryptedMessage)), newKey);
        return decrypted;
    }

    public static String emojiReplace(String message) {
        String encryptedMessage = message;
        for (int i = 0; i < message.length(); i++) {
            char currentCharacter = message.charAt(i);

            String firstHalf = encryptedMessage.substring(0, i);
            String secondHalf = encryptedMessage.substring(i + 1);
            switch (isVowel(currentCharacter)) {
                // AE
                case 1:
                    encryptedMessage = firstHalf + "Æ" + secondHalf;
                    break;
                case 2:
                    encryptedMessage = firstHalf + "£" + secondHalf;
                    break;
                case 3:
                    encryptedMessage = firstHalf + "Σ" + secondHalf;
                    break;
                // accented C
                case 4:
                    encryptedMessage = firstHalf + "Ç" + secondHalf;
                    break;
                case 5:
                    encryptedMessage = firstHalf + "β" + secondHalf;
                    break;
            }
        }
        return encryptedMessage;
    }

    public static String decryptEmojiReplace(String encryptedMessage) {
        String decryptedMessage = encryptedMessage;
        for (int i = 0; i < encryptedMessage.length(); i++) {
            char currentCharacter = encryptedMessage.charAt(i);

            String firstHalf = decryptedMessage.substring(0, i);
            String secondHalf = decryptedMessage.substring(i + 1);

            switch (currentCharacter) {
                // AE
                case 'Æ':
                    decryptedMessage = firstHalf + "a" + secondHalf;
                    break;
                case '£':
                    decryptedMessage = firstHalf + "e" + secondHalf;
                    break;
                case 'Σ':
                    decryptedMessage = firstHalf + "i" + secondHalf;
                    break;
                // alpha
                case 'Ç':
                    decryptedMessage = firstHalf + "o" + secondHalf;
                    break;
                case 'β':
                    decryptedMessage = firstHalf + "u" + secondHalf;
                    break;
            }
        }
        return decryptedMessage;
    }

    // doesnt include uppercase intentionally
    public static int isVowel(char character) {
        if (character == 'a') {
            return 1;
        }
        if (character == 'e') {
            return 2;
        }
        if (character == 'i') {
            return 3;
        }
        if (character == 'o') {
            return 4;
        }
        if (character == 'u') {
            return 5;
        }
        return -1;
    }

    public static String vigenere(String message, String key) {
        String newKey = key;
        if (key.length() < message.length()) {
            newKey = keyGen(message, key);
        }
        newKey = newKey.toUpperCase();
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
        String newKey = key;
        if (key.length() < encryptedMessage.length()) {
            newKey = keyGen(encryptedMessage, newKey);
        }
        newKey = newKey.toUpperCase();
        for (int i = 0; i < encryptedMessage.length(); i++) {
            int currentKeyChar = newKey.charAt(i);
            int currentEncryptedMessageChar = encryptedMessage.charAt(i);
            boolean isLowerCase = false;

            if (isSpecialCharacter((char) currentEncryptedMessageChar)) {
                decryptedString += encryptedMessage.charAt(i);
                continue;
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
            if (isSpecialCharacter(key.charAt(i % key.length()))) {
                newKey += 'S'; // random value
                continue;
            }
            newKey += key.charAt(i % key.length());
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
