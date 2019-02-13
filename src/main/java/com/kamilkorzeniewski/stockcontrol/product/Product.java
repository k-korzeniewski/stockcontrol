package com.kamilkorzeniewski.stockcontrol.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.PropertyType;
import com.querydsl.core.annotations.QueryType;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false)
    public Long id;

    @NotNull
    @Column(name = "product_name")
    public String name;

    @NotNull
    @Min(value = 0)
    @Column(name = "product_quantity")
    public int quantity;

    @Column(name = "product_code")
    public String code;

    @Column(name = "product_price")
    @Min(value = 0)
    @NotNull
    public float price;

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
    //

    public Product() {
    }

    private Product(String name, int quantity, String code, float price) {
        this.name = name;
        this.quantity = quantity;
        this.code = code;
        this.price = price;
    }

    private Product(Long id, String name, int quantity, String code, float price){
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.code = code;
        this.price = price;
    }
    Product productWith(String name){
        return new Product(name,this.quantity,this.code,this.price);
    }

    Product productWith(Product product){
        return new Product(this.id,product.name,product.quantity,product.code,product.price);
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



}
