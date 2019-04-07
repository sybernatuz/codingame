package main.java.compete.platinum_rift_episode_2.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Path implements Cloneable{

    public List<Zone> zones = new ArrayList<>();

    public Path() {
    }

    public Path(Path path) {
        List<Zone> zoneList = new ArrayList<>(path.zones);
        this.zones = zoneList;
    }

    @Override
    public String toString() {
        return "Path{" +
                "zones=" + zones +
                '}';
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
