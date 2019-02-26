package com.kamilkorzeniewski.stockcontrol.invoice.csv;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.nio.file.Path;
import java.util.Map;

public class CsvProductInvoiceParameterDto {
    @NotNull
    @Min(0)
    public int row_offset;
    @NotNull
    public Map<Integer,String> field_names;

    @JsonIgnore
    public Path path;

    public CsvProductInvoiceParameterDto( int row_offset,  Map<Integer, String> field_names, Path path) {
        this.row_offset = row_offset;
        this.field_names = field_names;
        this.path = path;
    }
}
