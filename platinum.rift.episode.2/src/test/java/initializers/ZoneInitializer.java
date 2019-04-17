package initializers;

import enums.TeamEnum;
import objects.Zone;


public class ZoneInitializer {

    public static Zone initFriendBase() {
        Zone zone = new Zone();
        zone.id = 1;
        zone.enemyPods = 0;
        zone.friendPods = 1;
        zone.platinum = 0;
        zone.team = TeamEnum.FRIEND;
        return zone;
    }

    public static Zone initFriendZone1() {
        Zone zone = new Zone();
        zone.id = 2;
        zone.enemyPods = 0;
        zone.friendPods = 0;
        zone.platinum = 2;
        zone.team = TeamEnum.FRIEND;
        return zone;
    }

    public static Zone initFriendZone2() {
        Zone zone = new Zone();
        zone.id = 6;
        zone.enemyPods = 0;
        zone.friendPods = 0;
        zone.platinum = 0;
        zone.team = TeamEnum.FRIEND;
        return zone;
    }

    public static Zone initFriendZone3() {
        Zone zone = new Zone();
        zone.id = 7;
        zone.enemyPods = 0;
        zone.friendPods = 2;
        zone.platinum = 2;
        zone.team = TeamEnum.FRIEND;
        return zone;
    }

    public static Zone initNoneZone1() {
        Zone zone = new Zone();
        zone.id = 3;
        zone.enemyPods = 0;
        zone.friendPods = 0;
        zone.platinum = 0;
        zone.team = TeamEnum.NONE;
        return zone;
    }

    public static Zone initNoneZone2() {
        Zone zone = new Zone();
        zone.id = 4;
        zone.enemyPods = 0;
        zone.friendPods = 0;
        zone.platinum = 3;
        zone.team = TeamEnum.NONE;
        return zone;
    }

    public static Zone initEnemyBase() {
        Zone zone = new Zone();
        zone.id = 5;
        zone.enemyPods = 2;
        zone.friendPods = 0;
        zone.platinum = 0;
        zone.team = TeamEnum.ENEMY;
        return zone;
    }


}
