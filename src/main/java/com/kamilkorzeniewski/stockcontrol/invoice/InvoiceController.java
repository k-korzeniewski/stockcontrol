package com.kamilkorzeniewski.stockcontrol.invoice;

import com.kamilkorzeniewski.stockcontrol.invoice.csv.CsvProductInvoiceParameterDto;
import com.kamilkorzeniewski.stockcontrol.product.Product;
import com.kamilkorzeniewski.stockcontrol.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Supplier;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private ProductService productService;

    @Autowired
    private InvoiceStorageService invoiceStorageService;

    @Autowired
    @Qualifier("csvProductInvoiceLoader")
    private InvoiceLoader<Product> productInvoiceLoader;

    @PostMapping("/csv/product")
    public List<Product> loadCsvProductInvoice(@RequestBody MultipartFile file, @Valid @RequestBody CsvProductInvoiceParameterDto dto) {
        Path path = invoiceStorageService.storeFile(file);
        InvoiceLoaderParameter invoiceLoaderParameter = dto.fromDto(path);
        Supplier<List<Product>> loadFromCsvSupplier = () -> productInvoiceLoader.load(invoiceLoaderParameter);
        return productService.loadProductsFrom(loadFromCsvSupplier);

    }

}
