package main.java.compete.platinum_rift_episode_2.objects;

import main.java.compete.platinum_rift_episode_2.utils.ZoneUtils;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Link {

    public Zone zone1;
    public Zone zone2;

    public Link(Scanner in, List<Zone> zones) {
        int zone1Id = in.nextInt();
        int zone2Id = in.nextInt();
        zone1 = ZoneUtils.findById(zones, zone1Id);
        zone2 = ZoneUtils.findById(zones, zone2Id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(zone1, link.zone1) &&
                Objects.equals(zone2, link.zone2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zone1, zone2);
    }
}
