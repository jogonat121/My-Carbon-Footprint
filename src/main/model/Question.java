package model;

// Represents a question with query, footprint per unit and category
public class Question {
    private final String query;
    private final double unitFootprint;
    private final String category;

    // EFFECTS: constructs a question with given query, footprint per unit, category
    public Question(String query, double footprint, String category) {
        this.query = query;
        unitFootprint = footprint;
        this.category = category;
    }

    // EFFECTS: returns the query
    public String getQuery() {
        return query;
    }

    // EFFECTS: returns the footprint per unit
    public double getUnitFootprint() {
        return unitFootprint;
    }

    // EFFECTS: returns the category
    public String getCategory() {
        return category;
    }

    // EFFECTS: returns the footprint emitted by the given usage
    public double calculateFootprint(double usage) {
        double resFootprint = unitFootprint * usage;
        return (Math.round(resFootprint * 1000) / 1000.0);
    }
}
