package com.kamilkorzeniewski.stockcontrol.product;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {


    @Autowired
    private ProductService productService;

    @GetMapping
    public List<QProduct> getProductsQueryDsl(@QuerydslPredicate(root = Product.class) Predicate productPredicate) {
        return productService.findAllByPredicate(productPredicate);
    }

    @PostMapping
    public void postProduct(@RequestBody @Valid Product product) {
        productService.saveProduct(product);
    }

    @PutMapping("/{id}")
    public void putProduct(@RequestBody @Valid Product product,@PathVariable Long id){
        productService.updateOrCreate(product,id);
    }




}
