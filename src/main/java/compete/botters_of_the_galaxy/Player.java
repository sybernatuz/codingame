package main.java.compete.botters_of_the_galaxy;

import main.java.compete.botters_of_the_galaxy.enums.HeroEnum;
import main.java.compete.botters_of_the_galaxy.enums.TeamEnum;
import main.java.compete.botters_of_the_galaxy.enums.UnitTypeEnum;
import main.java.compete.botters_of_the_galaxy.objects.Coordinate;
import main.java.compete.botters_of_the_galaxy.objects.Entity;
import main.java.compete.botters_of_the_galaxy.utils.ComputeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

class Player {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int myTeam = in.nextInt();
        int bushAndSpawnPointCount = in.nextInt(); // useful from wood1, represents the number of bushes and the number of places where neutral units can spawn
        for (int i = 0; i < bushAndSpawnPointCount; i++) {
            String entityType = in.next(); // BUSH, from wood1 it can also be SPAWN
            int x = in.nextInt();
            int y = in.nextInt();
            int radius = in.nextInt();
        }
        int itemCount = in.nextInt(); // useful from wood2
        for (int i = 0; i < itemCount; i++) {
            String itemName = in.next(); // contains keywords such as BRONZE, SILVER and BLADE, BOOTS connected by "_" to help you sort easier
            int itemCost = in.nextInt(); // BRONZE items have lowest cost, the most expensive items are LEGENDARY
            int damage = in.nextInt(); // keyword BLADE is present if the most important item stat is damage
            int health = in.nextInt();
            int maxHealth = in.nextInt();
            int mana = in.nextInt();
            int maxMana = in.nextInt();
            int moveSpeed = in.nextInt(); // keyword BOOTS is present if the most important item stat is moveSpeed
            int manaRegeneration = in.nextInt();
            int isPotion = in.nextInt(); // 0 if it's not instantly consumed
        }
        // game loop
        while (true) {
            int gold = in.nextInt();
            int enemyGold = in.nextInt();
            int roundType = in.nextInt(); // a positive value will show the number of heroes that await a command
            int entityCount = in.nextInt();
            List<Entity> entities = new ArrayList<>();
            for (int i = 0; i < entityCount; i++) {
                Entity entity = new Entity(in, myTeam);
                entities.add(entity);
            }
            if (roundType < 0)
                System.out.println(HeroEnum.HULK.toString());
            else
                System.out.println(computeAction(entities));
        }
    }

    private static String computeAction(List<Entity> entities) {
        Optional<Entity> friendHero = entities.stream()
                .filter(entity -> entity.unitType.equals(UnitTypeEnum.HERO))
                .filter(entity -> entity.team.equals(TeamEnum.FRIEND))
                .findFirst();
        Optional<Entity> enemyHero = entities.stream()
                .filter(entity -> entity.unitType.equals(UnitTypeEnum.HERO))
                .filter(entity -> entity.team.equals(TeamEnum.ENEMY))
                .findFirst();
        if (!friendHero.isPresent() || !enemyHero.isPresent())
            return "WAIT";

        // https://math.stackexchange.com/questions/175896/finding-a-point-along-a-line-a-certain-distance-away-from-another-point
        double distance = ComputeUtils.computeDistance(enemyHero.get().coordinate, friendHero.get().coordinate);
        double t = friendHero.get().attackRange / distance;
        double x = (1 - t) * enemyHero.get().coordinate.x + t * friendHero.get().coordinate.x;
        double y = (1 - t) * enemyHero.get().coordinate.y + t * friendHero.get().coordinate.y;
        Coordinate maxRangeToEnemyHero = new Coordinate((int) x, (int) y);

        Optional<Entity> friendTower = entities.stream()
                .filter(entity -> entity.unitType.equals(UnitTypeEnum.TOWER))
                .filter(entity -> entity.team.equals(TeamEnum.FRIEND))
                .findFirst();

        if (!friendTower.isPresent())
            return "WAIT";

        double distanceToTower = ComputeUtils.computeDistance(enemyHero.get().coordinate, friendTower.get().coordinate);
//        if (distanceToTower >= friendTower.get().attackRange)
//            return "MOVE " + friendTower.get().coordinate.x + " " + friendTower.get().coordinate.y;

        return "ATTACK_NEAREST " + UnitTypeEnum.HERO.toString();
//        StringBuilder action = new StringBuilder();
//        return action.append("MOVE_ATTACK")
//                .append(" ")
//                .append(friendTower.get().coordinate.x)
//                .append(" ")
//                .append(friendTower.get().coordinate.y)
//                .append(" ")
//                .append(enemyHero.get().unitId)
//                .toString();

//        StringBuilder action = new StringBuilder();
//        return action.append("MOVE_ATTACK")
//                .append(" ")
//                .append(maxRangeToEnemyHero.x)
//                .append(" ")
//                .append(maxRangeToEnemyHero.y)
//                .append(" ")
//                .append(enemyHero.get().unitId)
//                .toString();
    }
}
