import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class EnigmaTester {
	
	@Test
	void testDiskSetupShort() {
		EnigmaMachine testEn = new EnigmaMachine(1);
		EnigmaMachine testDe = new EnigmaMachine(1);
		testEn.encrypt("a a");
		testDe.decrypt("1 2");
		assertEquals(13, testEn.getDisks()[0].getRotation());
		assertEquals(8, testEn.getDisks()[1].getRotation());
		assertEquals(25, testEn.getDisks()[2].getRotation());
		assertEquals(13, testDe.getDisks()[0].getRotation());
		assertEquals(8, testDe.getDisks()[1].getRotation());
		assertEquals(23, testDe.getDisks()[2].getRotation());
	}
	
	@Test
	void testDiskSetupMedium() {
		EnigmaMachine testEn = new EnigmaMachine(2);
		EnigmaMachine testDe = new EnigmaMachine(2);
		testEn.encrypt("abcdefghijklmnopqrstuvwxyz"
				+ "abcdefghijklmnopqrstuvwxyzaaaabbbbcccc"); // String of length 64
		testDe.decrypt("abcdefghijklmnopqrstuvwxyz"
				+ "abcdefghijklmnopqrstuvwxyzaaaabbbbcccc");
		assertEquals(4, testEn.getDisks()[0].getRotation());
		assertEquals(26, testEn.getDisks()[1].getRotation());
		assertEquals(5, testEn.getDisks()[2].getRotation());
		assertEquals(4, testDe.getDisks()[0].getRotation());
		assertEquals(25, testDe.getDisks()[1].getRotation());
		assertEquals(1, testDe.getDisks()[2].getRotation());
	}
	
	@Test
	void testDiskSetupLong() {
		EnigmaMachine testEn = new EnigmaMachine(31);
		EnigmaMachine testDe = new EnigmaMachine(31);
		String testStr = ""; // String of length 3602
		for (int i = 0; i < 3602; i++) testStr += "a";
		testEn.encrypt(testStr);
		testDe.decrypt(testStr);
		assertEquals(23, testEn.getDisks()[0].getRotation());
		assertEquals(11, testEn.getDisks()[1].getRotation());
		assertEquals(11, testEn.getDisks()[2].getRotation());
		assertEquals(22, testDe.getDisks()[0].getRotation());
		assertEquals(11, testDe.getDisks()[1].getRotation());
		assertEquals(9, testDe.getDisks()[2].getRotation());
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
