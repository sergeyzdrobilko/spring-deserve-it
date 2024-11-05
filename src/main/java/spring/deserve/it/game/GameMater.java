package spring.deserve.it.game;

import spring.deserve.it.api.Inject;

public class GameMater {

    @Inject
    private PaperSpider spider1;
    @Inject
    private StatisticalSpider spider2;

    @Inject
    private HistoricalService historicalService;


    public void fight() {
        System.out.println("The battle begins!");

        // Пауки атакуют друг друга до тех пор, пока один не умрет
        while (spider1.isAlive() && spider2.isAlive()) {
            System.out.println("PaperSpider attacks: " + spider1.fight(spider2, 1));
            spider2.loseLife();
            if (!spider2.isAlive()) {
                System.out.println("StoneSpider has been defeated!");
                break;
            }

            System.out.println("StoneSpider attacks: " + spider2.fight(spider1, 1));
            spider1.loseLife();
            if (!spider1.isAlive()) {
                System.out.println("PaperSpider has been defeated!");
            }
        }

        System.out.println("The battle is over!");
    }

}
