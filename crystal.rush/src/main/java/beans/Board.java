package beans;

import java.util.*;

public class Board {
    // Given at startup
    public final int width;
    public final int height;

    // Updated each turn
    public final Team myTeam = new Team();
    public final Team opponentTeam = new Team();
    private Cell[][] cells;
    public int myRadarCooldown;
    public int myTrapCooldown;
    public Map<Integer, Entity> entitiesById;
    public Collection<Coord> myRadarPos;
    public Collection<Coord> myTrapPos;

    public Board(Scanner in) {
        width = in.nextInt();
        height = in.nextInt();
    }

    public void update(Scanner in) {
        // Read new data
        myTeam.readScore(in);
        opponentTeam.readScore(in);
        cells = new Cell[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells[y][x] = new Cell(in);
            }
        }
        int entityCount = in.nextInt();
        myRadarCooldown = in.nextInt();
        myTrapCooldown = in.nextInt();
        entitiesById = new HashMap<>();
        myRadarPos = new ArrayList<>();
        myTrapPos = new ArrayList<>();
        for (int i = 0; i < entityCount; i++) {
            Entity entity = new Entity(in);
            entitiesById.put(entity.id, entity);
            switch (entity.type) {
                case ALLY_ROBOT: myTeam.robots.add(entity);
                case TRAP: myTrapPos.add(entity.pos);
                case RADAR: myRadarPos.add(entity.pos);
                case ENEMY_ROBOT: opponentTeam.robots.add(entity);
            }
        }
    }

    public boolean cellExist(Coord pos) {
        return (pos.x >= 0) && (pos.y >= 0) && (pos.x < width) && (pos.y < height);
    }

    public Cell getCell(Coord pos) {
        return cells[pos.y][pos.x];
    }
}
