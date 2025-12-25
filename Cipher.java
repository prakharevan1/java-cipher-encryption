public class Cipher {
    public static void main(String[] args) {
        System.out.println(atBash("HELLOWORLD"));
    }

    public static String atBash(String message) {
        String encryptedMessage = "";
        for (int i = 0; i < message.length(); i++) {
            // get char
            char currentCharacter = message.charAt(i);
            // convert the character to a range of 0-25 corresponding to A-Z
            int encryptedChar = (int) (currentCharacter - 65);

            // map it to its opposite letter on the alphabet spectrum (A -> Z, B -> Y, ...)
            encryptedChar = 25 - encryptedChar;

            // convert back to ASCII
            encryptedChar += 'A';

            // append to encrypted message
            encryptedMessage += (char) encryptedChar;
        }
        return encryptedMessage;
    }
}
