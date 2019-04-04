package main.java.compete.code_royal.strategies;

import main.java.compete.code_royal.enums.BarrackTypeEnum;
import main.java.compete.code_royal.enums.OwnerEnum;
import main.java.compete.code_royal.enums.UnitTypeEnum;
import main.java.compete.code_royal.objects.GameInfo;
import main.java.compete.code_royal.objects.Site;
import main.java.compete.code_royal.objects.Unit;
import main.java.compete.code_royal.utils.SortUtils;
import main.java.compete.code_royal.utils.site.BarrackUtils;
import main.java.compete.code_royal.utils.site.TowerUtils;
import main.java.compete.code_royal.utils.site.UnitUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TrainStrategy {

    public List<Site> computeTrain(List<Site> sites, GameInfo gameInfo, List<Unit> units) {
        List<Site> possibleSitesToTrain = BarrackUtils.findWaitingBarrack(sites, OwnerEnum.FRIEND);
        return isKnightStrategy(possibleSitesToTrain, units, gameInfo)
                .orElse(isGiantStrategy(possibleSitesToTrain, units, gameInfo, sites)
                .orElseGet(Collections::emptyList));
    }

    private Optional<List<Site>> isKnightStrategy(List<Site> possibleSitesToTrain, List<Unit> units, GameInfo gameInfo) {
        List<Site> possibleKnightSitesToTrain = BarrackUtils.findByBarrackType(possibleSitesToTrain, BarrackTypeEnum.KNIGHT);
        List<Unit> friendKngiths = UnitUtils.findByUnitType(units, OwnerEnum.FRIEND, UnitTypeEnum.KNIGHT);
        boolean isEnoughKnights = friendKngiths.size() >= 1;
        if (possibleKnightSitesToTrain.isEmpty() || isEnoughKnights)
            return Optional.empty();

        List<Site> sitesToTrain = new ArrayList<>();
        Unit enemyQueen = UnitUtils.findQueen(units, OwnerEnum.ENEMY);
        possibleSitesToTrain = BarrackUtils.findByBarrackType(possibleSitesToTrain, BarrackTypeEnum.KNIGHT);
        List<Site> sitesClosestToEnemyQueen = SortUtils.sortByDistanceToCordinate(possibleSitesToTrain, enemyQueen.coordinate);
        sitesClosestToEnemyQueen.forEach(site -> addSiteToTrain(sitesToTrain, site, gameInfo));
        return Optional.of(sitesToTrain);
    }

    private Optional<List<Site>> isGiantStrategy(List<Site> possibleSitesToTrain, List<Unit> units, GameInfo gameInfo, List<Site> sites) {
        List<Site> possibleGiantSitesToTrain = BarrackUtils.findByBarrackType(possibleSitesToTrain, BarrackTypeEnum.GIANT);
        List<Site> enemyTowers = TowerUtils.findByOwner(sites, OwnerEnum.ENEMY);
        List<Unit> friendGiants = UnitUtils.findByUnitType(units, OwnerEnum.FRIEND, UnitTypeEnum.GIANT);
        boolean isEnoughGiants = friendGiants.size() >= 1;
        boolean isEnemyTowersToDestroy = enemyTowers.size() >= 2;
        if (possibleGiantSitesToTrain.isEmpty() || isEnoughGiants || !isEnemyTowersToDestroy)
            return Optional.empty();

        List<Site> sitesToTrain = new ArrayList<>();
        Unit enemyQueen = UnitUtils.findQueen(units, OwnerEnum.ENEMY);
        possibleSitesToTrain = BarrackUtils.findByBarrackType(possibleSitesToTrain, BarrackTypeEnum.GIANT);
        List<Site> sitesClosestToEnemyQueen = SortUtils.sortByDistanceToCordinate(possibleSitesToTrain, enemyQueen.coordinate);
        sitesClosestToEnemyQueen.forEach(site -> addSiteToTrain(sitesToTrain, site, gameInfo));
        return Optional.of(sitesToTrain);
    }

    private void addSiteToTrain(List<Site> sitesToTrain, Site site, GameInfo gameInfo) {
        BarrackTypeEnum barrackType = BarrackTypeEnum.get(site.param2);
        if (gameInfo.gold >= barrackType.cost) {
            sitesToTrain.add(site);
            gameInfo.gold -= barrackType.cost;
        }
    }
}
