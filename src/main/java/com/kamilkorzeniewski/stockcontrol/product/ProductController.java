package com.kamilkorzeniewski.stockcontrol.product;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product")
    public List<QProduct> getProductsQueryDsl(@QuerydslPredicate(root = Product.class) Predicate productPredicate){
        return productService.findAllByPredicate(productPredicate);
    }
}
