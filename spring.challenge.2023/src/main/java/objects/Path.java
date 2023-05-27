package objects;

import java.util.ArrayList;
import java.util.List;

public class Path implements Cloneable{

    public List<Zone> zones = new ArrayList<>();

    public Path() {
    }

    public Path(Path path) {
        this.zones = new ArrayList<>(path.zones);
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
