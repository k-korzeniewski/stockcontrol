package com.kamilkorzeniewski.stockcontrol.product;

import com.kamilkorzeniewski.stockcontrol.invoice.csv.CsvInvoiceParameter;
import com.kamilkorzeniewski.stockcontrol.invoice.csv.CsvProductInvoiceLoader;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {


    @Autowired
    private ProductService productService;

    @Autowired
    private CsvProductInvoiceLoader invoiceLoader;

    private static CsvInvoiceParameter csvTestInvoiceLoaderParameter() {
        String csvFilePath = new File("src/test/resources/csvfile.csv").getAbsolutePath();
        final int rowOffset = 1;
        Map<Integer, String> fieldNames = new HashMap<>();
        fieldNames.put(0, "name");
        fieldNames.put(1, "code");
        fieldNames.put(2, "quantity");
        fieldNames.put(3, "price");
        CsvInvoiceParameter parameter = new CsvInvoiceParameter(csvFilePath, rowOffset, fieldNames);

        return parameter;
    }

    @Before
    public void setUp() {
        Supplier<List<Product>> productSupplier = () -> invoiceLoader.load(csvTestInvoiceLoaderParameter());
        productService.loadProductsFrom(productSupplier).forEach(productService::saveProduct);
    }

    @Test
    public void loadProductsFrom_then_ok() {
        Supplier<List<Product>> productSupplier = () -> invoiceLoader.load(csvTestInvoiceLoaderParameter());
        List<Product> products = productService.loadProductsFrom(productSupplier);
        List<Product> validProducts = List.of(new Product("P1", 1, "KT1", 24.89f),
                new Product("P2", 33, "KT2", 12.5f));
        Assertions.assertThat(products).containsExactlyInAnyOrder(validProducts.toArray(new Product[validProducts.size()]));

    }

    @Test
    public void predicate_products_then_ok() {
        Supplier<List<Product>> productSupplier = () -> invoiceLoader.load(csvTestInvoiceLoaderParameter());
        List<Product> loadedProducts = productService.loadProductsFrom(productSupplier);
        Map<Product, List<Product>> predicates = productService.predicateProductsByName(loadedProducts);
        loadedProducts.forEach(product -> {
            List<String> predicateNames = predicates.get(product).stream().map(p -> p.name).collect(Collectors.toList());
            Assertions.assertThat(predicateNames).containsOnly(product.name);
        });
    }
}
