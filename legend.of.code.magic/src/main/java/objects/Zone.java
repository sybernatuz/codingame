package objects;


import enums.TeamEnum;

import java.util.Objects;
import java.util.Scanner;

public class Zone {

    public int zoneId;
    public int platinumSource;
    public TeamEnum team; // the player who owns this zone (-1 otherwise)
    public int friendPods; // player 0's PODs on this zone
    public int enemyPods; // player 1's PODs on this zone
    public int visible; // 1 if one of your units can see this tile, else 0
    public int platinum; // the amount of Platinum this zone can provide (0 if hidden by fog)
    public boolean isVisited;

    public Zone(Scanner in, boolean update, int friendTeam) {
        if (!update) {
            zoneId = in.nextInt();
            platinumSource = in.nextInt();
        } else {
            int ownerId = in.nextInt();
            team = TeamEnum.get(ownerId, friendTeam); // the player who owns this zone (-1 otherwise)
            if (friendTeam == 0) {
                friendPods = in.nextInt(); // player 0's PODs on this zone
                enemyPods = in.nextInt(); // player 1's PODs on this zone
            } else {
                enemyPods = in.nextInt(); // player 1's PODs on this zone
                friendPods = in.nextInt(); // player 0's PODs on this zone
            }
            visible = in.nextInt(); // 1 if one of your units can see this tile, else 0
            platinum = in.nextInt(); // the amount of Platinum this zone can provide (0 if hidden by fog)
        }
        isVisited = false;
    }

    public void update(Zone zone) {
        team = zone.team;
        friendPods = zone.friendPods;
        enemyPods = zone.enemyPods;
        visible = zone.visible;
        platinum = zone.platinum;
    }

    @Override
    public String toString() {
        return "Zone{" +
                "zoneId=" + zoneId +
                ", platinumSource=" + platinumSource +
                ", team=" + team +
                ", friendPods=" + friendPods +
                ", enemyPods=" + enemyPods +
                ", visible=" + visible +
                ", platinum=" + platinum +
                ", isVisited=" + isVisited +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zone zone = (Zone) o;
        return zoneId == zone.zoneId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoneId);
    }
}
