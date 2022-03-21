package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class ProductEntity {

    @Id
    public String id;
    public String name;
    public int quantity;
    public int weight;
    public String category;

    public ProductEntity(){}

    public ProductEntity(String name, int quantity, int weight, String category) {
        this.name = name;
        this.quantity = quantity;
        this.weight = weight;
        this.category = category;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", weight=" + weight +
                ", category='" + category + '\'' +
                '}';
    }
}
