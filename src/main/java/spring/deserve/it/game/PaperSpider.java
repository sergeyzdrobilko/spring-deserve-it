package spring.deserve.it.game;

import spring.deserve.it.api.RPSEnum;
import spring.deserve.it.api.Spider;

public class PaperSpider extends AbstractSpider {
    @Override
    public RPSEnum fight(Spider spider, int battleId) {
        return RPSEnum.PAPER;
    }
}
