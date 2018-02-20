package entities.dbEntities;

public class Product {

    private int id;
    private int name;
    private int version;

    public Product(){

    }

    public Product(int id, int name, int version) {
        this.id = id;
        this.name = name;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (id != product.id) return false;
        if (name != product.name) return false;
        return version == product.version;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name;
        result = 31 * result + version;
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name=" + name +
                ", version=" + version +
                '}';
    }
}
