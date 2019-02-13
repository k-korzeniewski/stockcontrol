package com.kamilkorzeniewski.stockcontrol.configuration;

import com.kamilkorzeniewski.stockcontrol.invoice.InvoiceStorageProperties;
import com.kamilkorzeniewski.stockcontrol.product.Product;
import com.kamilkorzeniewski.stockcontrol.utils.CsvReader;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        InvoiceStorageProperties.class
})
public class AppConfig {
    @Bean(name = "productCsvReaderBean")
    public CsvReader<Product> productCsvReader(){
        return new CsvReader<>(Product.class);
    }
}
