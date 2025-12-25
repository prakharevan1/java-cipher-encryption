public class Cipher {
    public static void main(String[] args) {
        String message = "You are an idiot. :)";
        System.out.println(message);
        System.out.println(atBash(message));
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
