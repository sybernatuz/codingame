package main.java.compete.platinum_rift_episode_2.objects;

import java.util.ArrayList;
import java.util.List;

public class Path implements Cloneable{

    public List<Zone> zones = new ArrayList<>();

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
