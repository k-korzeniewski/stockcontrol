package com.kamilkorzeniewski.stockcontrol.invoice;

import com.kamilkorzeniewski.stockcontrol.product.Product;

import java.util.List;

public interface InvoiceLoader {
    List<?> load(InvoiceLoaderParameter parameters);
}
