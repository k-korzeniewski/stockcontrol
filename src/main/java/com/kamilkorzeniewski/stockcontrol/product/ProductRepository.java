package com.kamilkorzeniewski.stockcontrol.product;

import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository, QuerydslPredicateExecutor<QProduct>, QuerydslBinderCustomizer<QProduct> {

    @Override
    default void customize(QuerydslBindings bindings, QProduct root) {
        ProductCustomBindings.bind(bindings, root);
    }
}