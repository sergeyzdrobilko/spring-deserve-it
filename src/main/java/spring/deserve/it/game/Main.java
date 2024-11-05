package spring.deserve.it.game;

import spring.deserve.it.api.ObjectFactory;

public class Main {


    public static void main(String[] args) {
        System.out.println("Starting game");

        GameMater gameMater = ObjectFactory.getInstance().createObject(GameMater.class);
        gameMater.fight();
    }

}
