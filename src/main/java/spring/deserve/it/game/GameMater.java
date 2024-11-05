package spring.deserve.it.game;

import spring.deserve.it.api.Inject;

public class GameMater {
    @Inject private PaperSpider paperSpider;
    @Inject private StoneSpider stoneSpider;


    public void fight() {
        System.out.println("The battle begins!");

        // Пауки атакуют друг друга до тех пор, пока один не умрет
        while (paperSpider.isAlive() && stoneSpider.isAlive()) {
            System.out.println("PaperSpider attacks: " + paperSpider.fight(stoneSpider, 1));
            stoneSpider.loseLife();
            if (!stoneSpider.isAlive()) {
                System.out.println("StoneSpider has been defeated!");
                break;
            }

            System.out.println("StoneSpider attacks: " + stoneSpider.fight(paperSpider, 1));
            paperSpider.loseLife();
            if (!paperSpider.isAlive()) {
                System.out.println("PaperSpider has been defeated!");
            }
        }

        System.out.println("The battle is over!");
    }

}
