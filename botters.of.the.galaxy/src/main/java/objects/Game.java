package objects;

import enums.HeroEnum;
import enums.SideEnum;
import enums.TeamEnum;
import utils.SearchUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    public int gold;
    public int enemyGold;
    public int roundType; // a positive value will show the number of heroes that await a command
    public List<Entity> entities = new ArrayList<>();
    public SideEnum side;
    public Hero currentHero;

    public void update(Scanner in) {
        gold = in.nextInt();
        enemyGold = in.nextInt();
        roundType = in.nextInt(); // a positive value will show the number of heroes that await a command
    }

    public void updateSide() {
        if (side != null)
            return;

        Entity myTower = SearchUtils.findTower(entities, TeamEnum.FRIEND);
        Entity enemyTower = SearchUtils.findTower(entities, TeamEnum.ENEMY);
        if (myTower.coordinate.x > enemyTower.coordinate.x) {
            side = SideEnum.RIGHT;
        } else {
            side = SideEnum.LEFT;
        }
    }

    public boolean isFirstRound() {
        return roundType < 0;
    }
}
