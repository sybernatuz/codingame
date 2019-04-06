package main.java.compete.platinum_rift_episode_2.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Zone {

    public List<Zone> linkedZones;
    public int zoneId;
    public int platinumSource;
    int ownerId; // the player who owns this zone (-1 otherwise)
    int podsP0; // player 0's PODs on this zone
    int podsP1; // player 1's PODs on this zone
    int visible; // 1 if one of your units can see this tile, else 0
    int platinum; // the amount of Platinum this zone can provide (0 if hidden by fog)

    public Zone(Scanner in, int i) {
        zoneId = in.nextInt();
        platinumSource = in.nextInt();
        linkedZones = new ArrayList<>();
    }

    public void update(Scanner in) {
        ownerId = in.nextInt(); // the player who owns this zone (-1 otherwise)
        podsP0 = in.nextInt(); // player 0's PODs on this zone
        podsP1 = in.nextInt(); // player 1's PODs on this zone
        visible = in.nextInt(); // 1 if one of your units can see this tile, else 0
        platinum = in.nextInt(); // the amount of Platinum this zone can provide (0 if hidden by fog)
    }

    @Override
    public String toString() {
        return "Zone{" +
                "linkedZones=" + linkedZones +
                ", zoneId=" + zoneId +
                ", platinumSource=" + platinumSource +
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
