package spring.deserve.it.api;

import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ObjectFactory  {
    private static final ObjectFactory INSTANCE = new ObjectFactory();

    // Мапа для хранения всех конфигураторов по их классам
    private final Map<Class<?>, ObjectConfigurator> configurators = new HashMap<>();

    private ObjectFactory() {
        // Регистрация конфигураторов. Каждый конфигуратор добавляется с соответствующим классом.
        configurators.put(PropertyObjectConfigurator.class, new PropertyObjectConfigurator());
        injectDependencies(this); // Инъекция зависимостей для ObjectFactory, если нужно
    }

    public static ObjectFactory getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    public <T> T createObject(Class<T> clazz) {
        T instance = clazz.getDeclaredConstructor().newInstance();
        configureObject(instance); // Настройка объекта через конфигураторы
        injectDependencies(instance); // Инъекция зависимостей для создаваемого объекта
        return instance;
    }

    private void configureObject(Object obj) {
        // Проходим по всем конфигураторам и вызываем их метод configure
        for (ObjectConfigurator configurator : configurators.values()) {
            configurator.configure(obj);
        }
    }

    @SneakyThrows
    private void injectDependencies(Object target) {
        Class<?> clazz = target.getClass();
        Set<Field> fields = ReflectionUtils.getAllFields(clazz);
        for (Field field : fields) {
            if (field.isAnnotationPresent(Inject.class)) {
                Class<?> fieldType = field.getType();
                Object dependency = createObject(fieldType);
                field.setAccessible(true);
                field.set(target, dependency);
            }
        }
    }
}
