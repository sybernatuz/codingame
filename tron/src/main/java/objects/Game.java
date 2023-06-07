package objects;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {
    public static int MAX_X = 30;
    public static int MAX_Y = 20;
    public static int MIN = 0;
    public List<Moto> motos;
    public ZoneType[][] grid = new ZoneType[MAX_Y][MAX_X];


    private static final Game INSTANCE = new Game();

    public static Game getInstance() {
        return INSTANCE;
    }

    public Game() {
        for (int y = 0; y < MAX_Y; y++) {
            for (int x = 0; x < MAX_X; x++) {
                grid[y][x] = ZoneType.FREE;
            }
        }
    }

    public void update(Scanner in) {
        if (motos != null) {
            for (Moto moto : motos) {
                grid[moto.coordinate.y][moto.coordinate.x] = ZoneType.BLOCKED;
            }
        }

        int playersNumber = in.nextInt();
        int myNumber = in.nextInt();
        motos = IntStream.range(0, playersNumber)
                .mapToObj(playerNumber -> new Moto(in, playerNumber, myNumber))
                .peek(this::updateGrid)
                .collect(Collectors.toList());
    }

    private void updateGrid(Moto moto) {
        ZoneType motoTeam = Team.ALLY.equals(moto.team) ? ZoneType.MY_MOTO : ZoneType.ENEMY_MOTO;
        grid[moto.coordinate.y][moto.coordinate.x] = motoTeam;
    }

    public Moto getMyMoto() {
        return motos.stream()
                .filter(moto -> Team.ALLY.equals(moto.team))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
