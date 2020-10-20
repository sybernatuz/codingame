package objects;


import enums.TeamEnum;

import java.util.Objects;

public class Zone {

    public int id;
    public int platinumSource;
    public TeamEnum team; // the player who owns this zone (-1 otherwise)
    public int friendPods; // player 0's PODs on this zone
    public int enemyPods; // player 1's PODs on this zone
    public int visible; // 1 if one of your units can see this tile, else 0
    public int platinum; // the amount of Platinum this zone can provide (0 if hidden by fog)

    public Zone() {
    }

    public void update(Zone zone) {
        friendPods = zone.friendPods;
        enemyPods = zone.enemyPods;
        visible = zone.visible;
        platinum = zone.platinum;
        if (TeamEnum.NONE.equals(zone.team) && team != null)
            return;
        team = zone.team;
    }

    @Override
    public String toString() {
        return "Zone{" +
                "id=" + id +
                ", platinumSource=" + platinumSource +
                ", team=" + team +
                ", friendPods=" + friendPods +
                ", enemyPods=" + enemyPods +
                ", visible=" + visible +
                ", platinum=" + platinum +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zone zone = (Zone) o;
        return id == zone.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
