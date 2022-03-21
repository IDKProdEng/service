package ro.unibuc.hello.dto;

import ro.unibuc.hello.data.ProductEntity;

public class ProductDto {
    public String id;

    public String name;
    public int quantity;
    public int weight;
    public String category;

    public ProductDto(ProductEntity entity){
        this.id = entity.id;
        this.name = entity.name;
        this.quantity = entity.quantity;
        this.weight = entity.weight;
        this.category = entity.category;

    }
}
