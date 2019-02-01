package com.kamilkorzeniewski.stockcontrol.product;

import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    SmartValidator smartValidator;

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<QProduct> getProductsQueryDsl(@QuerydslPredicate(root = Product.class) Predicate productPredicate) {
        return productService.findAllByPredicate(productPredicate);
    }

    @PutMapping()
    public void putProducts(@RequestBody @Valid Product product) {
        productService.saveProduct(product);
    }
}
