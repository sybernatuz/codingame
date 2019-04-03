package main.java.compete.code_royal;

import main.java.compete.code_royal.enums.OwnerEnum;
import main.java.compete.code_royal.enums.StructureTypeEnum;
import main.java.compete.code_royal.objects.*;
import main.java.compete.code_royal.strategies.build.BuildStrategy;
import main.java.compete.code_royal.strategies.MoveStrategy;
import main.java.compete.code_royal.strategies.TrainStrategy;
import main.java.compete.code_royal.utils.site.FindUtils;
import main.java.compete.code_royal.utils.site.UnitUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {

    private static final BuildStrategy buildStrategy = new BuildStrategy();
    private static final TrainStrategy trainStrategy = new TrainStrategy();
    private static final MoveStrategy moveStrategy = new MoveStrategy();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        List<Site> sites = new ArrayList<>();
        GameInfo gameInfo = new GameInfo();
        int numSites = in.nextInt();
        for (int i = 0; i < numSites; i++) {
            sites.add(new Site(in));
        }

        // game loop
        while (true) {
            List<Unit> units = new ArrayList<>();
            gameInfo.gold = in.nextInt();
            int touchedSite = in.nextInt(); // -1 if none
            for (int i = 0; i < numSites; i++) {
                int siteId = in.nextInt();
                FindUtils.findSiteById(sites, siteId).updateSite(in);
            }
            int numUnits = in.nextInt();
            for (int i = 0; i < numUnits; i++) {
                units.add(new Unit(in));
            }

            Unit queen = UnitUtils.findQueen(units, OwnerEnum.FRIEND);
            if (gameInfo.start == null)
                gameInfo.start = new Coordinate(queen.coordinate);
            if (gameInfo.isFirstAction) {
                Site site = FindUtils.findClosestSiteToOpposedExtremity(sites, gameInfo);
                if (gameInfo.isFirstAction && touchedSite == site.siteId)
                    gameInfo.isFirstAction = false;
            }

            System.out.println(build(queen, sites, gameInfo, touchedSite));
            System.out.println(train(sites, gameInfo, units));
        }
    }

    private static String train(List<Site> sites, GameInfo gameInfo, List<Unit> units) {
        List<Site> sitesToTrain = trainStrategy.computeTrain(sites, gameInfo, units);
        return trainUnit(sitesToTrain);
    }

    private static String build(Unit queen, List<Site> sites, GameInfo gameInfo, int touchedSite) {
        Build build = buildStrategy.computeBuild(queen, sites, gameInfo);
        if (build == null || gameInfo.isFirstAction)
            return move(gameInfo, sites);
        return buildSite(build);
    }

    private static String trainUnit(List<Site> sitesToTrain) {
        StringBuilder train = new StringBuilder();
        train.append("TRAIN");
        sitesToTrain.forEach(site -> train.append(" ").append(site.siteId));
        return train.toString();
    }

    private static String move(GameInfo gameInfo, List<Site> sites) {
        StringBuilder train = new StringBuilder();
        Coordinate coordinate = moveStrategy.computeMove(gameInfo, sites);
        train.append("MOVE")
                .append(" ")
                .append(coordinate.x)
                .append(" ")
                .append(coordinate.y);

        return train.toString();
    }

    private static String buildSite(Build build) {
        StringBuilder buildAction = new StringBuilder();
        buildAction.append("BUILD")
                .append(" ")
                .append(build.site.siteId)
                .append(" ");
        if (build.structureType.equals(StructureTypeEnum.BARRACK)) {
            buildAction.append("BARRACKS-")
                    .append(build.barrackType.toString());
        } else
            buildAction.append(build.structureType.toString());
        return buildAction.toString();
    }
}