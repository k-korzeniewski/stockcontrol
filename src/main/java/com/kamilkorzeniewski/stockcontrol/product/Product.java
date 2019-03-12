package com.kamilkorzeniewski.stockcontrol.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.PropertyType;
import com.querydsl.core.annotations.QueryType;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotNull
    @Column(name = "product_name")
    String name;

    @NotNull
    @Min(value = 0)
    @Column(name = "product_quantity")
    private int quantity;

    @Column(name = "product_code")
    private String code;

    @Column(name = "product_price")
    @Min(value = 0)
    @NotNull
    private float price;

    // below fields are for query filtering
    @Transient
    @QueryType(PropertyType.NUMERIC)
    @JsonIgnore
    public float priceFrom;

    @Transient
    @QueryType(PropertyType.NUMERIC)
    @JsonIgnore
    public float priceTo;

    @Transient
    @QueryType(PropertyType.NUMERIC)
    @JsonIgnore
    public int quantityFrom;

    @Transient
    @QueryType(PropertyType.NUMERIC)
    @JsonIgnore
    public int quantityTo;


    public Product() {
    }

    Product(String name, int quantity, String code, float price) {
        this.name = name;
        this.quantity = quantity;
        this.code = code;
        this.price = price;
    }

    private Product(Long id, String name, int quantity, String code, float price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.code = code;
        this.price = price;
    }


    Product productWith(String name) {
        return new Product(name, this.quantity, this.code, this.price);
    }

    Product productWith(Product product) {
        return new Product(this.id, product.name, product.quantity, product.code, product.price);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", code='" + code + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return quantity == product.quantity &&
                Float.compare(product.price, price) == 0 &&
                Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(code, product.code);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, quantity, code, price);
    }
}
