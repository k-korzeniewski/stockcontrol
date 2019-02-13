package com.kamilkorzeniewski.stockcontrol.invoice.csv;

import com.kamilkorzeniewski.stockcontrol.invoice.InvoiceLoader;
import com.kamilkorzeniewski.stockcontrol.invoice.InvoiceLoaderParameter;
import com.kamilkorzeniewski.stockcontrol.product.Product;
import com.kamilkorzeniewski.stockcontrol.utils.CsvReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@Qualifier("csvProductInvoiceLoader")
public class CsvProductInvoiceLoader implements InvoiceLoader<Product> {

    @Autowired
    @Qualifier("productCsvReaderBean")
    CsvReader<Product> productCsvReader;

    @Override
    public List<Product> load(InvoiceLoaderParameter parameters) {


        String path = Optional.of(parameters.get("path"))
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .orElseThrow(IllegalArgumentException::new);

        @SuppressWarnings("unchecked")// unfortunately cant check if generic types are compatible, because there is no runtime difference
                                        // between Map<Integer,String> or for example Map<String,String>
                                        // but we access only Map<Integer,String> in product service
                Map<Integer, String> fieldNames = Optional.of(parameters.get("field_names"))
                .filter(Map.class::isInstance)
                .map(Map.class::cast)
                .orElseThrow(IllegalArgumentException::new);

        int rowOffset = Optional.of(parameters.get("row_offset"))
                .filter(Integer.class::isInstance)
                .map(Integer.class::cast)
                .filter(x -> x >= 0)
                .orElseThrow(IllegalArgumentException::new);


        productCsvReader.addFieldsDeclarations(fieldNames);
        return productCsvReader.read(path, rowOffset);

    }

}
