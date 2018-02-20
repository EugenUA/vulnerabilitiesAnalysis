package entities.dbEntities;

public class Description {

    private int id;
    private int vulnerability_id;
    private String short_description;
    private String long_description;
    private String preprocessed_short_description;
    private String preprocessed_long_description;

    public Description(){

    }

    public Description(int id, int vulnerability_id, String short_description, String long_description,
                       String preprocessed_short_description, String preprocessed_long_description) {
        this.id = id;
        this.vulnerability_id = vulnerability_id;
        this.short_description = short_description;
        this.long_description = long_description;
        this.preprocessed_short_description = preprocessed_short_description;
        this.preprocessed_long_description = preprocessed_long_description;
    }

    public Description(int id, int vulnerability_id, String short_description, String long_description) {
        this.id = id;
        this.vulnerability_id = vulnerability_id;
        this.short_description = short_description;
        this.long_description = long_description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVulnerability_id() {
        return vulnerability_id;
    }

    public void setVulnerability_id(int vulnerability_id) {
        this.vulnerability_id = vulnerability_id;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getLong_description() {
        return long_description;
    }

    public void setLong_description(String long_description) {
        this.long_description = long_description;
    }

    public String getPreprocessed_short_description() {
        return preprocessed_short_description;
    }

    public void setPreprocessed_short_description(String preprocessed_short_description) {
        this.preprocessed_short_description = preprocessed_short_description;
    }

    public String getPreprocessed_long_description() {
        return preprocessed_long_description;
    }

    public void setPreprocessed_long_description(String preprocessed_long_description) {
        this.preprocessed_long_description = preprocessed_long_description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Description that = (Description) o;

        if (id != that.id) return false;
        if (vulnerability_id != that.vulnerability_id) return false;
        if (!short_description.equals(that.short_description)) return false;
        if (!long_description.equals(that.long_description)) return false;
        if (preprocessed_short_description != null ? !preprocessed_short_description.equals(that.preprocessed_short_description) : that.preprocessed_short_description != null)
            return false;
        return preprocessed_long_description != null ? preprocessed_long_description.equals(that.preprocessed_long_description) : that.preprocessed_long_description == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + vulnerability_id;
        result = 31 * result + short_description.hashCode();
        result = 31 * result + long_description.hashCode();
        result = 31 * result + (preprocessed_short_description != null ? preprocessed_short_description.hashCode() : 0);
        result = 31 * result + (preprocessed_long_description != null ? preprocessed_long_description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Description{" +
                "id=" + id +
                ", vulnerability_id=" + vulnerability_id +
                ", short_description='" + short_description + '\'' +
                ", long_description='" + long_description + '\'' +
                ", preprocessed_short_description='" + preprocessed_short_description + '\'' +
                ", preprocessed_long_description='" + preprocessed_long_description + '\'' +
                '}';
    }
}
