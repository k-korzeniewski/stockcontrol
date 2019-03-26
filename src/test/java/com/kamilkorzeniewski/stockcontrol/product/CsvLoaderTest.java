package com.kamilkorzeniewski.stockcontrol.product;

import com.kamilkorzeniewski.stockcontrol.invoice.InvoiceStorageService;
import com.kamilkorzeniewski.stockcontrol.invoice.csv.CsvInvoiceParameter;
import com.kamilkorzeniewski.stockcontrol.invoice.csv.CsvProductInvoiceLoader;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableConfigurationProperties
public class CsvLoaderTest {

    private final List<Product> validTestProducts;

    @Autowired
    @Qualifier("csvProductInvoiceLoader")
    private CsvProductInvoiceLoader csvProductInvoiceLoader;

    @Value("${csv.test-file-path}")
    private String testFilePath;

    @Autowired
    private InvoiceStorageService invoiceStorageService;


    public CsvLoaderTest() {
        validTestProducts = List.of(new Product("P1", 1, "KT1", 24.89f),
                new Product("P2", 33, "KT2", 12.5f));
    }

    @Before
    public void setUp() {
        TestUtils.setUpTestCsvFile(invoiceStorageService.getFileStoragePath(testFilePath), validTestProducts);
    }

    @Test
    public void whenLoadCsv_thenReturnProductList() {
        final int offset = 1;
        CsvInvoiceParameter csvInvoiceParameter = new CsvInvoiceParameter(testFilePath, offset, TestUtils.prepareFieldMapping());
        List<Product> resultProducts = csvProductInvoiceLoader.load(csvInvoiceParameter);
        Assertions.assertThat(resultProducts).isEqualTo(validTestProducts);
    }

    


}
