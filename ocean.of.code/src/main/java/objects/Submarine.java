package objects;

import objects.actions.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Submarine {

    public PossibleLocation coordinate;
    public Coordinate coordinateFinal;
    public List<PossibleLocation> possibleLocation = new ArrayList<>();
    public boolean spotted = false;
    public int life = 6;
    public int lifeLastTurn;
    public List<Action> orders = new ArrayList<>();
    public Coordinate coordinateToSilence;

    public void initPossibleLocation() {
        possibleLocation = Grid.getInstance().empty.stream()
                .map(PossibleLocation::new)
                .collect(Collectors.toList());
    }

}
