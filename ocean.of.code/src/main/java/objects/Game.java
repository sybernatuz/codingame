package objects;

import objects.actions.*;

import java.util.*;
import java.util.stream.Collectors;

public class Game {

    private static final Game INSTANCE = new Game();

    public int torpedoCooldown;
    public int sonarCooldown;
    public int silenceCooldown;
    public int mineCooldown;
    public String sonarResult;
    public Submarine mySubmarine = new Submarine();
    public Submarine enemySubmarine = new Submarine();
    public int step = 1;
    public int turn = 0;
    public List<Coordinate> bestPath = new ArrayList<>();

    public static Game getInstance() {
        return INSTANCE;
    }

    public void update(Scanner in) {
        mySubmarine.coordinateFinal = new Coordinate(in.nextInt(), in.nextInt());
        mySubmarine.lifeLastTurn = mySubmarine.life;
        mySubmarine.life = in.nextInt();
        if (mySubmarine.life < mySubmarine.lifeLastTurn) {
            mySubmarine.spotted = true;
        }
        enemySubmarine.lifeLastTurn = enemySubmarine.life;
        enemySubmarine.life = in.nextInt();

        torpedoCooldown = in.nextInt();
        sonarCooldown = in.nextInt();
        silenceCooldown = in.nextInt();
        mineCooldown = in.nextInt();
        sonarResult = in.next();

        Grid.getInstance().mined.forEach(mine -> mine.active = true);
    }

    public void updateOrder(Scanner in) {
        String enemyOrder = in.nextLine();
        System.err.println(enemyOrder);
        enemySubmarine.orders = findActionFromString(enemyOrder);
    }

    public List<Action> findActionFromString(String actionsLine) {
        if (Objects.equals(actionsLine, "NA"))
            return Collections.emptyList();

        return Arrays.stream(actionsLine.split("\\|"))
                .map(String::trim)
                .map(lastAction -> {
                    if (lastAction.startsWith(Type.SILENCE.name())) {
                        Action action = new Action();
                        action.type = Type.SILENCE;
                        return action;
                    }
                    if (lastAction.startsWith(Type.SURFACE.name())) {
                        Action action = new Action();
                        action.type = Type.SURFACE;
                        action.sector = Integer.valueOf(lastAction.split(" ")[1]);
                        return action;
                    }
                    if (lastAction.startsWith(Type.MINE.name())) {
                        Action action = new Action();
                        action.type = Type.MINE;
                        return action;
                    }
                    if (lastAction.startsWith(Type.MOVE.name())) {
                        String direction = lastAction.split(" ")[1];
                        Action action = new Action();
                        action.type = Type.MOVE;
                        action.direction = Direction.valueOf(direction);
                        return action;
                    }
                    if (lastAction.startsWith(Type.SONAR.name())) {
                        Action action = new Action();
                        action.type = Type.SONAR;
                        action.sector = Integer.parseInt(lastAction.replace(Type.SONAR.name(), "").trim());
                        return action;
                    }
                    String[] values = lastAction.split(" ");

                    Action action = new Action();
                    action.type = Type.valueOf(values[0]);
                    action.coordinate = new Coordinate(Integer.parseInt(values[1]), Integer.parseInt(values[2]));
                    return action;
                })
                .collect(Collectors.toList());
    }

}
