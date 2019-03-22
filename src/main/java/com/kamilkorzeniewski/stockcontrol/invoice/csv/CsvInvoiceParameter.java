package com.kamilkorzeniewski.stockcontrol.invoice.csv;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kamilkorzeniewski.stockcontrol.reader.FieldMapping;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class CsvInvoiceParameter {
    @NotNull
    private String path;
    @NotNull
    private int rowOffset;
    @NotNull
    @JsonProperty("fieldMappingList")
    private Set<FieldMapping> fieldMappingList;

    private CsvInvoiceParameter(){}

    @JsonCreator
    public CsvInvoiceParameter(@NotNull @JsonProperty("path") String path, @NotNull @JsonProperty("rowOffset") int rowOffset,
                               @NotNull @JsonProperty("fieldMappingList") Set<FieldMapping> fieldMappingList) {
        this.path = path;
        this.rowOffset = rowOffset;
        this.fieldMappingList = fieldMappingList;
    }

    String getPath() {
        return path;
    }

    int getRowOffset() {
        return rowOffset;
    }

    Set<FieldMapping> getFieldNames() {
        return fieldMappingList;
    }

}
