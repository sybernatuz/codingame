package beans;

import beans.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Team {
    public int score;
    public Collection<Entity> robots;

    public void readScore(Scanner in) {
        score = in.nextInt();
        robots = new ArrayList<>();
    }
}
