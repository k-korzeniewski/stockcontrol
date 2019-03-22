package com.kamilkorzeniewski.stockcontrol.reader;

import javax.validation.constraints.NotNull;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


public class CsvReader<T> {

    private static final char DEFAULT_SEPARATOR = ',';
    private Class<T> clazz;
    private Set<FieldMapping> fields = new HashSet<>();

    public CsvReader(Class<T> clazz) {
        this.clazz = clazz;
    }


    public List<T> read(@NotNull String path, @NotNull int rowOffset) {
        if (rowOffset < 0) {
            throw new IllegalArgumentException("RowOffset must be greater or equal 0");
        }
        if (fields.size() <= 0) {
            throw new IllegalArgumentException("No defined fields");
        }

        List<T> result = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            for (int i = rowOffset; i > 0; i--) {
                br.readLine();                                                  // Skip lines before offset.
            }
            while ((line = br.readLine()) != null) {
                result.add(resolveLine(line));
            }
        } catch (InstantiationException | NoSuchMethodException |
                InvocationTargetException | IllegalAccessException |
                IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void addFieldsDeclarations(Set<FieldMapping> fieldsMapping) {
        fields.addAll(fieldsMapping);
    }

    private T resolveLine(@NotNull String line) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {

        T object = clazz.getDeclaredConstructor().newInstance();
        String[] columns = line.split(Character.toString(DEFAULT_SEPARATOR));
        for (int i = 0; i < columns.length; i++) {
            for (FieldMapping f : fields) {
                if (f.containColumn(i)) {
                    this.setFieldValue(f.getName(), columns[i], object);
                }
            }
        }
        return object;
    }


    private void setFieldValue(@NotNull String fieldName, @NotNull Object value, @NotNull Object target) {
        Field field;
        try {
            field = target.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            field = null;
        }

        try {
            Field opt_field = Optional.ofNullable(field).orElseThrow(() -> new IllegalArgumentException("Field " + fieldName + " not found"));
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
            if (opt_field.getType().equals(Long.TYPE)) {
                opt_field.set(target, Long.valueOf((String) value));
            }
            if (opt_field.getType().equals(Boolean.TYPE)) {
                opt_field.set(target, Boolean.valueOf((String) value));
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

}
