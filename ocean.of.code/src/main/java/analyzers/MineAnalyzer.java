package analyzers;

import objects.PossibleLocation;
import objects.Submarine;
import objects.actions.Action;
import objects.actions.Type;

import java.util.List;
import java.util.stream.Collectors;

class MineAnalyzer {

    private static final MineAnalyzer INSTANCE = new MineAnalyzer();

    public static MineAnalyzer getInstance() {
        return INSTANCE;
    }

    public void filterByTriggeredMine(Submarine submarine, Action trigger, Submarine otherSubmarine) {
        if (submarine.life == submarine.lifeLastTurn) {
            submarine.possibleLocation = mineDidNotHit(submarine, trigger);
            return;
        }

        boolean hasTorpedo = otherSubmarine.orders.stream()
                .anyMatch(action -> Type.TORPEDO.equals(action.type));
        if (hasTorpedo)
            return;

        int lifeLost = submarine.lifeLastTurn - submarine.life;
        boolean hasSurface = submarine.orders.stream()
                .anyMatch(action -> Type.SURFACE.equals(action.type));
        if (hasSurface)
            lifeLost--;

        if (lifeLost == 0)
            return;

        if (lifeLost == 2) {
            submarine.possibleLocation = mineDidHitExactLocation(submarine, trigger);
            return;
        }
        submarine.possibleLocation = mineDidHit(submarine, trigger);
    }

    private List<PossibleLocation> mineDidNotHit(Submarine submarine, Action trigger) {
        return submarine.possibleLocation.stream()
                .filter(possibleLocation -> !trigger.coordinate.isNeighbor(possibleLocation))
                .collect(Collectors.toList());
    }

    private List<PossibleLocation> mineDidHitExactLocation(Submarine submarine, Action trigger) {
        return submarine.possibleLocation.stream()
                .filter(trigger.coordinate::equals)
                .collect(Collectors.toList());
    }

    private List<PossibleLocation> mineDidHit(Submarine submarine, Action trigger) {
        return submarine.possibleLocation.stream()
                .filter(trigger.coordinate::isNeighbor)
                .collect(Collectors.toList());
    }
}
