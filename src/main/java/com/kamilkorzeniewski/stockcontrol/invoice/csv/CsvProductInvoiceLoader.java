package com.kamilkorzeniewski.stockcontrol.invoice.csv;

import com.kamilkorzeniewski.stockcontrol.product.Product;
import com.kamilkorzeniewski.stockcontrol.utils.CsvReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
@Qualifier("csvProductInvoiceLoader")
public class CsvProductInvoiceLoader {

    private CsvReader<Product> productCsvReader;

    @Autowired
    public CsvProductInvoiceLoader(@Qualifier("productCsvReaderBean") CsvReader<Product> productCsvReader) {
        this.productCsvReader = productCsvReader;
    }

    public List<Product> load(CsvInvoiceParameter parameter) {
        String path = parameter.getPath();
        Map<Integer, String> fieldNames = parameter.getFieldNames();
        int rowOffset = parameter.getRowOffset();

        productCsvReader.addFieldsDeclarations(fieldNames);
        return productCsvReader.read(path, rowOffset);

    }

}
