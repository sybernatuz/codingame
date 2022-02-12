package strategies;

import beans.Board;
import beans.Entity;
import beans.EntityType;

public class StrategyFactory {

    private final Strategy radarStrategy = new RadarStrategy();
    private final Strategy defaultStrategy = new DefaultStrategy();

    public Strategy findStrategy(Board board, Entity robot) {
        if (board.myRadarCooldown == 0 || EntityType.RADAR.equals(robot.item)) {
            return radarStrategy;
        }
        return defaultStrategy;
    }
}
