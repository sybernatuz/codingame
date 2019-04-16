package builder;

import enums.TeamEnum;
import objects.Zone;

import java.util.Scanner;

public class ZoneBuilder {

    private Zone zone;

    private ZoneBuilder() {
        zone = new Zone();
    }

    public static ZoneBuilder init(Scanner in) {
        ZoneBuilder zoneBuilder = new ZoneBuilder();
        zoneBuilder.zone.id = in.nextInt();
        return zoneBuilder;
    }

    public Zone build() {
        Zone zoneBuilt = zone;
        zone = new Zone();
        return zoneBuilt;
    }

    public ZoneBuilder create(Scanner in) {
        zone.platinumSource = in.nextInt();
        return this;
    }

    public ZoneBuilder update(Scanner in, int friendTeam) {
        int ownerId = in.nextInt();
        zone.team = TeamEnum.get(ownerId, friendTeam); // the player who owns this zone (-1 otherwise)
        if (friendTeam == 0) {
            zone.friendPods = in.nextInt(); // player 0's PODs on this zone
            zone.enemyPods = in.nextInt(); // player 1's PODs on this zone
        } else {
            zone.enemyPods = in.nextInt(); // player 1's PODs on this zone
            zone.friendPods = in.nextInt(); // player 0's PODs on this zone
        }
        zone.visible = in.nextInt(); // 1 if one of your units can see this tile, else 0
        zone.platinum = in.nextInt();
        return this;
    }
}
