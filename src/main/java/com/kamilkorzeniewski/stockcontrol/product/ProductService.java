package com.kamilkorzeniewski.stockcontrol.product;

import com.google.common.collect.Lists;
import com.kamilkorzeniewski.stockcontrol.invoice.InvoiceLoader;
import com.kamilkorzeniewski.stockcontrol.invoice.InvoiceLoaderParameter;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ProductService {

    @Autowired
    @Qualifier("invoiceProductCsvLoader")
    private InvoiceLoader invoiceCsvLoader;

    @Autowired
    private ProductRepository productRepository;


    public void loadProductsFromCsv(String path, Map<Integer, String> fieldsName, int rowOffset) {
        InvoiceLoaderParameter csvIlp = new InvoiceLoaderParameter();
        csvIlp.put("path", path);
        csvIlp.put("field_names", fieldsName);
        csvIlp.put("row_offset", rowOffset);
        List<Product> products = (List<Product>) invoiceCsvLoader.load(csvIlp);
        productRepository.saveAll(products);
    }

    public List<QProduct> findAllByPredicate(Predicate predicate){
         return Lists.newArrayList(productRepository.findAll(predicate));
    }

    public void saveProduct(Product product){
        productRepository.save(product);
    }

}
