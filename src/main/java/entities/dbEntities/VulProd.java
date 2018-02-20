package entities.dbEntities;

/**
 * Created by Eugen on 13.09.2017.
 */
public class VulProd {

    private int vulnerability_id;
    private int product_id;

    public VulProd(){}

    public VulProd(int vulnerability_id, int product_id) {
        this.vulnerability_id = vulnerability_id;
        this.product_id = product_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getVulnerability_id() {
        return vulnerability_id;
    }

    public void setVulnerability_id(int vulnerability_id) {
        this.vulnerability_id = vulnerability_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VulProd vulProd = (VulProd) o;

        if (vulnerability_id != vulProd.vulnerability_id) return false;
        return product_id == vulProd.product_id;

    }

    @Override
    public int hashCode() {
        int result = vulnerability_id;
        result = 31 * result + product_id;
        return result;
    }

    @Override
    public String toString() {
        return "VulProd{" +
                "vulnerability_id=" + vulnerability_id +
                ", product_id=" + product_id +
                '}';
    }
}