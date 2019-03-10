package com.kamilkorzeniewski.stockcontrol.invoice.csv;


import java.util.Map;

public class CsvInvoiceParameter {
    private String path;
    private int rowOffset;
    private Map<Integer, String> fieldNames;

    public CsvInvoiceParameter(String path, int rowOffset, Map<Integer, String> stringMap) {
        this.path = path;
        this.rowOffset = rowOffset;
        this.fieldNames = stringMap;
    }

    public String getPath() {
        return path;
    }

    public int getRowOffset() {
        return rowOffset;
    }

    public Map<Integer, String> getFieldNames() {
        return fieldNames;
    }
}
