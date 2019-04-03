package main.java.compete.code_royal.strategies;

import main.java.compete.code_royal.enums.BarrackTypeEnum;
import main.java.compete.code_royal.enums.OwnerEnum;
import main.java.compete.code_royal.enums.UnitTypeEnum;
import main.java.compete.code_royal.objects.GameInfo;
import main.java.compete.code_royal.objects.Site;
import main.java.compete.code_royal.objects.Unit;
import main.java.compete.code_royal.utils.SortUtils;
import main.java.compete.code_royal.utils.site.BarrackUtils;
import main.java.compete.code_royal.utils.site.UnitUtils;

import java.util.ArrayList;
import java.util.List;

public class TrainStrategy {

    public List<Site> computeTrain(List<Site> sites, GameInfo gameInfo, List<Unit> units) {
        List<Site> sitesToTrain = new ArrayList<>();
        List<Site> possibleSitesToTrain = BarrackUtils.findWaitingBarrack(sites, OwnerEnum.FRIEND);

        List<Unit> friendArchers = UnitUtils.findByUnitType(units, OwnerEnum.FRIEND, UnitTypeEnum.ARCHER);
        if (friendArchers.size() >= 1) {
            Unit enemyQueen = UnitUtils.findQueen(units, OwnerEnum.ENEMY);
            possibleSitesToTrain = BarrackUtils.findByBarrackType(possibleSitesToTrain, BarrackTypeEnum.KNIGHT);
            List<Site> sitesClosestToEnemyQueen = SortUtils.sortByDistanceToCordinate(possibleSitesToTrain, enemyQueen.coordinate);
            sitesClosestToEnemyQueen.forEach(site -> addSiteToTrain(sitesToTrain, site, gameInfo));
        } else {
            Unit friendQueen = UnitUtils.findQueen(units, OwnerEnum.FRIEND);
            possibleSitesToTrain = BarrackUtils.findByBarrackType(possibleSitesToTrain, BarrackTypeEnum.ARCHER);
            List<Site> sitesClosestToFriendQueen = SortUtils.sortByDistanceToCordinate(possibleSitesToTrain, friendQueen.coordinate);
            sitesClosestToFriendQueen.forEach(site -> addSiteToTrain(sitesToTrain, site, gameInfo));
        }

        return sitesToTrain;
    }

    private void addSiteToTrain(List<Site> sitesToTrain, Site site, GameInfo gameInfo) {
        BarrackTypeEnum barrackType = BarrackTypeEnum.get(site.param2);
        if (gameInfo.gold >= barrackType.cost) {
            sitesToTrain.add(site);
            gameInfo.gold -= barrackType.cost;
        }
    }
}
