import java.util.*;

public class EnigmaMachine {
    private final int DAY_OF_MONTH;
    private final Rotor[] disks = new Rotor[3];
    private final ArrayList<Character> plugboardSelect = new ArrayList<>();
    private final ArrayList<Character> inputMapping = new ArrayList<>();

    public EnigmaMachine(int dayOfMonth) {
        DAY_OF_MONTH = dayOfMonth;
        for (Character ch : Constants.INPUT_CHARS) {
            inputMapping.add(ch);
        }
        // Set up the plugboard
        for (Character ch : Constants.PLUGBOARD_OPTIONS[DAY_OF_MONTH - 1]) {
            plugboardSelect.add(ch);
        }
    }

    /**
     * Encrypts the given message, rotates disks accordingly
     *
     * @param plaintext The plaintext message received from the console
     * @return The encrypted version of the plaintext message
     */
    public String encrypt(String plaintext) {
        setUpDisks(plaintext, true);
        ArrayDeque<Character> ciphertext = new ArrayDeque<>();
        for (Character c : plaintext.toUpperCase().toCharArray()) {
            if (c == ' ') {
                ciphertext.addLast(' ');
            } else {
                char output = mapThroughOutputAndReflector(c, true);
                mapThroughInputDisksAndPlugboard(false, output, ciphertext);
            }
        }
        return convertToResult(ciphertext);
    }

    /**
     * Decrypts the given message, starting from the corresponding disk rotations
     *
     * @param ciphertext The (encrypted) ciphertext message received from the console
     * @return The unencrypted version of the ciphertext message
     */
    public String decrypt(String ciphertext) {
        setUpDisks(ciphertext, false);
        ArrayDeque<Character> plaintext = new ArrayDeque<>();
        char[] ct = ciphertext.toUpperCase().toCharArray();
        int backRotationCounter = ciphertext.replaceAll(" ", "").length();
        for (int i = ciphertext.length() - 1; i >= 0; i--) {
            char c = ct[i];
            if (c == ' ') {
                plaintext.addFirst(' ');
            } else {
                char output = mapThroughOutputAndReflector(c, false);
                // Handle disk back-rotations
                disks[2].rotate(-1); // Right disk always rotates back one click
                if (backRotationCounter % 60 == 0) {
                    disks[1].rotate(-1);
                    if (backRotationCounter % 3600 == 0) {
                        disks[0].rotate(-1);
                    }
                }
                mapThroughInputDisksAndPlugboard(true, output, plaintext);
                backRotationCounter--;
            }
        }
        return convertToResult(plaintext);
    }

    public Rotor[] getDisks() {
        return disks;
    }

    /**
     * // Set up the three disks with correct mappings and starting rotations
     * @param inputText The text entered in the console
     * @param encrypting Whether the user is encrypting or decrypting
     */
    private void setUpDisks(String inputText, boolean encrypting) {
        for (int i = 0; i < 3; i++) {
            int rotation = encrypting ? Constants.DISK_ORDER_W_ROTATIONS[DAY_OF_MONTH - 1][3 + i] : (Constants.DISK_ORDER_W_ROTATIONS[DAY_OF_MONTH - 1][3 + i] +
                    (inputText.replaceAll(" ", "").length()
                            / (int) (Math.pow(60, 2 - i)))) % 60;
            disks[i] = new Rotor(Constants.OUTPUT_MAPPINGS[Constants.DISK_ORDER_W_ROTATIONS[DAY_OF_MONTH - 1][i] - 1], rotation);
        }
    }

    /**
     * Map the encrypting/decrypting character through all three inbound disks and the plugboard
     * @param prepend Whether to prepend or append characters to the ArrayDeque
     * @param output The character currently traveling through the Enigma Machine
     * @param resultText The queue containing output characters up to this point
     */
    private void mapThroughInputDisksAndPlugboard(boolean prepend, char output, ArrayDeque<Character> resultText) {
        // Input disks
        for (int diskNum = 0; diskNum < 3; diskNum++) {
            output = inputMapping.get((60 + disks[diskNum].getOutputMapping().indexOf(output) - disks[diskNum].getRotation()) % 60);
        }
        // Input plugboard
        char charToAdd = output;
        if (plugboardSelect.contains(output)) {
            int plugIndexIn = plugboardSelect.indexOf(output);
            charToAdd = plugIndexIn % 2 == 0 ? plugboardSelect.get(plugIndexIn + 1) : plugboardSelect.get(plugIndexIn - 1);
        }
        if (prepend) {
            resultText.addFirst(charToAdd);
        } else resultText.addLast(charToAdd);
    }

    /**
     * Map the encrypting/decrypting character through all three outbound disks and the reflector plate
     * @param output The character currently traveling through the Enigma Machine
     * @param encrypting Whether the user is encrypting or decrypting
     * @return The modified character from output
     */
    private char mapThroughOutputAndReflector(char output, boolean encrypting) {
        // Output plugboard
        if (plugboardSelect.contains(output)) {
            int plugIndexOut = plugboardSelect.indexOf(output);
            output = plugIndexOut % 2 == 0 ? plugboardSelect.get(plugIndexOut + 1) :
                    plugboardSelect.get(plugIndexOut - 1);
        }
        // Output disks
        boolean lastDiskCycled = true;
        for (int diskNum = 2; diskNum >= 0; diskNum--) {
            output = disks[diskNum].getOutputMapping().get((inputMapping.indexOf(output) + disks[diskNum].getRotation()) % 60);
            if (lastDiskCycled && encrypting) {
                lastDiskCycled = disks[diskNum].rotate(1);
            }
        }
        // Reflector plate
        return inputMapping.get(inputMapping.size() - 1 - inputMapping.indexOf(output));
    }

    /**
     * Convert the queue of characters to our result message
     * @param input The queue containing all processed characters
     * @return The string of the combined enqueued characters
     */
    private String convertToResult(ArrayDeque<Character> input) {
        StringBuilder sb = new StringBuilder();
        while (!input.isEmpty()) {
            sb.append(input.pollFirst());
        }
        return sb.toString();
    }
}
