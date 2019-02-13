package com.kamilkorzeniewski.stockcontrol.product;

import com.google.common.collect.Lists;
import com.kamilkorzeniewski.stockcontrol.invoice.InvoiceLoader;
import com.kamilkorzeniewski.stockcontrol.invoice.InvoiceLoaderParameter;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class ProductService {

    @Autowired
    @Qualifier("csvProductInvoiceLoader")
    private InvoiceLoader<Product> invoiceCsvLoader;

    @Autowired
    private ProductRepository productRepository;


    public void loadProductsFromCsv(String path, Map<Integer, String> fieldsName, int rowOffset) {
        InvoiceLoaderParameter csvIlp = new InvoiceLoaderParameter();
        csvIlp.put("path", path);
        csvIlp.put("field_names", fieldsName);
        csvIlp.put("row_offset", rowOffset);
        List<Product> products = invoiceCsvLoader.load(csvIlp);
        productRepository.saveAll(preProcess(Stream.of(products)));
    }

    List<QProduct> findAllByPredicate(Predicate predicate) {
        return Lists.newArrayList(productRepository.findAll(predicate));
    }

    void saveProduct(Product product) {
        productRepository.save(product);
    }

    void updateOrCreate(Product product, Long id) {
        Product p = productRepository.findById(id).orElseGet(Product::new);
        productRepository.save(p.productWith(product));

    }

    // Removing dots from end of names
    private List<Product> preProcess(Stream<List<Product>> productsStream) {
        return productsStream.flatMap(Collection::stream).map(p -> {
            if (p.name.endsWith(".")) {
                String newName = p.name.substring(0, p.name.length() - 1);
                return p.productWith(newName);
            }
            return p;
        }).collect(Collectors.toList());
    }


}
