public class CaesarCipher {
    public static String encrypt(String plaintext, int shift) {
        StringBuilder encryptedText = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i++) {
            char currentChar = plaintext.charAt(i);
            if (Character.isLetter(currentChar)) {
                char base = Character.isUpperCase(currentChar) ? 'A' : 'a';
                encryptedText.append((char) ((currentChar - base + shift) % 26 + base));
            } else {
                encryptedText.append(currentChar);
            }
        }

        return encryptedText.toString();
    }

    public static void bruteForceAttack(String ciphertext) {
        System.out.println("Brute Force Attack Results:");
        for (int i = 1; i <= 25; i++) {
            String decryptedText = decrypt(ciphertext, i);
            System.out.println("Shift " + i + ": " + decryptedText);
        }
    }

    public static String decrypt(String ciphertext, int shift) {
        return encrypt(ciphertext, 26 - shift);
    }

    // Aufgabe 10 - implement a known plaintext attack
    public static int findKey(String plaintext, String ciphertext) {
        int key = 0;
        plaintext = plaintext.toUpperCase();
        ciphertext = ciphertext.toUpperCase();

        // Find the key by comparing characters in plaintext and ciphertext
        for (int i = 0; i < plaintext.length(); i++) {
            char plainChar = plaintext.charAt(i);
            char cipherChar = ciphertext.charAt(i);

            if (Character.isLetter(plainChar)) {
                int shift = (cipherChar - plainChar + 26) % 26;
                key = shift;
                break; 
            }
        }
        return key;
    }

    public static void main(String[] args) {
        //Beispiel 1
        System.out.println("Beispiel 1: ");
        String plaintext = "Hello World";
        int shift = 5;
        String ciphertext = encrypt(plaintext, shift);
        System.out.println("Plaintext: " + plaintext);
        System.out.println("Encrypted: " + ciphertext);

        
        bruteForceAttack(ciphertext);

        int key = findKey(plaintext, ciphertext);
        System.out.println("Key (Shift): " + key);

        System.out.println();
        System.out.println("Beispiel 2: ");

        //Beispiel 2
        String plaintext2 = " the shift value is 7";
        int shift2 = 7;
        String cipher2 = encrypt(plaintext2, shift2 );
        System.out.println("Plaintext2: " + plaintext2);
        System.out.println("Encrypted2: " + cipher2);

        bruteForceAttack(cipher2);

        int key2 = findKey(plaintext2, cipher2);
        System.out.println("Key (Shift)2: " + key2);

    }
}
