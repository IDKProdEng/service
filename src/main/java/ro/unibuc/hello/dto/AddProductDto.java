package ro.unibuc.hello.dto;

public class AddProductDto {
    public String name;
    public int quantity;
    public int weight;
    public String category;
    public AddProductDto(String name, int quantity, int weight, String category){
        this.name = name;
        this.quantity = quantity;
        this.weight = weight;
        this.category = category;
    }
}
