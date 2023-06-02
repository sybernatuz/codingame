package objects;

import java.util.*;

public class Grid {

    private static final Grid INSTANCE = new Grid();

    public int width;
    public int height;
    public List<Coordinate> blocked = new ArrayList<>();
    public List<Coordinate> empty = new ArrayList<>();
    public List<Coordinate> mined = new ArrayList<>();
    public int[][] asArray;

    public static Grid getInstance() {
        return INSTANCE;
    }

    public void sized(Scanner in) {
        width = in.nextInt();
        height = in.nextInt();
        asArray = new int[height][width];
    }

    public void initialize(String line, int y) {
        for (int i = 0; i < line.length(); i++) {
            Coordinate coordinate = new Coordinate(i, y);
            switch (line.charAt(i)) {
                case 'x':
                    blocked.add(coordinate);
                    asArray[y][i] = 1;
                    break;
                case '.':
                    empty.add(coordinate);
                    asArray[y][i] = 0;
                    break;
            }
        }
    }
}
