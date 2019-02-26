package com.kamilkorzeniewski.stockcontrol.invoice.csv;

import com.kamilkorzeniewski.stockcontrol.invoice.InvoiceLoaderParameter;
import com.kamilkorzeniewski.stockcontrol.utils.ModelConverter;
import org.springframework.stereotype.Component;

@Component
public class CsvProductModelConverter implements ModelConverter<InvoiceLoaderParameter,CsvProductInvoiceParameterDto> {
    @Override
    public InvoiceLoaderParameter fromDto(CsvProductInvoiceParameterDto dto) {
        InvoiceLoaderParameter ilp = new InvoiceLoaderParameter();
        ilp.put("path",dto.path.toString());
        ilp.put("row_offset",dto.row_offset);
        ilp.put("filed_names",dto.field_names);
        return ilp;
    }


}
