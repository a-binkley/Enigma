import java.util.ArrayList;

public class Rotor {
    private final ArrayList<Character> outputMapping = new ArrayList<>();
    private final int startPos;
    private int rotation;

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
     * Rotates the disk by one position
     * @return An indication of whether the disk has completed one full rotation from its starting position
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
