package ro.unibuc.hello.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.data.ProductEntity;
import ro.unibuc.hello.data.ProductRepository;
import ro.unibuc.hello.dto.AddProductDto;
import ro.unibuc.hello.dto.ConsumeProductDto;
import ro.unibuc.hello.exception.NotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductControllerTest{

    @Mock
    ProductRepository mockRepository;

    @InjectMocks
    ProductController productController = new ProductController();

    @Test
    public void getProduct_Throws(){
        when(mockRepository.findByName(anyString())).thenReturn(null);

        try{
            productController.getProduct("Test");
            Assertions.fail();
        }
        catch (Exception e){
            Assertions.assertEquals(NotFoundException.class, e.getClass());
            Assertions.assertEquals("Not Found", e.getMessage());
        }
    }

    @Test
    public void getProduct_Returns(){
        when(mockRepository.findByName("Test")).thenReturn(new ProductEntity("Test", 1, 1, "Test"));

        var res = productController.getProduct("Test");

        Assertions.assertEquals("Test", res.name);
        Assertions.assertEquals(1, res.quantity);
        Assertions.assertEquals(1, res.weight);
        Assertions.assertEquals("Test", res.category);
    }

    @Test
    public void getAllProducts_Returns(){
        when(mockRepository.findAll()).thenReturn(Arrays.asList(new ProductEntity("Test1", 1, 2, "Test"),
                new ProductEntity("Test2", 2, 3, "Test"),
                new ProductEntity("Test3", 3, 4, "Test")));

        var res = productController.getAllProducts();

        Assertions.assertEquals("Test1", res.get(0).name);
        Assertions.assertEquals("Test2", res.get(1).name);
        Assertions.assertEquals("Test3", res.get(2).name);
    }

    @Test
    public void getAllProducts_ThrowsNoProducts(){
        when(mockRepository.findAll()).thenReturn(List.of());

        try {
            var res = productController.getAllProducts();
            Assertions.fail();
        }
        catch (Exception e){
            Assertions.assertEquals(NotFoundException.class, e.getClass());
            Assertions.assertEquals("Not Found", e.getMessage());
        }
    }

    @Test
    public void addProduct() {
        var product = new AddProductDto("Test", 1, 1, "Test");
        productController.addProduct(product);

        verify(mockRepository, times(1)).save(any());
    }

    @Test
    public void addProduct_Throws() {
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

    @Test
    public void consumeProduct(){
        var product = new ProductEntity("Test", 2, 3, "Test");
        when(mockRepository.findByName(anyString())).thenReturn(product);
        productController.consumeProduct(new ConsumeProductDto("Test", 1));
        verify(mockRepository, times(1)).save(any());
        Assertions.assertEquals(1, product.quantity);
    }

    @Test
    public void consumeProduct_MissingBody(){
        try{
            productController.consumeProduct(null);
            Assertions.fail();
        }
        catch (Exception e){
            Assertions.assertEquals(NotFoundException.class, e.getClass());
            Assertions.assertEquals("Not Found", e.getMessage());
        }
    }

    @Test
    public void consumeProduct_Negative(){
        try{
            productController.consumeProduct(new ConsumeProductDto("Test", -1));
            Assertions.fail();
        }
        catch (Exception e){
            Assertions.assertEquals(NotFoundException.class, e.getClass());
            Assertions.assertEquals("Not Found", e.getMessage());
        }
    }

    @Test
    public void consumeProduct_NotFound(){
        try{
            productController.consumeProduct(new ConsumeProductDto("asd", 1));
            Assertions.fail();
        }
        catch (Exception e){
            Assertions.assertEquals(NotFoundException.class, e.getClass());
            Assertions.assertEquals("Not Found", e.getMessage());
        }
    }
}
