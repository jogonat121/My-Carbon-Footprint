package model;

public class Question {
    private final String query;
    private final double footprint;
    private final String category;

    public Question(String q, double val, String cat) {
        query = q;
        footprint = val;
        category = cat;
    }

    public String getQuery() {
        return query;
    }

    public double getFootprint() {
        return footprint;
    }

    public String getCategory() {
        return category;
    }
}
