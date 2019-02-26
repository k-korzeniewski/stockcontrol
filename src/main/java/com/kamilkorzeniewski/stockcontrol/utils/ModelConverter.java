package com.kamilkorzeniewski.stockcontrol.utils;

public interface ModelConverter<T,R>  {

    T fromDto(R dto);
}
