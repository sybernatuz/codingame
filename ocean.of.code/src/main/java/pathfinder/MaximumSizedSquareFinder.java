package pathfinder;

import objects.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MaximumSizedSquareFinder {


    private static final MaximumSizedSquareFinder INSTANCE = new MaximumSizedSquareFinder();

    public static MaximumSizedSquareFinder getInstance() {
        return INSTANCE;
    }


//    public static void main(String[] args) {
//        int[][] grid = {
//                {0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
//                // Additional rows...
//        };
//
//        List<Coordinate> square = findMaximumSquare(grid);
//        assert square.size() == 64;
//        System.out.println("Maximum square size: " + square.size());
//        square.forEach(System.out::println);
//    }

    public List<Coordinate> findMaximumSquare(int[][] grid) {
        List<Coordinate> maxSquare = new ArrayList<>();
        int maxSize = 0;

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == 0) {
                    List<Coordinate> coordinates = new ArrayList<>();
                    int size = 0;

                    while (y + size < grid.length && x + size < grid[y].length) {
                        boolean canExpand = true;

                        List<Coordinate> currentSquare = new ArrayList<>();
                        for (int i = y; i <= y + size; i++) {
                            for (int j = x; j <= x + size; j++) {
                                if (grid[i][j] == 1) {
                                    canExpand = false;
                                    break;
                                }
                                currentSquare.add(new Coordinate(j, i));
                            }
                            if (!canExpand) {
                                break;
                            }
                        }

                        if (canExpand) {
                            size++;
                            coordinates.addAll(currentSquare);
                        } else {
                            break;
                        }
                    }

                    if (size > maxSize) {
                        maxSize = size;
                        maxSquare = coordinates;
                    }
                }
            }
        }

        return maxSquare.stream()
                .distinct()
                .collect(Collectors.toList());
    }
}
