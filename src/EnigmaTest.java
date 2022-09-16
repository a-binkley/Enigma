import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class EnigmaTest {

    @Test
    void testDiskSetupShort() {
        EnigmaMachine testEn = new EnigmaMachine(1);
        EnigmaMachine testDe = new EnigmaMachine(1);
        testEn.encrypt("a a");
        testDe.decrypt("1 2");

        int[] encryptPositions = new int[] {13, 8, 25}, decryptPositions = new int[] {13, 8, 23};
		for (int i = 0; i < 3; i++) {
			assertEquals(encryptPositions[i], testEn.getDisks()[i].getRotation());
			assertEquals(decryptPositions[i], testDe.getDisks()[i].getRotation());
		}
    }

    @Test
    void testDiskSetupMedium() {
        EnigmaMachine testEn = new EnigmaMachine(2);
        EnigmaMachine testDe = new EnigmaMachine(2);
        testEn.encrypt("abcdefghijklmnopqrstuvwxyz"
                + "abcdefghijklmnopqrstuvwxyzaaaabbbbcccc"); // String of length 64
        testDe.decrypt("abcdefghijklmnopqrstuvwxyz"
                + "abcdefghijklmnopqrstuvwxyzaaaabbbbcccc");

        int[] encryptPositions = new int[] {4, 26, 5}, decryptPositions = new int[] {4, 25, 1};
        for (int i = 0; i < 3; i++) {
            assertEquals(encryptPositions[i], testEn.getDisks()[i].getRotation());
            assertEquals(decryptPositions[i], testDe.getDisks()[i].getRotation());
        }
    }

    @Test
    void testDiskSetupLong() {
        EnigmaMachine testEn = new EnigmaMachine(31);
        EnigmaMachine testDe = new EnigmaMachine(31);
        String testStr = "a".repeat(3602);  // String of length 3602
        testEn.encrypt(testStr);
        testDe.decrypt(testStr);

        int[] encryptPositions = new int[] {23, 11, 11}, decryptPositions = new int[] {22, 11, 9};
        for (int i = 0; i < 3; i++) {
            assertEquals(encryptPositions[i], testEn.getDisks()[i].getRotation());
            assertEquals(decryptPositions[i], testDe.getDisks()[i].getRotation());
        }
    }

    @Test
    void testEncryptionAdro() {
        EnigmaMachine testEn = new EnigmaMachine(30);
        assertEquals("QW.T", testEn.encrypt("Adro"));
    }

    @Test
    void testEncryptionRepeatWithSpaces() {
        EnigmaMachine testEn = new EnigmaMachine(5);
        assertEquals("( 3", testEn.encrypt("a a"));
    }

    @Test
    void testDecryptionAdro() {
        EnigmaMachine testDe = new EnigmaMachine(30);
        assertEquals("ADRO", testDe.decrypt("Qw.T"));
    }

    @Test
    void testDecryptionRepeatWithSpaces() {
        EnigmaMachine testDe = new EnigmaMachine(5);
        assertEquals("A A", testDe.decrypt("( 3"));
    }

    @Test
    void testRotorPrint() {
        char[] testMap = {'f', 'o', 'o', 'b', 'a', 'r'};
        Rotor testRotor = new Rotor(testMap, 42);
        assertEquals("[f, o, o, b, a, r], rotated 42", testRotor.toString());
    }
}
