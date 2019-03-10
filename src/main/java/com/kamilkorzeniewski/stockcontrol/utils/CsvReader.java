package com.kamilkorzeniewski.stockcontrol.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

// Read csv file and by reflection create object of given class

public class CsvReader<T> {

    private static final char DEFAULT_SEPARATOR = ',';
    private Class<T> clazz;
    private Map<Integer, String> fields = new HashMap<>(); // Fields names and corresponding column numbers

    public CsvReader(Class<T> clazz) {
        this.clazz = clazz;
    }


    public List<T> read(String path, int rowOffset) {

        List<T> resultList = new ArrayList<>();

        if (rowOffset < 0) {
            throw new IllegalArgumentException("Row_offset must be greater or equal 0");
        }
        if (fields.size() <= 0) {
            return Collections.emptyList();
        }


        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            for (int i = rowOffset; i > 0; i--) {
                br.readLine(); // Skip lines before row_offset
            }

            while ((line = br.readLine()) != null) {
                T object = clazz.getDeclaredConstructor().newInstance();

                String[] lines = line.split(Character.toString(DEFAULT_SEPARATOR));
                for (int i = 0; i < lines.length; i++) {
                    if (fields.containsKey(i)) {
                        this.setFieldValue(fields.get(i), lines[i], object);
                    }
                }
                resultList.add(object);


            }

        } catch (InstantiationException | NoSuchMethodException |
                InvocationTargetException | IllegalAccessException |
                IOException e) {
            e.printStackTrace();
        }

        return resultList;
    }


    public void addFieldsDeclarations(Map<Integer, String> fieldMaps) {
        fields.putAll(fieldMaps);
    }

    private void setFieldValue(String fieldName, Object value, Object target) {
        Field field;
        try {
            field = target.getClass().getDeclaredField(fieldName);

        } catch (NoSuchFieldException e) {
            field = null;
        }

        try {
            Field opt_field = Optional.ofNullable(field).orElseThrow(() -> new IllegalArgumentException("Field "+fieldName+" not founded"));
            opt_field.setAccessible(true);
            if (opt_field.getType().equals(String.class)) {
                opt_field.set(target, String.valueOf(value));
            }
            if (opt_field.getType().equals(Integer.TYPE)) {
                opt_field.set(target, Integer.valueOf((String) value));
            }
            if (opt_field.getType().equals(Float.TYPE)) {
                opt_field.set(target, Float.valueOf((String) value));
            }
            if(opt_field.getType().equals(Long.TYPE)){
                opt_field.set(target,Long.valueOf((String) value));
            }
            if(opt_field.getType().equals(Boolean.TYPE)){
                opt_field.set(target,Boolean.valueOf((String) value));
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

}
