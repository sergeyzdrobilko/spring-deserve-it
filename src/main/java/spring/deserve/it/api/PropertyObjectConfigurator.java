package spring.deserve.it.api;

import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

public class PropertyObjectConfigurator implements ObjectConfigurator {

    private final Properties properties = new Properties();

    public PropertyObjectConfigurator() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new IllegalArgumentException("Configuration file not found");
            }
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @SneakyThrows
    public void configure(Object target) {
        Class<?> clazz = target.getClass();
        for (Field field : ReflectionUtils.getAllFields(target.getClass())) {
            if (field.isAnnotationPresent(InjectProperty.class)) {
                InjectProperty annotation = field.getAnnotation(InjectProperty.class);
                String propertyKey = annotation.value().isEmpty() ? field.getName() : annotation.value();
                String value = properties.getProperty(propertyKey);
                if (value != null) {
                    field.setAccessible(true);
                    if (field.getType() == int.class) {
                        field.setInt(target, Integer.parseInt(value));
                    } else {
                        field.set(target, value);
                    }
                }
            }
        }
    }
}
