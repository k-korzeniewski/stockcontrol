package com.kamilkorzeniewski.stockcontrol.invoice;

import java.util.List;

public interface InvoiceLoader<T> {
    List<T> load(InvoiceLoaderParameter parameters);
}
