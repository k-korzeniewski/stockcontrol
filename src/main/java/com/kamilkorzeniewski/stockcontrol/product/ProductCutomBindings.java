package com.kamilkorzeniewski.stockcontrol.product;

import org.springframework.data.querydsl.binding.QuerydslBindings;

public class ProductCutomBindings {
    public static void bind(QuerydslBindings bindings, QProduct root) {
        bindings.bind(root.name,root.code).first(((path, value) -> {
            if (value.startsWith("*")) {
                return path.endsWithIgnoreCase(value.substring(1, value.length()));
            } else if (value.endsWith("*")) {
                return path.startsWithIgnoreCase(value.substring(0,value.length() - 1));
            }
            if(value.startsWith("*") && value.endsWith("*")){
                return path.containsIgnoreCase(value);
            }
            return path.equalsIgnoreCase(value);
        }));
    }
}
