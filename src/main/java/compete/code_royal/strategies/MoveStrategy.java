package main.java.compete.code_royal.strategies;

import main.java.compete.code_royal.objects.Coordinate;
import main.java.compete.code_royal.objects.GameInfo;
import main.java.compete.code_royal.utils.FindUtils;

public class MoveStrategy {

    public Coordinate computeMove(GameInfo gameInfo) {
        return FindUtils.findTheClosestCoordinate(gameInfo.start, gameInfo.extremities);
    }
}
