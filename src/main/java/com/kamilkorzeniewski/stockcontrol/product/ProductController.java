package com.kamilkorzeniewski.stockcontrol.product;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {


    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProductsQueryDsl(@QuerydslPredicate(root = Product.class) Predicate productPredicate) {
        return productService.findAllByPredicate(productPredicate);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        Product resposne = productService.findProductById(id);
        return new ResponseEntity<>(resposne, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> postProduct(@RequestBody @Valid Product products) {
        Product response = productService.saveProduct(products);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public void putProduct(@RequestBody @Valid Product product, @PathVariable Long id) {
        productService.updateOrCreate(product, id);
    }

    /*
        Predicate existing products from list of products.
        Return Map where key is product from list and value is list of predicates, for specific product.
     */
    @PostMapping("/predicate")
    public Map<Product, List<Product>> predicateProducts(@Valid @RequestBody List<Product> products) {
        return productService.predicateProducts(products);
    }

    @DeleteMapping("/{id}")
    public void removeProduct(@PathVariable Long id) {
        productService.removeProduct(id);
    }

}
