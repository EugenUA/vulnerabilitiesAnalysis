package entities.dbEntities;

public class VulProd {

    private Integer vulnerability_id;
    private Integer product_id;

    public VulProd(){}

    public VulProd(Integer vulnerability_id, Integer product_id) {
        this.vulnerability_id = vulnerability_id;
        this.product_id = product_id;
    }

    public Integer getVulnerability_id() {
        return vulnerability_id;
    }

    public void setVulnerability_id(Integer vulnerability_id) {
        this.vulnerability_id = vulnerability_id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    @Override
    public String toString() {
        return "VulProd{" +
                "vulnerability_id=" + vulnerability_id +
                ", product_id=" + product_id +
                '}';
    }
}
