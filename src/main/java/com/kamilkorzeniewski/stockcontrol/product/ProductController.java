package com.kamilkorzeniewski.stockcontrol.product;

import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Predicate;

@RestController
public class ProductController {
    @GetMapping("/product")
    public List<Product> getProductsQueryDsl(@QuerydslPredicate(root = Product.class), Predicate productPredicate){
        return .findAll(userPredicate);
    }
}
