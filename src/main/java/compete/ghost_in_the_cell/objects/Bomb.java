package main.java.compete.ghost_in_the_cell.objects;

import main.java.compete.ghost_in_the_cell.enums.OwnerEnum;

import java.util.Scanner;

public class Bomb {

    public int id;
    public OwnerEnum owner;
    public int factoryFromId;
    public int factoryTargetId;
    public int turnsToArrive;
    public int arg5;

    public Bomb(Scanner in, int id) {
        this.id = id;
        owner = OwnerEnum.get(in.nextInt());
        factoryFromId = in.nextInt();
        factoryTargetId = in.nextInt();
        turnsToArrive = in.nextInt();
        arg5 = in.nextInt();
    }
}
