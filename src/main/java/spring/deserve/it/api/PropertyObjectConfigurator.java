package spring.deserve.it.api;

import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

// Класс для конфигурирования объектов с использованием загруженных свойств
public class PropertyObjectConfigurator implements ObjectConfigurator {
    private final PropertyLoader propertyLoader = new PropertyLoader();
    private final Map<Class<?>, Function<String, ?>> converters = new HashMap<>();

    public PropertyObjectConfigurator() {
        initializeConverters();
    }

    private void initializeConverters() {
        // Инициализация мапы с типами и соответствующими функциями преобразования
        converters.put(int.class, Integer::valueOf);
        converters.put(Integer.class, Integer::valueOf);
        converters.put(boolean.class, Boolean::valueOf);
        converters.put(Boolean.class, Boolean::valueOf);
        converters.put(double.class, Double::valueOf);
        converters.put(Double.class, Double::valueOf);
        converters.put(long.class, Long::valueOf);
        converters.put(Long.class, Long::valueOf);
        converters.put(String.class, Function.identity()); // Для строк просто возвращаем значение
    }

    @SneakyThrows
    public void configure(Object target) {
        Class<?> clazz = target.getClass();
        for (Field field : ReflectionUtils.getAllFields(clazz)) {
            if (field.isAnnotationPresent(InjectProperty.class)) {
                InjectProperty annotation = field.getAnnotation(InjectProperty.class);
                String propertyKey = annotation.value().isEmpty() ? field.getName() : annotation.value();
                String value = propertyLoader.getProperty(propertyKey);
                if (value != null) {
                    field.setAccessible(true);
                    Object convertedValue = convertValue(field.getType(), value);
                    field.set(target, convertedValue);
                }
            }
        }
    }

    private Object convertValue(Class<?> fieldType, String value) {
        Function<String, ?> converter = converters.get(fieldType);
        if (converter != null) {
            return converter.apply(value);
        } else {
            throw new IllegalArgumentException("Unsupported field type: " + fieldType.getName());
        }
    }
}
