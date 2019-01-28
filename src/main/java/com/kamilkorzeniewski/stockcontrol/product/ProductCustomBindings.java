package com.kamilkorzeniewski.stockcontrol.product;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.Collection;
import java.util.Optional;

public class ProductCustomBindings {

    public static void bind(QuerydslBindings bindings) {
        bindings.bind(String.class).all((StringPath path, Collection<? extends String> values) -> {
            BooleanBuilder predicate = new BooleanBuilder();
            values.forEach(value -> { if (value.startsWith("*") && value.endsWith("*")) { predicate.or(path.containsIgnoreCase(value.substring(1,value.length() - 1)));
                }
                if(value.startsWith("*")){
                    predicate.or(path.endsWithIgnoreCase(value.substring(1, value.length())));
                }
                if(value.endsWith("*")){
                    predicate.or(path.startsWithIgnoreCase(value.substring(0, value.length() - 1)));
                }
            });
            return Optional.of(predicate);
        });
    }
}
