package ro.unibuc.hello.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import ro.unibuc.hello.data.ProductEntity;
import ro.unibuc.hello.data.ProductRepository;
import ro.unibuc.hello.dto.AddProductDto;
import ro.unibuc.hello.dto.ConsumeProductDto;
import ro.unibuc.hello.exception.NotFoundException;

public class ProductControllerTestIT {
   @Autowired
   ProductController productController;

   @Autowired
    ProductRepository productRepository;

   @BeforeEach
    public void setUp(){
       var x = productRepository.findByName("None");
       if (x != null)
           productRepository.delete(x);

       var y = productRepository.findByName("Something");   //TO BE ADDED
       if (y != null)
           productRepository.delete(y);

       productRepository.save(new ProductEntity("Something", 2, 1, "Test"));
   }

   @Test
   @Order(1)
    public void getProduct_Throws(){
       try{
           productController.getProduct("Something");
           Assertions.fail();
       }
       catch (Exception e){
           Assertions.assertEquals(NotFoundException.class, e.getClass());
           Assertions.assertEquals("Not Found", e.getMessage());
       }
   }

   @Test
   @Order(2)
   public void addProduct(){
       var product = new AddProductDto("Nothing", 1, 1, "Test");

       productController.addProduct(product);

       Assertions.assertNotNull(productRepository.findByName("Nothing"));
   }

   @Test
   @Order(3)
   public void consumeProduct(){
       productController.consumeProduct(new ConsumeProductDto("Something", 1));

       Assertions.assertEquals(1, productRepository.findByName("Something").quantity);
   }

   @Test
   @Order(4)
   public void consumeProduct_Throws(){
       try{
           productController.consumeProduct(new ConsumeProductDto("asd", 1));
           Assertions.fail();
       }
       catch (Exception e){
           Assertions.assertEquals(NotFoundException.class, e.getClass());
           Assertions.assertEquals("Not Found", e.getMessage());
       }
   }

   @Test
   @Order(5)
   public void addProduct_Throws(){
       var product = new AddProductDto("title", -1, 1, "Test");

       try {
           productController.addProduct(product);
           Assertions.fail();
       }
       catch (Exception e){
           Assertions.assertEquals(NotFoundException.class, e.getClass());
           Assertions.assertEquals("Not Found", e.getMessage());
       }
   }
}
