package fr.ucs.advancedrank.ranks;

public class Rank {
    private final int power;
    private final String name, display;

    public Rank(int power, String name, String display) {
        this.power = power;
        this.name = name;
        this.display = display;
    }

    public int getPower() {
        return power;
    }

    public String getName() {
        return name;
    }

    public String getDisplay() {
        return display;
    }
}
