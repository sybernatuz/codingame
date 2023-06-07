package objects;

import java.util.Scanner;

public class Moto {
    public Team team;
    public Coordinate initialCoordinate;
    public Coordinate coordinate;

    public Moto(Scanner scanner, int playerNumber, int allyNumber) {
        team = Team.fromPlayerNumber(playerNumber, allyNumber);
        initialCoordinate = new Coordinate(scanner.nextInt(), scanner.nextInt());
        coordinate = new Coordinate(scanner.nextInt(), scanner.nextInt());
    }
}
