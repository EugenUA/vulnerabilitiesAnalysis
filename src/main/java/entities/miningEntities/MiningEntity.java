package entities.miningEntities;

import entities.dbEntities.Description;
import entities.dbEntities.Product;
import entities.dbEntities.Vulnerability;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MiningEntity {

    private String short_description;
    private String long_description;

    /* Preprocessing */
    private List<String> preprocessed_short_description;
    private List<String> preprocessed_long_description;

    /* Vector Space Model (TF-IDF) */
    private double[] short_tfidfDocsVector;
    private double[] long_tfidfDocsVector;

    /* Clustering */
    private int clusterNum;

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

    public int getClusterNum() {
        return clusterNum;
    }

    public void setClusterNum(int clusterNum) {
        this.clusterNum = clusterNum;
    }

    public double[] getShort_tfidfDocsVector() {
        return short_tfidfDocsVector;
    }

    public void setShort_tfidfDocsVector(double[] short_tfidfDocsVector) {
        this.short_tfidfDocsVector = short_tfidfDocsVector;
    }

    public double[] getLong_tfidfDocsVector() {
        return long_tfidfDocsVector;
    }

    public void setLong_tfidfDocsVector(double[] long_tfidfDocsVector) {
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
                ", preprocessed_short_description=" + preprocessed_short_description +
                ", preprocessed_long_description=" + preprocessed_long_description +
                ", short_tfidfDocsVector=" + Arrays.toString(short_tfidfDocsVector) +
                ", long_tfidfDocsVector=" + Arrays.toString(long_tfidfDocsVector) +
                ", clusterNum=" + clusterNum +
                ", date='" + date + '\'' +
                ", source='" + source + '\'' +
                ", source_type='" + source_type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MiningEntity that = (MiningEntity) o;

        if (clusterNum != that.clusterNum) return false;
        if (!short_description.equals(that.short_description)) return false;
        if (!long_description.equals(that.long_description)) return false;
        if (!preprocessed_short_description.equals(that.preprocessed_short_description)) return false;
        if (!preprocessed_long_description.equals(that.preprocessed_long_description)) return false;
        if (!Arrays.equals(short_tfidfDocsVector, that.short_tfidfDocsVector)) return false;
        if (!Arrays.equals(long_tfidfDocsVector, that.long_tfidfDocsVector)) return false;
        if (!date.equals(that.date)) return false;
        if (!source.equals(that.source)) return false;
        return source_type.equals(that.source_type);
    }

    @Override
    public int hashCode() {
        int result = short_description.hashCode();
        result = 31 * result + long_description.hashCode();
        result = 31 * result + preprocessed_short_description.hashCode();
        result = 31 * result + preprocessed_long_description.hashCode();
        result = 31 * result + Arrays.hashCode(short_tfidfDocsVector);
        result = 31 * result + Arrays.hashCode(long_tfidfDocsVector);
        result = 31 * result + clusterNum;
        result = 31 * result + date.hashCode();
        result = 31 * result + source.hashCode();
        result = 31 * result + source_type.hashCode();
        return result;
    }
}
