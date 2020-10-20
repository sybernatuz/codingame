package builder;

import enums.TeamEnum;
import objects.Zone;

import java.util.Scanner;

public class ZoneBuilder {

    private final Zone zone;

    private ZoneBuilder() {
        zone = new Zone();
    }

    public static ZoneBuilder init(Scanner in) {
        ZoneBuilder zoneBuilder = new ZoneBuilder();
        zoneBuilder.zone.id = in.nextInt();
        return zoneBuilder;
    }

    public Zone build() {
        return zone;
    }

    public ZoneBuilder withPlatinumSource(Scanner in) {
        zone.platinumSource = in.nextInt();
        return this;
    }

    public ZoneBuilder update(Scanner in, int friendTeam) {
        int ownerId = in.nextInt(); // the player who owns this zone (-1 otherwise)
        zone.team = TeamEnum.get(ownerId, friendTeam);
        if (friendTeam == 0) {
            zone.friendPods = in.nextInt(); // player 0's PODs on this zone
            zone.enemyPods = in.nextInt(); // player 1's PODs on this zone
        } else {
            zone.enemyPods = in.nextInt(); // player 1's PODs on this zone
            zone.friendPods = in.nextInt(); // player 0's PODs on this zone
        }
        zone.visible = in.nextInt(); // 1 if one of your units can see this tile, else 0
        zone.platinum = in.nextInt(); // the amount of Platinum this zone can provide (0 if hidden by fog)
        return this;
    }
}
