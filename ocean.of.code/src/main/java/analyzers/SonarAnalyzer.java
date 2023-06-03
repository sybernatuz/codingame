package analyzers;

import objects.Game;
import objects.PossibleLocation;
import objects.Submarine;
import objects.actions.Action;

import java.util.List;
import java.util.stream.Collectors;

public class SonarAnalyzer {

    private static final SonarAnalyzer INSTANCE = new SonarAnalyzer();

    public static SonarAnalyzer getInstance() {
        return INSTANCE;
    }

    public void filterBySonar(Submarine submarine, Action sonar) {
        if (submarine.coordinateFinal != null) {
            if (submarine.coordinateFinal.computeSector() == sonar.sector) {
                submarine.possibleLocation = sameSector(submarine, sonar);
            } else {
                submarine.possibleLocation = differentSector(submarine, sonar);
            }
        } else {
            if ("NA".equals(Game.getInstance().sonarResult))
                return;

            if (Game.getInstance().sonarResult.equals("Y")) {
                submarine.possibleLocation = sameSector(submarine, sonar);
            } else {
                submarine.possibleLocation = differentSector(submarine, sonar);
            }
        }
    }

    private List<PossibleLocation> sameSector(Submarine submarine, Action sonar) {
        return submarine.possibleLocation.stream()
                .filter(coordinate -> coordinate.computeSector() == sonar.sector)
                .collect(Collectors.toList());
    }

    private List<PossibleLocation> differentSector(Submarine submarine, Action sonar) {
        return submarine.possibleLocation.stream()
                .filter(coordinate -> coordinate.computeSector() != sonar.sector)
                .collect(Collectors.toList());
    }
}
