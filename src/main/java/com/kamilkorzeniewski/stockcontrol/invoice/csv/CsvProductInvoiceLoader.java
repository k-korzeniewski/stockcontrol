package com.kamilkorzeniewski.stockcontrol.invoice.csv;

import com.kamilkorzeniewski.stockcontrol.invoice.InvoiceStorageService;
import com.kamilkorzeniewski.stockcontrol.product.Product;
import com.kamilkorzeniewski.stockcontrol.reader.CsvReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Qualifier("csvProductInvoiceLoader")
public class CsvProductInvoiceLoader {

    private CsvReader<Product> productCsvReader;
    private InvoiceStorageService invoiceStorageService;

    @Autowired
    public CsvProductInvoiceLoader(@Qualifier("productCsvReaderBean") CsvReader<Product> productCsvReader,
                                   InvoiceStorageService invoiceStorageService) {
        this.productCsvReader = productCsvReader;
        this.invoiceStorageService = invoiceStorageService;
    }
    public List<Product> load(CsvInvoiceParameter parameter) {
        productCsvReader.addFieldsDeclarations(parameter.getFieldNames());
        return productCsvReader.read(invoiceStorageService.getFileStoragePath(parameter.getPath()),
                                        parameter.getRowOffset());
    }

}
