package spring.deserve.it.api;

import lombok.SneakyThrows;
import org.reflections.Reflections;
import spring.deserve.it.game.PaperSpider;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ObjectFactory {

    // Единственный экземпляр класса
    private static final ObjectFactory INSTANCE = new ObjectFactory();

    private final List<ObjectConfigurator> configurators = new ArrayList<>();

    // Приватный конструктор, чтобы предотвратить создание экземпляров извне
    private ObjectFactory() {
        // Используем Reflections для поиска всех классов, реализующих ObjectConfigurator
        Reflections reflections = new Reflections("spring.deserve.it"); // Укажите свой пакет здесь
        Set<Class<? extends ObjectConfigurator>> configuratorClasses = reflections.getSubTypesOf(ObjectConfigurator.class);

        // Создаем экземпляры конфигураторов и добавляем их в список
        for (Class<? extends ObjectConfigurator> configuratorClass : configuratorClasses) {
            try {
                configurators.add(configuratorClass.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                throw new RuntimeException("Failed to create configurator instance: " + configuratorClass, e);
            }
        }
    }

    // Публичный метод для получения экземпляра
    public static ObjectFactory getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    public <T> T createObject(Class<T> type) {
        T t = type.getDeclaredConstructor().newInstance();


        configurators.forEach(configurator -> configurator.configure(t));

        return t;
    }
}
