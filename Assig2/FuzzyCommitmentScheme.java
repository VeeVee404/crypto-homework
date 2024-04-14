import java.util.Random;

public class FuzzyCommitmentScheme {
    private String key;

    public FuzzyCommitmentScheme(int biometricTemplateLength) {
        this.key = generateKey(biometricTemplateLength);
    }

    // generate a random binary key of given length
    private String generateKey(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(2));
        }
        return sb.toString();
    }

    // generate a random binary biometric template
    public String generateBiometricTemplate() {
        return generateKey(key.length());
    }

    // generate encrypted hash
    public String generateHash(String biometricTemplate) {
        StringBuilder hash = new StringBuilder();
        for (int i = 0; i < biometricTemplate.length(); i++) {
            hash.append(biometricTemplate.charAt(i) ^ key.charAt(i));
        }
        return hash.toString();
    }

    // compares a received Hash with the Template, if the difference is within the tolerance, it returns true
    public boolean authenticate(String receivedHash, String biometricTemplate, int tolerance) {
        int distance = hammingDistance(receivedHash, generateHash(biometricTemplate));
        return distance <= tolerance;
    }

    // calculate Hamming distance between two binary strings
    private int hammingDistance(String s1, String s2) {
        int distance = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                distance++;
            }
        }
        return distance;
    }

    public static void main(String[] args) {
        int biometricTemplateLength = 128;
        FuzzyCommitmentScheme fcs = new FuzzyCommitmentScheme(biometricTemplateLength);
        String biometricTemplate = fcs.generateBiometricTemplate();

        // simulate biometric variance in a biometric template 
        int tolerance = 10;
        StringBuilder noisyTemplateBuilder = new StringBuilder(biometricTemplate);
        Random random = new Random();
        for (int i = 0; i < biometricTemplate.length(); i++) {
            if (random.nextDouble() < 0.1) { 
                char bit = (biometricTemplate.charAt(i) == '0') ? '1' : '0';
                noisyTemplateBuilder.setCharAt(i, bit);
            }
        }
        String noisyBiometricTemplate = noisyTemplateBuilder.toString();

        String receivedHash = fcs.generateHash(noisyBiometricTemplate);
        boolean authenticated = fcs.authenticate(receivedHash, noisyBiometricTemplate, tolerance);

        System.out.println("Biometric template: " + biometricTemplate);
        System.out.println("Noisy biometric template: " + noisyBiometricTemplate);
        System.out.println("Authenticated: " + authenticated);
    }
}
