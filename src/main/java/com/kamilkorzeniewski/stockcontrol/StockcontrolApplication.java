package com.kamilkorzeniewski.stockcontrol;

import com.kamilkorzeniewski.stockcontrol.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@ComponentScan(basePackages = "com.kamilkorzeniewski.stockcontrol")
public class StockcontrolApplication implements ApplicationRunner {

    @Autowired
    private ProductService productService;


    public static void main(String[] args) {
        SpringApplication.run(StockcontrolApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Map<Integer, String> fields = new HashMap<>();
        fields.put(0, "name");
        fields.put(1, "code");
        fields.put(2, "price");
        fields.put(4, "quantity");


        productService.loadProductsFromCsv("/Users/kamilkorzeniewski/csvfile.csv", fields, 1);

    }
}

