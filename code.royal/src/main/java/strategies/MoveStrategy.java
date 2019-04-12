package strategies;


import enums.OwnerEnum;
import objects.Coordinate;
import objects.GameInfo;
import objects.Site;
import utils.site.FindUtils;
import utils.site.TowerUtils;

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
