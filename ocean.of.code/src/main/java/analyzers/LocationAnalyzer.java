package analyzers;

import objects.Submarine;
import objects.actions.Action;

public class LocationAnalyzer {

    private static final LocationAnalyzer INSTANCE = new LocationAnalyzer();

    public static LocationAnalyzer getInstance() {
        return INSTANCE;
    }

    public void process(Submarine submarine) {
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
        spotted(submarine);
    }

    // For performance
    public void processByOtherSubmarineActions(Submarine submarine, Submarine otherSubmarine) {
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
        spotted(submarine);
    }

    private void spotted(Submarine submarine) {
        if (submarine.possibleLocation.size() == 1) {
            submarine.coordinate = submarine.possibleLocation.get(0);
        }
        if (submarine.coordinate != null)
            submarine.spotted = true;
    }
}
