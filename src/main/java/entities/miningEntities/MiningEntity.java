package entities.miningEntities;

import entities.dbEntities.Description;
import entities.dbEntities.Product;
import entities.dbEntities.Vulnerability;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MiningEntity {

    private String short_description;
    private String long_description;

    /* Preprocessing */
    private List<String> preprocessed_short_description;
    private List<String> preprocessed_long_description;

    /* Vector Space Model (TF-IDF) */
    private List<double[]> short_tfidfDocsVector;
    private List<double[]> long_tfidfDocsVector;

    private String date;
    private String source;
    private String source_type;

    public MiningEntity(){

    }

    public MiningEntity(String short_description, String long_description, List<String> preprocessed_short_description,
                        List<String> preprocessed_long_description, String date, String source, String source_type) {
        this.short_description = short_description;
        this.long_description = long_description;
        this.preprocessed_short_description = preprocessed_short_description;
        this.preprocessed_long_description = preprocessed_long_description;
        this.date = date;
        this.source = source;
        this.source_type = source_type;
    }

    public List<double[]> getShort_tfidfDocsVector() {
        return short_tfidfDocsVector;
    }

    public void setShort_tfidfDocsVector(List<double[]> short_tfidfDocsVector) {
        this.short_tfidfDocsVector = short_tfidfDocsVector;
    }

    public List<double[]> getLong_tfidfDocsVector() {
        return long_tfidfDocsVector;
    }

    public void setLong_tfidfDocsVector(List<double[]> long_tfidfDocsVector) {
        this.long_tfidfDocsVector = long_tfidfDocsVector;
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

    public List<String> getPreprocessed_short_description() {
        return preprocessed_short_description;
    }

    public void setPreprocessed_short_description(List<String> preprocessed_short_description) {
        this.preprocessed_short_description = preprocessed_short_description;
    }

    public List<String> getPreprocessed_long_description() {
        return preprocessed_long_description;
    }

    public void setPreprocessed_long_description(List<String> preprocessed_long_description) {
        this.preprocessed_long_description = preprocessed_long_description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource_type() {
        return source_type;
    }

    public void setSource_type(String source_type) {
        this.source_type = source_type;
    }

    @Override
    public String toString() {
        return "MiningEntity{" +
                "short_description='" + short_description + '\'' +
                ", long_description='" + long_description + '\'' +
                ", preprocessed_short_description='" + preprocessed_short_description + '\'' +
                ", preprocessed_long_description='" + preprocessed_long_description + '\'' +
                ", date='" + date + '\'' +
                ", source='" + source + '\'' +
                ", source_type='" + source_type + '\'' +
                '}';
    }
}
