package com.kamilkorzeniewski.stockcontrol.invoice;

import com.kamilkorzeniewski.stockcontrol.invoice.csv.CsvProductInvoiceParameterDto;
import com.kamilkorzeniewski.stockcontrol.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private ProductService productService;

    @Autowired
    private InvoiceStorageService invoiceStorageService;

    @PostMapping("/csvproduct")
    public void loadCsvProductInvoice(@RequestBody MultipartFile file, @RequestBody CsvProductInvoiceParameterDto dto) {
        Path path = invoiceStorageService.storeFile(file);
        productService.loadProductsFromCsv(path.toString(),dto.field_names,dto.row_offset);
    }
}
