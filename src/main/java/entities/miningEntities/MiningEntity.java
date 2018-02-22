package entities.miningEntities;

import entities.dbEntities.Description;
import entities.dbEntities.Product;
import entities.dbEntities.Vulnerability;

public class MiningEntity {

    private Vulnerability vulnerability;
    private Description description;
    private Product product;

    public MiningEntity(){}

    public MiningEntity(Vulnerability vulnerability, Description description, Product product) {
        this.vulnerability = vulnerability;
        this.description = description;
        this.product = product;
    }

    public Vulnerability getVulnerability() {
        return vulnerability;
    }

    public void setVulnerability(Vulnerability vulnerability) {
        this.vulnerability = vulnerability;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MiningEntity that = (MiningEntity) o;

        if (!vulnerability.equals(that.vulnerability)) return false;
        if (!description.equals(that.description)) return false;
        return product.equals(that.product);
    }

    @Override
    public int hashCode() {
        int result = vulnerability.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + product.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "MiningEntity{" +
                "vulnerability=" + vulnerability +
                ", description=" + description +
                ", product=" + product +
                '}';
    }
}
