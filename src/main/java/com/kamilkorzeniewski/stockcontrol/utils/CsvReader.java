package com.kamilkorzeniewski.stockcontrol.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * This class read csv file and assign columns to field in class T by field name
 *
 * @param <T>
 */
public class CsvReader<T> {

    private static final char DEFAULT_SEPARATOR = ',';
    private static final Logger logger = LoggerFactory.getLogger(CsvReader.class);
    private Class clazz;
    private Map<Integer, String> fields = new HashMap<>(); // Fields names and corresponding column numbers

    public CsvReader(Class clazz) {
        this.clazz = clazz;
    }

    /**
     * @param rowOffset define from with row start reading. Must be greater or equal 0
     * @param path      define path to csv file
     **/

    public List<T> read(String path, int rowOffset) {

        List<T> resultList = new ArrayList<>();

        if (rowOffset < 0) {
            throw new IllegalArgumentException("Row_offset must be greater or equal 0");
        }
        if (fields.size() <= 0) {
            return Collections.emptyList();
        }

        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            for (int i = rowOffset; i >= 0; i--) {
                br.readLine(); // Skip lines before offset
            }

            while ((line = br.readLine()) != null) {
                T object = (T) clazz.getDeclaredConstructor().newInstance();

                String[] lines = line.split(Character.toString(DEFAULT_SEPARATOR));
                for (int i = 0; i < lines.length; i++) {
                    if (fields.containsKey(i)) {
                        this.setFieldValue(fields.get(i), lines[i], object);
                    }
                }
                resultList.add(object);

                // Debug logging
                StringBuilder sb = new StringBuilder();
                sb.append("New object loaded from CSV ( ").append(path).append(")").append("->").append(object.toString());
                logger.info(sb.toString());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    /**
     * Assign column number to fild name in class T
     *
     * @param classFieldName
     * @param columnNumber
     */
    public void addFieldDeclaration(String classFieldName, int columnNumber) {
        fields.put(columnNumber, classFieldName);

    }

    public void addFieldsDeclarations(Map<Integer,String> fieldMaps){
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
            Field opt_field = Optional.of(field).orElseThrow(IllegalArgumentException::new);
            opt_field.setAccessible(true);
            if (opt_field.getType().equals(String.class)){
                opt_field.set(target,String.valueOf(value));
            }
            if (opt_field.getType().equals(Integer.TYPE)) {
                opt_field.set(target,Integer.valueOf((String) value));
            }
            if(opt_field.getType().equals(Float.TYPE)){
                opt_field.set(target,Float.valueOf((String) value));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

}
