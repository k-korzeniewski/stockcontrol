package com.kamilkorzeniewski.stockcontrol.product;

import com.kamilkorzeniewski.stockcontrol.reader.FieldMapping;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Set;

public class TestUtils {

    static Set<FieldMapping> prepareFieldMapping() {
        FieldMapping nameFiled = new FieldMapping("name", 0);
        FieldMapping codeField = new FieldMapping("code", 1);
        FieldMapping quantityField = new FieldMapping("quantity", 2);
        FieldMapping priceField = new FieldMapping("price", 3);
        return Set.of(nameFiled, codeField, quantityField, priceField);
    }
    static void setUpTestCsvFile(String testFilePath,List<Product> products){
        File file = new File(testFilePath);
        try {
            if (!file.createNewFile())
                Files.newBufferedWriter(file.toPath(), StandardOpenOption.TRUNCATE_EXISTING).close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder builder = new StringBuilder();
        builder.append("Nazwa:,Kod towaru:,Ilość produktów:,Wartość:\n");
        for (Product product : products) {
            builder.append(product.name).append(",")
                    .append(product.code).append(",")
                    .append(product.quantity).append(",")
                    .append(product.price).append("\n");
        }
        try (Writer writer = new FileWriter(testFilePath)) {
            writer.write(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
