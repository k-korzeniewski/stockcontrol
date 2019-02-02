package com.kamilkorzeniewski.stockcontrol.invoice;

import java.util.List;

public interface InvoiceLoader {
    List<?> load(InvoiceLoaderParameter parameters);
}
