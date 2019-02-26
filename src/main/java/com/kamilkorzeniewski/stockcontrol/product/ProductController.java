package com.kamilkorzeniewski.stockcontrol.product;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {


    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getProductsQueryDsl(@QuerydslPredicate(root = Product.class) Predicate productPredicate) {
        return productService.findAllByPredicate(productPredicate);
    }

    @PostMapping
    public Product postProduct(@RequestBody @Valid Product product) {
        return productService.saveProduct(product);
    }

    @PutMapping("/id/{id}")
    public void putProduct(@RequestBody @Valid Product product,@PathVariable Long id){
        productService.updateOrCreate(product,id);
    }

    @PostMapping("/predicate/name")
    public Map<Product,List<Product>> predicateProductsByName(@Valid @RequestBody List<Product> products){
        return productService.predicateProductsByName(products);
    }

}
