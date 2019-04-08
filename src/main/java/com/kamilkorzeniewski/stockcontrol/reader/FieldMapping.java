package com.kamilkorzeniewski.stockcontrol.reader;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

public class FieldMapping {
    @JsonProperty("name")
    private String name;
    @JsonProperty("column")
    private int column;

    @JsonCreator
    public FieldMapping(@JsonProperty("name") String name,@JsonProperty("column") int column) {
        this.name = name;
        this.column = column;
    }

    boolean containColumn(int column){
        return this.column == column;
    }

    String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldMapping that = (FieldMapping) o;
        return column == that.column &&
                Objects.equal(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, column);
    }
}
