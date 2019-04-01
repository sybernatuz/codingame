package main.java.compete.code_royal.utils;

import main.java.compete.code_royal.objects.Coordinate;

public class ComputeUtils {

    public static Double computeDistance(Coordinate coordinate1, Coordinate coordinate2) {
        double resX = Math.pow((double) coordinate1.x - (double) coordinate2.x, 2);
        double resY = Math.pow((double) coordinate1.y - (double) coordinate2.y, 2);
        return Math.sqrt(resX + resY);
    }

    public static int computeTurnToReachCoordinate(Coordinate coordinate1, Coordinate coordinate2, int speed) {
        Double distance = computeDistance(coordinate1, coordinate2);
        return (int) (distance / speed);
    }
}