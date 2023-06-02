package analyzers;

import objects.Submarine;
import objects.actions.Type;

public class LocationAnalyzer {

    private static final LocationAnalyzer INSTANCE = new LocationAnalyzer();

    public static LocationAnalyzer getInstance() {
        return INSTANCE;
    }

    public void process(Submarine submarine, Submarine otherSubmarine) {
        otherSubmarine.orders.stream()
                .filter(action -> Type.TRIGGER.equals(action.type))
                .findFirst()
                .ifPresent(action -> MineAnalyzer.getInstance().filterByTriggeredMine(submarine, action, otherSubmarine));

        submarine.orders.forEach(action -> {
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
        });
        if (submarine.possibleLocation.size() == 1)
            submarine.coordinate = submarine.possibleLocation.get(0);
    }
}
