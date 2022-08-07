package ru.yamost.main.models;

/**
 * Data class of Russia city
 */
public class City {
    private String name = "";
    private String region = "";
    private String district = "";
    private int population = 0;
    private String foundation = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getFoundation() {
        return foundation;
    }

    public void setFoundation(String foundation) {
        this.foundation = foundation;
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * String representation = "City{name='%s', region='%s', district='%s', population=%d, foundation='%s'}"
     * String.format(representation, name, region, district, population, foundation)
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        String representation = "City{name='%s', region='%s', district='%s', population=%d, foundation='%s'}";
        return String.format(representation, name, region, district, population, foundation);
    }
}

