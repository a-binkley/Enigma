import java.util.*;

public class EnigmaMachine {
	private int dayOfMonth;
	private Rotor[] disks = new Rotor[3];
	private ArrayList<Character> plugboardSelect = new ArrayList<>();
	private ArrayList<Character> inputMapping = new ArrayList<>();
	
	private char[] inputChars = {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', ',', '<', '.', '>', 
			'/', '?', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '=', '+', ';', ':', '\'', '"'};
	private char[][] outputMappings = {
			{'R', '8', 'M', '5', 'E', '-', '6', 'V', '/', '=', 'S', '+', ',', ':', 'B', 'Q', '3', ';', '1', '"', 
			'9', 'C', '^', 'F', 'W', '\'', '%', 'H', 'Z', 'I', 'Y', 'L', 'O', 'X', 'T', '!', '&', ')', '0', '(', 
			'?', 'N', 'P', '2', '$', '<', 'D', 'K', 'J', '_', '#', '4', 'U', 'G', '*', '.', 'A', '7', '@', '>'}, 
			{'C', 'O', '7', '&', 'Q', 'S', 'Y', 'T', '%', 'K', '!', '>', ':', '_', '$', 'M', '\'', 'I', '-', '3', 
			'6', '<', 'B', 'H', 'P', 'D', 'L', 'U', ')', '2', 'N', '4', '/', '"', '+', '0', '@', 'G', 'E', 'J', 
			'?', '.', 'R', ',', '=', ';', 'W', '5', '9', '*', 'A', '#', '8', '^', '(', 'V', 'F', 'Z', '1', 'X'},
			{'I', '=', 'U', '@', '(', 'Y', '/', '8', '0', 'M', 'R', 'S', 'O', 'K', '_', '4', '1', '5', 'X', 'P', 
			'2', 'N', 'F', '.', ',', '-', '?', '!', '6', 'D', '7', '+', '#', 'W', '9', 'V', 'C', 'Q', '^', 'J', 
			'$', 'G', 'E', '>', '%', ';', 'L', 'Z', ':', '<', '\'', 'B', 'T', ')', 'H', '3', '*', '&', '"', 'A'},
			{'X', 'B', '2', '>', 'V', 'D', '!', '$', ')', '8', 'F', '-', 'S', '\'', ',', 'Y', 'T', '.', '0', '=', 
			'<', ':', 'I', '1', 'H', '9', '4', 'P', '#', 'G', ';', '5', 'L', 'N', 'O', 'Z', 'U', 'J', '^', 'R', 
			'_', '3', '@', '%', '&', '+', '(', '6', 'W', 'E', 'C', '7', '/', 'Q', '"', 'M', '*', 'K', '?', 'A'},
			{'_', '#', 'T', 'O', '-', 'L', '!', 'H', 'X', 'I', '3', '1', '6', '@', 'S', '\'', 'M', 'Q', 'D', '?', 
			'"', '(', '<', '7', 'U', '2', 'N', 'P', '^', '%', '.', '0', ';', '*', '/', 'V', '$', 'G', 'W', '9', 
			'8', 'C', '+', 'A', 'J', ')', 'Y', 'R', ',', '>', 'Z', '&', 'E', '=', 'K', 'F', ':', '4', '5', 'B'}};
	private char[][] plugboardOptions = {
			{'D', 'P', 'B', 'M', 'N', 'Z', 'C', 'K', 'G', 'V', 'H', 'Q', 'A', 'F', 'U', 'Y', 'S', 'W', 'J', 'O'}, 
			{'B', 'N', 'H', 'U', 'E', 'G', 'P', 'Y', 'K', 'Q', 'C', 'P', 'O', 'S', 'J', 'W', 'A', 'I', 'V', 'Z'},  
			{'K', 'R', 'M', 'P', 'C', 'N', 'B', 'F', 'E', 'H', 'D', 'Z', 'I', 'W', 'A', 'V', 'G', 'J', 'L', 'O'},  
			{'A', 'C', 'B', 'L', 'O', 'Z', 'E', 'K', 'Q', 'W', 'G', 'P', 'S', 'U', 'D', 'H', 'J', 'M', 'T', 'X'},  
			{'M', 'V', 'C', 'L', 'G', 'K', 'O', 'Q', 'B', 'I', 'F', 'U', 'H', 'S', 'P', 'X', 'N', 'W', 'E', 'Y'},  
			{'D', 'Q', 'G', 'U', 'B', 'W', 'N', 'P', 'H', 'K', 'A', 'Z', 'C', 'I', 'P', 'O', 'J', 'X', 'V', 'Y'},  
			{'U', 'X', 'I', 'Z', 'H', 'N', 'B', 'K', 'G', 'Q', 'C', 'P', 'F', 'T', 'J', 'Y', 'M', 'W', 'A', 'R'},  
			{'F', 'I', 'N', 'Q', 'S', 'Y', 'C', 'U', 'B', 'Z', 'A', 'H', 'E', 'L', 'T', 'X', 'D', 'O', 'K', 'P'},  
			{'Q', 'Y', 'B', 'S', 'L', 'N', 'K', 'T', 'A', 'P', 'I', 'U', 'D', 'W', 'H', 'O', 'R', 'V', 'J', 'Z'},  
			{'L', 'R', 'I', 'K', 'M', 'S', 'Q', 'U', 'H', 'W', 'P', 'T', 'G', 'O', 'V', 'X', 'F', 'Z', 'E', 'N'},  
			{'K', 'N', 'U', 'Y', 'H', 'R', 'P', 'W', 'F', 'M', 'B', 'O', 'E', 'Z', 'Q', 'T', 'D', 'X', 'J', 'V'},  
			{'M', 'U', 'B', 'P', 'C', 'Y', 'R', 'Z', 'K', 'X', 'A', 'N', 'J', 'T', 'D', 'G', 'I', 'L', 'P', 'W'},  
			{'L', 'Y', 'A', 'G', 'K', 'M', 'B', 'R', 'I', 'Q', 'J', 'U', 'H', 'V', 'S', 'W', 'E', 'T', 'C', 'X'},  
			{'G', 'M', 'J', 'R', 'K', 'S', 'I', 'Y', 'H', 'Z', 'P', 'L', 'A', 'X', 'B', 'T', 'C', 'Q', 'N', 'V'},  
			{'D', 'S', 'H', 'Y', 'M', 'R', 'G', 'W', 'L', 'X', 'A', 'J', 'B', 'Q', 'C', 'O', 'I', 'P', 'N', 'T'},  
			{'H', 'M', 'J', 'O', 'D', 'I', 'N', 'R', 'B', 'Y', 'X', 'Z', 'G', 'S', 'P', 'U', 'F', 'Q', 'C', 'T'},  
			{'I', 'R', 'K', 'Z', 'L', 'S', 'E', 'M', 'O', 'V', 'G', 'Y', 'Q', 'X', 'A', 'F', 'J', 'P', 'B', 'U'},  
			{'E', 'J', 'O', 'Y', 'I', 'V', 'A', 'Q', 'K', 'W', 'F', 'X', 'M', 'T', 'P', 'S', 'L', 'U', 'B', 'D'},  
			{'O', 'X', 'P', 'R', 'F', 'H', 'W', 'Y', 'D', 'L', 'C', 'M', 'A', 'E', 'T', 'Z', 'J', 'S', 'G', 'I'},  
			{'D', 'F', 'M', 'O', 'Q', 'Z', 'A', 'U', 'R', 'Y', 'S', 'V', 'J', 'L', 'G', 'X', 'B', 'E', 'T', 'W'},  
			{'R', 'U', 'H', 'L', 'P', 'Y', 'O', 'S', 'G', 'Z', 'D', 'M', 'A', 'W', 'C', 'E', 'T', 'V', 'N', 'X'},  
			{'F', 'J', 'E', 'S', 'I', 'M', 'R', 'X', 'L', 'V', 'A', 'Y', 'O', 'U', 'B', 'G', 'X', 'Z', 'C', 'N'},  
			{'Q', 'V', 'F', 'R', 'A', 'K', 'E', 'O', 'D', 'H', 'C', 'J', 'M', 'Z', 'S', 'X', 'G', 'N', 'L', 'T'},  
			{'T', 'Y', 'A', 'S', 'O', 'W', 'K', 'V', 'J', 'M', 'D', 'R', 'H', 'X', 'G', 'L', 'C', 'Z', 'N', 'U'},  
			{'O', 'R', 'F', 'V', 'A', 'D', 'I', 'T', 'P', 'K', 'H', 'J', 'L', 'Z', 'N', 'S', 'E', 'Q', 'C', 'W'},  
			{'V', 'Z', 'A', 'L', 'R', 'T', 'K', 'O', 'C', 'G', 'E', 'I', 'B', 'J', 'D', 'U', 'F', 'S', 'H', 'P'},  
			{'D', 'Y', 'I', 'N', 'B', 'V', 'G', 'R', 'A', 'M', 'L', 'O', 'F', 'P', 'H', 'T', 'E', 'X', 'U', 'W'},  
			{'C', 'R', 'F', 'V', 'A', 'I', 'D', 'K', 'O', 'T', 'M', 'Q', 'E', 'U', 'B', 'X', 'L', 'P', 'G', 'J'},  
			{'D', 'J', 'A', 'T', 'C', 'V', 'I', 'O', 'E', 'R', 'Q', 'S', 'L', 'W', 'P', 'Z', 'F', 'N', 'B', 'H'},  
			{'I', 'S', 'E', 'V', 'M', 'X', 'R', 'W', 'D', 'T', 'U', 'Z', 'J', 'Q', 'A', 'O', 'C', 'H', 'N', 'Y'},  
			{'S', 'Z', 'G', 'T', 'D', 'V', 'K', 'U', 'F', 'O', 'M', 'Y', 'E', 'W', 'J', 'N', 'I', 'X', 'L', 'Q'}};
	private int[][] diskOrderWithRotations = {
		{2, 1, 3, 13, 8, 23}, {4, 5, 1, 4, 25, 1}, {5, 1, 2, 11, 23, 2}, {2, 4, 1, 5, 7, 15}, {5, 2, 4, 10, 2, 6}, 
		{3, 1, 5, 16, 21, 18}, {1, 4, 2, 7, 24, 11}, {4, 2, 5, 4, 17, 13}, {5, 1, 3, 23, 11, 3}, {3, 5, 4, 0, 8, 20}, 
		{2, 4, 3, 12, 4, 18}, {5, 2, 4, 23, 0, 9}, {1, 3, 2, 16, 24, 19}, {4, 1, 5, 14, 22, 25}, {2, 4, 1, 20, 9, 5}, 
		{5, 2, 3, 7, 15, 12}, {1, 4, 2, 0, 2, 6}, {4, 2, 5, 14, 10, 4}, {5, 3, 1, 12, 19, 2}, {3, 4, 5, 17, 9, 6}, 
		{1, 5, 2, 1, 25, 14}, {2, 4, 5, 22, 20, 0}, {4, 2, 1, 15, 3, 7}, {5, 1, 4, 12, 18, 24}, {4, 3, 1, 8, 2, 21}, 
		{1, 4, 5, 10, 17, 13}, {3, 1, 4, 22, 1, 24}, {2, 3, 5, 3, 20, 8}, {3, 2, 1, 18, 10, 5}, {4, 3, 2, 15, 13, 1}, 
		{1, 5, 3, 22, 11, 9}};
	
	public EnigmaMachine(int dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
		for (Character ch : inputChars) {
			inputMapping.add(ch);
		}
		// Set up the plugboard
		for (Character ch : plugboardOptions[dayOfMonth-1]) {
			plugboardSelect.add(ch);
		}
	}

	/**
	 * Encrypts the given message, rotates disks accordingly
	 * @param plaintext
	 * @return
	 */
	public String encrypt(String plaintext) {
		// Set up the three disks with correct mappings and starting rotations
		for (int i = 0; i < 3; i++) {
			disks[i] = new Rotor(outputMappings[diskOrderWithRotations[dayOfMonth-1][i]-1], 
					diskOrderWithRotations[dayOfMonth-1][3+i]);
			// System.out.println("Set up disk in position " + i + " with rotation " + disks[i].getRotation()); //TEMP
		}
		ArrayList<Character> ciphertext = new ArrayList<>();
		for (Character c : plaintext.toUpperCase().toCharArray()) {
			if (c == ' ') {
				ciphertext.add(' '); 
			} else {
				char output = c;
				// Plugboard (outbound)
				if (plugboardSelect.contains(c)) {
					int plugIndexOut = plugboardSelect.indexOf(c);
					output = plugIndexOut % 2 == 0 ? plugboardSelect.get(plugIndexOut + 1) : 
						plugboardSelect.get(plugIndexOut - 1);
				}
				// System.out.println("Plugboard (out) sending: " + output); //TEMP
				// Right disk (outbound)
				output = disks[2].getOutputMapping().get((inputMapping.indexOf(output) + disks[2].getRotation()) % 60);
				boolean rdCycled = disks[2].rotate(1);
				// System.out.println("Right disk (out) sending: " + output); //TEMP
				// Middle disk (outbound)
				output = disks[1].getOutputMapping().get((inputMapping.indexOf(output) + disks[1].getRotation()) % 60);
				boolean mdCycled = false;
				if (rdCycled) {
					mdCycled = disks[1].rotate(1);
				}
				// System.out.println("Middle disk (out) sending: " + output); //TEMP
				// Left disk (outbound)
				output = disks[0].getOutputMapping().get((inputMapping.indexOf(output) + disks[0].getRotation()) % 60);
				if (mdCycled) {
					disks[0].rotate(1);
				}
				// System.out.println("Left disk (out) sending: " + output); //TEMP
				// Reflector plate
				output = inputMapping.get(inputMapping.size() - 1 - inputMapping.indexOf(output));
				// System.out.println("Reflector plate sending: " + output); //TEMP
				// Left disk (inbound)
				output = inputMapping.get((60 + disks[0].getOutputMapping().indexOf(output) - disks[0].getRotation()) % 60);
				// System.out.println("Left disk (in) sending: " + output); //TEMP
				// Middle disk (inbound)
				output = inputMapping.get((60 + disks[1].getOutputMapping().indexOf(output) - disks[1].getRotation()) % 60);
				// System.out.println("Middle disk (in) sending: " + output); //TEMP
				// Right disk (inbound)
				output = inputMapping.get((60 + disks[2].getOutputMapping().indexOf(output) - disks[2].getRotation()) % 60);
				// System.out.println("Right disk (in) sending: " + output); //TEMP
				// Plugboard (inbound)
				if (plugboardSelect.contains(output)) {
					int plugIndexIn = plugboardSelect.indexOf(output);
					ciphertext.add(plugIndexIn % 2 == 0 ? plugboardSelect.get(plugIndexIn + 1) : 
						plugboardSelect.get(plugIndexIn - 1));
				} else ciphertext.add(output);
//				System.out.println("Disk current settings: " + disks[0].getRotation() + 
//						"-" + disks[1].getRotation() + 
//						"-" + disks[2].getRotation());
			}
		}
		// Convert to normal String
		String ct = "";
		for (Character out : ciphertext) {
			ct = ct.concat(out + "");
		}
		return ct;
	}
	
	/**
	 * Decrypts the given message, starting from the corresponding disk rotations
	 * @param ciphertext
	 * @return
	 */
	public String decrypt(String ciphertext) {
		// Set up the three disks with correct mappings and starting rotations
		for (int i = 0; i < 3; i++) {
			disks[i] = new Rotor(outputMappings[diskOrderWithRotations[dayOfMonth-1][i]-1], 
					(diskOrderWithRotations[dayOfMonth-1][3+i] + 
							(ciphertext.replaceAll(" ", "").length() 
							/ (int) (Math.pow(60, 2-i)))) % 60);
//			System.out.println("Set up disk in position " + i + 
//					" with rotation " + disks[i].getRotation());
		}
		ArrayList<Character> plaintext = new ArrayList<>(); 
		char[] ct = ciphertext.toUpperCase().toCharArray();
		int indexCounter = ciphertext.replaceAll(" ", "").length();
		for (int i = ciphertext.length() - 1; i >= 0; i--) {
			char c = ct[i];
			if (c == ' ') {
				plaintext.add(0, ' ');
			} else {
				char output = c;
				// Plugboard (outbound)
				if (plugboardSelect.contains(c)) {
					int plugIndexOut = plugboardSelect.indexOf(c);
					output = plugIndexOut % 2 == 0 ? plugboardSelect.get(plugIndexOut + 1) : 
						plugboardSelect.get(plugIndexOut - 1);
				}
				// System.out.println("Plugboard (out) sending: " + output); //TEMP
				// Right disk (outbound)
				output = disks[2].getOutputMapping().get((inputMapping.indexOf(output) + disks[2].getRotation()) % 60);
				// System.out.println("Right disk (out) sending: " + output); //TEMP
				// Middle disk (outbound)
				output = disks[1].getOutputMapping().get((inputMapping.indexOf(output) + disks[1].getRotation()) % 60);
				// System.out.println("Middle disk (out) sending: " + output); //TEMP
				// Left disk (outbound)
				output = disks[0].getOutputMapping().get((inputMapping.indexOf(output) + disks[0].getRotation()) % 60);
				// System.out.println("Left disk (out) sending: " + output); //TEMP
				// Reflector plate
				output = inputMapping.get(inputMapping.size() - 1 - inputMapping.indexOf(output));
				// System.out.println("Reflector plate sending: " + output); //TEMP
				// Handle disk back-rotations
				disks[2].rotate(-1); // Right disk always rotates back one click
				if (indexCounter % 60 == 0) {
					disks[1].rotate(-1);
					if (indexCounter % 3600 == 0) {
						disks[0].rotate(-1);
					}
				}
//				System.out.println("New disk rotations: " + disks[0].getRotation() + 
//						"-" + disks[1].getRotation() + 
//						"-" + disks[2].getRotation());
				// Left disk (inbound)
				output = inputMapping.get((60 + disks[0].getOutputMapping().indexOf(output) - disks[0].getRotation()) % 60);
				// System.out.println("Left disk (in) sending: " + output); //TEMP
				// Middle disk (inbound)
				output = inputMapping.get((60 + disks[1].getOutputMapping().indexOf(output) - disks[1].getRotation()) % 60);
				// System.out.println("Middle disk (in) sending: " + output); //TEMP
				// Right disk (inbound)
				output = inputMapping.get((60 + disks[2].getOutputMapping().indexOf(output) - disks[2].getRotation()) % 60);
				// System.out.println("Right disk (in) sending: " + output); //TEMP
				// Plugboard (inbound)
				if (plugboardSelect.contains(output)) {
					int plugIndexIn = plugboardSelect.indexOf(output);
					plaintext.add(0, plugIndexIn % 2 == 0 ? plugboardSelect.get(plugIndexIn + 1) : 
						plugboardSelect.get(plugIndexIn - 1));
				} else plaintext.add(0, output);
				indexCounter--;
//				System.out.println("Disk current settings: " + disks[0].getRotation() + 
//						"-" + disks[1].getRotation() + 
//						"-" + disks[2].getRotation());
				/*
				 * Disk rotation logic: disk rotates AFTER signal exits, on outbound direction only
				 * For decryption: Do all rotations once signal reaches Reflector plate?
				 */
			}
		}
		// Convert to normal String
		String pt = "";
		for (Character out : plaintext) {
			pt = pt.concat(out + "");
		}
		return pt;
	}

	public Rotor[] getDisks() {
		return disks;
	}
}
