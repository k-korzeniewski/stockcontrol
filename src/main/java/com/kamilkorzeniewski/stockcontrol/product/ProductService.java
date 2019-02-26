package com.kamilkorzeniewski.stockcontrol.product;

import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public List<Product> loadProductsFrom(Supplier<List<Product>> productSupplier) {
        List<Product> products = productSupplier.get().stream().map(this::removeEndDots).collect(Collectors.toList());
        return products;
    }

    Map<Product, List<Product>> predicateProductsByName(List<Product> products) {
        Map<Product, List<Product>> productListMap = new HashMap<>();
        products.forEach(product -> {
            productListMap.computeIfAbsent(product, Lists::newArrayList);
            productListMap.put(product, findProductsNameContains(product));
        });
        return productListMap;
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


    private List<Product> findProductsNameContains(Product product) {
        return Arrays.stream(product.name.split(" ")).map(nameElement -> {
            Predicate namePredicate = QProduct.product.name.containsIgnoreCase(nameElement);
            return productRepository.findAll(namePredicate);
        }).map(Lists::newArrayList).flatMap(list -> list.stream()).collect(Collectors.toList());

    }

    private Product removeEndDots(Product product) {
        if (product.name.endsWith(".")) {
            String name = product.name.substring(0, product.name.length() - 1);
            return product.productWith(name);
        }
        return product;
    }

}
