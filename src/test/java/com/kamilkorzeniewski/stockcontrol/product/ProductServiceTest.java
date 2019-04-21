package com.kamilkorzeniewski.stockcontrol.product;

import com.kamilkorzeniewski.stockcontrol.invoice.InvoiceStorageService;
import com.kamilkorzeniewski.stockcontrol.invoice.csv.CsvInvoiceParameter;
import com.kamilkorzeniewski.stockcontrol.invoice.csv.CsvProductInvoiceLoader;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableConfigurationProperties
public class ProductServiceTest {
    private final List<Product> validTestProducts;
    @Value("${csv.test-file-path}")
    private String testFilePath;
    @Autowired
    private ProductService productService;
    @Autowired
    private CsvProductInvoiceLoader invoiceLoader;

    @Autowired
    private InvoiceStorageService invoiceStorageService;

    public ProductServiceTest() {
        validTestProducts = List.of(new Product("P1", 1, "KT1", 24.89f),
                new Product("P2", 33, "KT2", 12.5f));
    }

    private CsvInvoiceParameter prepareInvoiceLoaderParameter() {
        final int offset = 1;
        return new CsvInvoiceParameter(testFilePath, offset, TestUtils.prepareFieldMapping());
    }

    @Before
    public void setUp() {
        TestUtils.setUpTestCsvFile(invoiceStorageService.getFileStoragePath(testFilePath), validTestProducts);
    }


    @Test
    public void whenLoadProductsFromFile_thenReturnProductsList() {
        Supplier<List<Product>> productSupplier = () -> invoiceLoader.load(prepareInvoiceLoaderParameter());
        List<Product> resultProducts = productService.loadProductsFrom(productSupplier);
        Assertions.assertThat(resultProducts).isEqualTo(validTestProducts);
    }

    @Test
    public void whenPredicateProducts_thenReturnPredicateMap() {
        Supplier<List<Product>> productSupplier = () -> invoiceLoader.load(prepareInvoiceLoaderParameter());
        List<Product> loadedProducts = productService.loadProductsFrom(productSupplier);

        loadedProducts.forEach(productService::saveProduct);
        Map<Product, List<Product>> predicates = productService.predicateProducts(loadedProducts);
        loadedProducts.forEach(product -> {
            List<String> predicateNames = predicates.get(product).stream().map(p -> p.name).collect(Collectors.toList());
            Assertions.assertThat(predicateNames).containsOnly(product.name);
        });
    }


}
