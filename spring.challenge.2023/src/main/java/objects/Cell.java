package objects;

import java.util.Scanner;

public class Cell {

    public int index;
    public CellType type; // 0 for empty, 1 for eggs, 2 for crystal
    public int resources; // the initial amount of eggs/crystals on this cell
    public int neigh0; // the index of the neighbouring cell for each direction
    public int neigh1;
    public int neigh2;
    public int neigh3;
    public int neigh4;
    public int neigh5;
    public int myAnts = 0;
    public int oppAnts = 0;

    public Cell(Scanner in, int index) {
        this.index = index;
        this.type = CellType.fromValue(in.nextInt());
        this.resources = in.nextInt();
        this.neigh0 = in.nextInt();
        this.neigh1 = in.nextInt();
        this.neigh2 = in.nextInt();
        this.neigh3 = in.nextInt();
        this.neigh4 = in.nextInt();
        this.neigh5 = in.nextInt();
    }

    public void update(Scanner in) {
        resources = in.nextInt(); // the current amount of eggs/crystals on this cell
        myAnts = in.nextInt(); // the amount of your ants on this cell
        oppAnts = in.nextInt(); // the amount of opponent ants on this cell
    }
}
