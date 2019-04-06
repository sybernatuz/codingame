package main.java.compete.botters_of_the_galaxy.utils;

import main.java.compete.botters_of_the_galaxy.objects.Coordinate;

public class ComputeUtils {

    public static Double computeDistance(Coordinate coordinate1, Coordinate coordinate2) {
        double resX = Math.pow((double) coordinate1.x - (double) coordinate2.x, 2);
        double resY = Math.pow((double) coordinate1.y - (double) coordinate2.y, 2);
        return Math.sqrt(resX + resY);
    }
}