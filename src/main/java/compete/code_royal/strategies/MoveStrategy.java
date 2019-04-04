package main.java.compete.code_royal.strategies;

import main.java.compete.code_royal.enums.OwnerEnum;
import main.java.compete.code_royal.objects.Coordinate;
import main.java.compete.code_royal.objects.GameInfo;
import main.java.compete.code_royal.objects.Site;
import main.java.compete.code_royal.objects.Unit;
import main.java.compete.code_royal.utils.site.FindUtils;
import main.java.compete.code_royal.utils.site.TowerUtils;

import java.util.List;

public class MoveStrategy {

    public Coordinate computeMove(GameInfo gameInfo, List<Site> sites) {
        Coordinate startExtremity = FindUtils.findTheClosestCoordinate(gameInfo.start, gameInfo.extremities);

        return getSafePlace(sites, gameInfo);
    }

    private static Coordinate getSafePlace(List<Site> sites, GameInfo gameInfo) {
        Site tower = FindUtils.findTheClosestSite(gameInfo.opposedY, TowerUtils.findByOwner(sites, OwnerEnum.FRIEND));
        if (tower == null)
            return gameInfo.opposedY;
        return tower.coordinate;
    }
}
