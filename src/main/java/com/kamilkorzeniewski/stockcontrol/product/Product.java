package com.kamilkorzeniewski.stockcontrol.product;

import com.querydsl.core.annotations.PropertyType;
import com.querydsl.core.annotations.QueryType;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "products")
public class Product {

    private Long id;

    private String name;

    private int quantity;

    private String code;

    private float price;

    @Transient
    @QueryType(PropertyType.SIMPLE)
    private float priceFrom;
    @QueryType(PropertyType.SIMPLE)
    @Transient
    private float priceTo;
    @QueryType(PropertyType.SIMPLE)
    @Transient
    private int quantityFrom;
    @QueryType(PropertyType.SIMPLE)
    @Transient
    private int quantityTo;

    public Product() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @NotNull
    @Column(name = "product_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Min(value = 0)
    @Column(name = "product_quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Column(name = "product_code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public float getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(float priceFrom) {
        this.priceFrom = priceFrom;
    }

    public float getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(float priceTo) {
        this.priceTo = priceTo;
    }

    public int getQuantityFrom() {
        return quantityFrom;
    }

    public void setQuantityFrom(int quantityFrom) {
        this.quantityFrom = quantityFrom;
    }

    public int getQuantityTo() {
        return quantityTo;
    }

    public void setQuantityTo(int quantityTo) {
        this.quantityTo = quantityTo;
    }
}
