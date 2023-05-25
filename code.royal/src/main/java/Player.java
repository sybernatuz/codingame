import enums.OwnerEnum;
import enums.StructureTypeEnum;
import objects.Build;
import objects.Coordinate;
import objects.GameInfo;
import objects.Site;
import objects.Unit;
import strategies.MoveStrategy;
import strategies.TrainStrategy;
import strategies.build.BuildStrategy;
import utils.site.FindUtils;
import utils.site.UnitUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
            gameInfo.gold = in.nextInt();
            int touchedSite = in.nextInt(); // -1 if none
            for (int i = 0; i < numSites; i++) {
                int siteId = in.nextInt();
                FindUtils.findSiteById(sites, siteId).updateSite(in);
            }
            int numUnits = in.nextInt();
            List<Unit> units = IntStream.range(0, numUnits)
                    .mapToObj(value -> new Unit(in))
                    .collect(Collectors.toList());

            Unit queen = UnitUtils.findQueen(units, OwnerEnum.FRIEND);
            if (gameInfo.start == null) {
                gameInfo.start = new Coordinate(queen.coordinate);
                Coordinate startExtremity = FindUtils.findTheClosestCoordinate(gameInfo.start, gameInfo.extremities);
                gameInfo.opposedY = gameInfo.extremities.stream()
                        .filter(coordinate -> coordinate.x == startExtremity.x && coordinate.y != startExtremity.y)
                        .findFirst()
                        .orElse(null);
            }
            System.out.println(build(queen, sites, gameInfo, units));
            System.out.println(train(sites, gameInfo, units));
        }
    }

    private static String train(List<Site> sites, GameInfo gameInfo, List<Unit> units) {
        List<Site> sitesToTrain = trainStrategy.computeTrain(sites, gameInfo, units);
        return trainUnit(sitesToTrain);
    }

    private static String build(Unit queen, List<Site> sites, GameInfo gameInfo, List<Unit> units) {
        Build build = buildStrategy.computeBuild(queen, sites, gameInfo, units);
        if (build == null || build.site == null)
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