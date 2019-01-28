package com.kamilkorzeniewski.stockcontrol;

import com.kamilkorzeniewski.stockcontrol.invoice.InvoiceProductCsvLoader;
import com.kamilkorzeniewski.stockcontrol.invoice.InvoiceLoaderParameter;
import com.kamilkorzeniewski.stockcontrol.product.Product;
import com.kamilkorzeniewski.stockcontrol.product.ProductRepository;
import com.kamilkorzeniewski.stockcontrol.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class StockcontrolApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(StockcontrolApplication.class, args);
    }

    @Autowired
    InvoiceProductCsvLoader invoiceProductCsvLoader;

    @Autowired
    private ProductService productService;
    @Override
    public void run(ApplicationArguments args) throws Exception {

        Map<Integer,String> fields = new HashMap<>();
        fields.put(0,"name");
        fields.put(1,"code");
        fields.put(2,"price");
        fields.put(4,"quantity");


        productService.loadProductsFromCsv("/Users/kamilkorzeniewski/csvfile.csv",fields,1);


    }
}

