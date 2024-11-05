package spring.deserve.it.game;

import spring.deserve.it.api.RPSEnum;
import spring.deserve.it.api.Spider;

public class StoneSpider extends AbstractSpider {
    @Override
    public RPSEnum fight(Spider spider, int battleId) {
        return RPSEnum.ROCK;
    }
}
