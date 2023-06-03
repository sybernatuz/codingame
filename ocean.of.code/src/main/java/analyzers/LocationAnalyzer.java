package analyzers;

import objects.PossibleLocation;
import objects.Submarine;
import objects.actions.Action;

import java.util.List;

public class LocationAnalyzer {

    private static final LocationAnalyzer INSTANCE = new LocationAnalyzer();

    public static LocationAnalyzer getInstance() {
        return INSTANCE;
    }

    public void process(Submarine submarine, Submarine otherSubmarine) {
        for (Action order : otherSubmarine.orders) {
            switch (order.type) {
                case TRIGGER:
                    MineAnalyzer.getInstance().filterByTriggeredMine(submarine, order, otherSubmarine);
                    break;
                case SONAR:
                    SonarAnalyzer.getInstance().filterBySonar(submarine, order);
                    break;
            }
        }

        for (Action action : submarine.orders) {
            switch (action.type) {
                case SILENCE:
                    SilenceAnalyzer.getInstance().addSilenceRangedZones(submarine);
                    break;
                case SURFACE:
                    SurfaceAnalyzer.getInstance().filterBySector(submarine, action);
                    break;
                case MOVE:
                    MoveAnalyzer.getInstance().filterByMove(submarine, action);
                    break;
                case TORPEDO:
                    TorpedoAnalyzer.getInstance().filterByTorpedo(submarine, action);
                    break;
            }
        };
        setIfFound(submarine);

        if (submarine.coordinate != null)
            submarine.spotted = true;
    }

    private void setIfFound(Submarine submarine) {
        List<PossibleLocation> distinct = submarine.getDistinctPossibleLocation();
        if (distinct.size() == 1) {
            submarine.coordinate = distinct.get(0);
        } else if (submarine.possibleLocation.size() == 1) {
            submarine.coordinate = submarine.possibleLocation.get(0);
        }
    }
}
