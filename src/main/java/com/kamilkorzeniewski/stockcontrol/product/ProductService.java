package com.kamilkorzeniewski.stockcontrol.product;

import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;


@Service
public class ProductService {


    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    List<Product> loadProductsFrom(Supplier<List<Product>> productSupplier) {
        return productSupplier.get().stream().map(this::removeEndDots).collect(Collectors.toList());
    }

    Map<Product, List<Product>> predicateProducts(List<Product> products) {
        Map<Product, List<Product>> productListMap = new HashMap<>();
        products.forEach(product -> {
            productListMap.computeIfAbsent(product, Lists::newArrayList);
            productListMap.put(product, findProductsNameContains(product));
        });
        return productListMap;
    }

    Product findProductById(Long id){
        return productRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    List<Product> findAllByPredicate(Predicate predicate) {
        return Lists.newArrayList(productRepository.findAll(predicate));
    }

    Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    void updateOrCreate(Product product, Long id) {
        productRepository.findById(id).ifPresentOrElse(p -> productRepository.save(p.productWith(product)),
                () -> productRepository.save(product));
    }

    Product removeProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product with id " + id + " not exist"));
        productRepository.delete(product);
        return product;
    }

    private List<Product> findProductsNameContains(Product product) {
        return Arrays.stream(product.name.split(" ")).map(nameElement -> {
            Predicate namePredicate = QProduct.product.name.containsIgnoreCase(nameElement);
            return productRepository.findAll(namePredicate);
        }).map(Lists::newArrayList).flatMap(Collection::stream).collect(Collectors.toList());

    }

    private Product removeEndDots(Product product) {
        if (product.name.endsWith(".")) {
            String name = product.name.substring(0, product.name.length() - 1);
            return product.productWith(name);
        }
        return product;
    }


}
