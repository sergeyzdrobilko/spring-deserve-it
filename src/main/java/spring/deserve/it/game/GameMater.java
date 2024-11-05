package spring.deserve.it.game;

import spring.deserve.it.api.ObjectFactory;

public class GameMater {

    private PaperSpider paperSpider =ObjectFactory.getInstance().createObject(PaperSpider.class);
    private StoneSpider stoneSpider = ObjectFactory.getInstance().createObject(StoneSpider.class);


    public void fight() {
        System.out.println("The battle begins!");

        // Пауки атакуют друг друга до тех пор, пока один не умрет
        while (paperSpider.isAlive() && stoneSpider.isAlive()) {
            System.out.println("PaperSpider attacks: " + paperSpider.fight());
            stoneSpider.loseLife();
            if (!stoneSpider.isAlive()) {
                System.out.println("StoneSpider has been defeated!");
                break;
            }

            System.out.println("StoneSpider attacks: " + stoneSpider.fight());
            paperSpider.loseLife();
            if (!paperSpider.isAlive()) {
                System.out.println("PaperSpider has been defeated!");
            }
        }

        System.out.println("The battle is over!");
    }

}
