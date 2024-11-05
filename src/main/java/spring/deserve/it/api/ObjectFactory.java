package spring.deserve.it.api;

import spring.deserve.it.game.PaperSpider;

public class ObjectFactory {

    // Единственный экземпляр класса
    private static final ObjectFactory INSTANCE = new ObjectFactory();

    // Приватный конструктор, чтобы предотвратить создание экземпляров извне
    private ObjectFactory() {}

    // Публичный метод для получения экземпляра
    public static ObjectFactory getInstance() {
        return INSTANCE;
    }

    public <T> T createObject(Class<T> paperSpiderClass) {
        return null;
    }
}
