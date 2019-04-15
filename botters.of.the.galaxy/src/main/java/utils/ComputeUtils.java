package utils;


import objects.Coordinate;

public class ComputeUtils {

    public static Double computeDistance(Coordinate coordinate1, Coordinate coordinate2) {
        double resX = Math.pow((double) coordinate1.x - (double) coordinate2.x, 2);
        double resY = Math.pow((double) coordinate1.y - (double) coordinate2.y, 2);
        return Math.sqrt(resX + resY);
    }
}