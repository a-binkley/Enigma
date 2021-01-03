import java.util.ArrayList;

public class Rotor {
	private ArrayList<Character> outputMapping = new ArrayList<>();
	private final int startPos;
	private int rotation = 0;
	
	public Rotor(char[] output, int rotation) {
		for (Character c : output) {
			outputMapping.add(c);
		}
		this.rotation = rotation;
		startPos = rotation;
	}
	
	public ArrayList<Character> getOutputMapping() {
		return outputMapping;
	}

	public int getRotation() {
		return rotation;
	}
	
	/**
	 * Rotates the disk by one position, returns a boolean value indicating whether
	 * or not the disk has completed one full rotation from its starting position
	 * @return
	 */
	public boolean rotate(int amount) {
		rotation = (60 + rotation + amount) % 60;
		return rotation == startPos;
	}

	@Override
	public String toString() {
		return outputMapping + ", rotated " + rotation;
	}
}
