package com.kamilkorzeniewski.stockcontrol.product;

import com.kamilkorzeniewski.stockcontrol.utils.LoggingUtils;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> findProductsQueryDsl(@QuerydslPredicate(root = Product.class) Predicate productPredicate) {
        
        return productService.findAllByPredicate(productPredicate);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable Long id, HttpServletRequest request){
        Product response = productService.findProductById(id);
        LOGGER.info(LoggingUtils.withIP(request,"fetch " + response.toString() ));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody @Valid Product products,HttpServletRequest request) {
        Product response = productService.saveProduct(products);
        LOGGER.info(LoggingUtils.withIP(request,"saved " + response.toString() ));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public void updateProduct(@RequestBody @Valid Product product, @PathVariable Long id,HttpServletRequest request) {
        productService.updateOrCreate(product, id);
        LOGGER.info(LoggingUtils.withIP(request,"update " + product.toString() ));
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
    public void removeProduct(@PathVariable Long id,HttpServletRequest request) {
        Product product = productService.removeProduct(id);
        LOGGER.info(LoggingUtils.withIP(request,"removed " + product.toString() ));
    }

}
