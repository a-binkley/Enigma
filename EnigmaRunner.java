import java.util.NoSuchElementException;
import java.util.Scanner;

public class EnigmaRunner {
	public static EnigmaMachine em;
	public static Scanner s = new Scanner(System.in);

	/**
	 * Welcomes the user to the machine, asks for (and returns) input day of month
	 * @return
	 */
	public static int initialize() {
		System.out.println("Welcome to the Engima Encryption & Decryption Digital Replica!\nPlease enter the day of the month to proceed:");
		int dayOfMonth = 0;
		while (true) {
			try {
				dayOfMonth = Integer.parseInt(s.nextLine());
				if (dayOfMonth > 0 && dayOfMonth < 32) {
					break;
				} else {
					System.out.println("Invalid day number entered. Please try again:");
				}
			} catch(NumberFormatException e) {
				System.out.println("Invalid day number entered. Please try again:");
			}
		}
		return dayOfMonth;
	}
	
	public static void main(String[] args) {
		em = new EnigmaMachine(initialize());
		System.out.println("Would you like to Encrypt or Decrypt? (Simply type 'E' or 'D' to choose):");
		// Get the user's selection
		char usage;
		while (true) {
			try {
				usage = s.nextLine().toUpperCase().charAt(0);
				if (usage == 'E' || usage == 'D') {
					break;
				} else {
					System.out.println("Invalid choice. Please try again:");
				}
			} catch (NoSuchElementException e) {
				System.out.println("Invalid choice. Please try again:");
			}
		}
		if (usage == 'E') { // Encrypt
			System.out.println("Type the message you want to encrypt:");
			String plaintext = s.nextLine();
			String ciphertext = em.encrypt(plaintext);
			System.out.println("Your encoded message is:\n" + ciphertext + '\n');
		} else { // Decrypt
			System.out.println("Type the message you want to decrypt:");
			String ciphertext = s.nextLine();
			String plaintext = em.decrypt(ciphertext);
			System.out.println("Your decoded message is:\n" + plaintext + '\n');
		}
		s.close();
	}
}
