package com.kamilkorzeniewski.stockcontrol.invoice;

import com.kamilkorzeniewski.stockcontrol.product.Product;
import com.kamilkorzeniewski.stockcontrol.utils.CsvReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;



@Service
@Qualifier("invoiceProductCsvLoader")
public class InvoiceProductCsvLoader implements InvoiceLoader {

    @Override
    public List<Product> load(InvoiceLoaderParameter parameters) {


        String path = Optional.of(parameters.get("path"))
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .orElseThrow(IllegalArgumentException::new);

        @SuppressWarnings("unchecked")
        Map<Integer, String> fieldNames = Optional.of(parameters.get("field_names"))
                .filter(Map.class::isInstance)
                .map(Map.class::cast)
                .orElseThrow(IllegalArgumentException::new);

        int rowOffset = Optional.of(parameters.get("row_offset"))
                .filter(Integer.class::isInstance)
                .map(Integer.class::cast)
                .filter(x -> x >= 0)
                .orElseThrow(IllegalArgumentException::new);


        CsvReader<Product> csvReader = new CsvReader<>(Product.class);
        csvReader.addFieldsDeclarations(fieldNames);
        return csvReader.read(path, rowOffset);

    }


}
