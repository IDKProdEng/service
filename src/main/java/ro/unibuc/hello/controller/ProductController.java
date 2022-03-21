package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.ProductEntity;
import ro.unibuc.hello.data.ProductRepository;
import ro.unibuc.hello.dto.AddProductDto;
import ro.unibuc.hello.dto.ConsumeProductDto;
import ro.unibuc.hello.dto.ProductDto;
import ro.unibuc.hello.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/product")
    @ResponseBody
    public ProductDto getProduct(@RequestParam(name="name") String name){
        var entity = productRepository.findByName(name);
        if(entity == null) {
            throw new NotFoundException();
        }
        return new ProductDto(entity);
    }

    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ProductDto> getAllProducts(){
        var entities = productRepository.findAll();
        if(entities.size() == 0){
            throw new NotFoundException();
        }
        var returnedEntities = entities.stream();
        return returnedEntities.map(ProductDto::new).collect(Collectors.toList());
    }

    @PostMapping("/product/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(@RequestBody AddProductDto model){

        if(model.quantity <= 0){
            throw new NotFoundException();
        }
        ProductEntity product = new ProductEntity(model.name, model.quantity, model.weight, model.category);
        productRepository.save(product);
    }

    @PostMapping("/product/consume")
    @ResponseStatus(HttpStatus.OK)
    public void consumeProduct(@RequestBody ConsumeProductDto model){

        if (model == null){
            throw new NotFoundException();
        }
        if(model.quantity <= 0){
            throw new NotFoundException();
        }
        var product = productRepository.findByName(model.name);
        if (product == null){
            throw new NotFoundException();
        }

        product.quantity -= model.quantity;
        productRepository.save(product);
    }

}
