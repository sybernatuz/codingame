package main.java.compete.ghost_in_the_cell.objects;

import main.java.compete.ghost_in_the_cell.enums.OwnerEnum;

import java.util.Scanner;

public class Troop {

    public int id;
    public OwnerEnum owner;
    public int factoryFromId;
    public int factoryTargetId;
    public int number;
    public int turnsToArrive;

    public Troop(Scanner in, int id) {
        this.id = id;
        owner = OwnerEnum.get(in.nextInt());
        factoryFromId = in.nextInt();
        factoryTargetId = in.nextInt();
        number = in.nextInt();
        turnsToArrive = in.nextInt();
    }
}
