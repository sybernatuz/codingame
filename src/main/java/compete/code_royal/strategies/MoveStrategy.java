package main.java.compete.code_royal.strategies;

import main.java.compete.code_royal.objects.Coordinate;
import main.java.compete.code_royal.objects.GameInfo;
import main.java.compete.code_royal.objects.Site;
import main.java.compete.code_royal.objects.Unit;
import main.java.compete.code_royal.utils.site.FindUtils;

import java.util.List;

public class MoveStrategy {

    public Coordinate computeMove(GameInfo gameInfo, List<Site> sites) {
        Site site = FindUtils.findClosestSiteToOpposedExtremity(sites, gameInfo);

        if (gameInfo.isFirstAction)
            return site.coordinate;

        Coordinate startExtremity = FindUtils.findTheClosestCoordinate(gameInfo.start, gameInfo.extremities);
        return startExtremity;
    }
}
