package com.kamilkorzeniewski.stockcontrol;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class StockcontrolApplication  {

    public static void main(String[] args) {
        SpringApplication.run(StockcontrolApplication.class, args);
    }


}

