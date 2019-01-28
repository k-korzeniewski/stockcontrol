package com.kamilkorzeniewski.stockcontrol;

import com.kamilkorzeniewski.stockcontrol.invoice.InvoiceProductCsvLoader;
import com.kamilkorzeniewski.stockcontrol.invoice.InvoiceLoaderParameter;
import com.kamilkorzeniewski.stockcontrol.product.Product;
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

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Map<Integer,String> fields = new HashMap<>();
        fields.put(0,"name");
        fields.put(1,"code");
        fields.put(2,"price");
        fields.put(4,"quantity");

        InvoiceLoaderParameter invoiceLoaderParameter = new InvoiceLoaderParameter();
        invoiceLoaderParameter.put("path","/Users/kamilkorzeniewski/csvfile.csv");
        invoiceLoaderParameter.put("field_names",fields);
        invoiceLoaderParameter.put("row_offset",1);


       List<Product> products = invoiceProductCsvLoader.load(invoiceLoaderParameter);
       products.stream().forEach(Product::toString);

    }
}

