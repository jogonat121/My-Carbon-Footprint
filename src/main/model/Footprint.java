package model;

public class Footprint {
    private double footprint;
    private final String name;

    public Footprint(String category) {
        this.footprint = 0;
        name = category;
    }

    public double getFootprint() {
        return footprint;
    }

    public String getName() {
        return name;
    }
}
