package com.kamilkorzeniewski.stockcontrol.invoice;

import com.kamilkorzeniewski.stockcontrol.invoice.csv.CsvInvoiceParameter;
import com.kamilkorzeniewski.stockcontrol.invoice.csv.CsvProductInvoiceLoader;
import com.kamilkorzeniewski.stockcontrol.product.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private InvoiceStorageService invoiceStorageService;
    private CsvProductInvoiceLoader productInvoiceLoader;

    public InvoiceController(InvoiceStorageService invoiceStorageService,
                             @Qualifier("csvProductInvoiceLoader") CsvProductInvoiceLoader productInvoiceLoader) {
        this.invoiceStorageService = invoiceStorageService;
        this.productInvoiceLoader = productInvoiceLoader;

    }

    @PostMapping(path = "/csv", consumes = {"multipart/form-data"}, produces = "application/json")
    public ResponseEntity<String> saveCsvFile(@RequestPart("file") MultipartFile file) { // Save csv file with generated name and return path to file.
        Path filePath = invoiceStorageService.storeFile(file);
        return new ResponseEntity<>(filePath.toString(), HttpStatus.OK);
    }

    @GetMapping(path = "/csv", produces = "application/json")
    public ResponseEntity<List<Product>> loadProductsFromCsvFile(@RequestBody CsvInvoiceParameter invoiceParameter) {
        List<Product> products = productInvoiceLoader.load(invoiceParameter);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping(path = "/csv/{name}")
    public ResponseEntity<String> removeFile(@PathVariable String name) {
        invoiceStorageService.removeFromStorage(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
