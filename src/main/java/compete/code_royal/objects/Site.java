package main.java.compete.code_royal.objects;


import main.java.compete.code_royal.enums.OwnerEnum;
import main.java.compete.code_royal.enums.StructureTypeEnum;

import java.util.Scanner;

public class Site {

    public int siteId;
    public Coordinate coordinate;
    public int radius;
    public int ignore1; // used in future leagues
    public int ignore2; // used in future leagues
    public StructureTypeEnum structureType; // -1 = No structure, 2 = Barracks
    public OwnerEnum owner; // -1 = No structure, 0 = Friendly, 1 = Enemy
    public int param1;
    public int param2;

    public Site(Scanner in) {
        siteId = in.nextInt();
        Coordinate coordinate = new Coordinate();
        coordinate.x = in.nextInt();
        coordinate.y = in.nextInt();
        this.coordinate = coordinate;
        radius = in.nextInt();
    }

    public void updateSite(Scanner in) {
        ignore1 = in.nextInt(); // used in future leagues
        ignore2 = in.nextInt(); // used in future leagues
        structureType = StructureTypeEnum.get(in.nextInt()); // -1 = No structure, 2 = Barracks
        owner = OwnerEnum.get(in.nextInt()); // -1 = No structure, 0 = Friendly, 1 = Enemy
        param1 = in.nextInt();
        param2 = in.nextInt();
    }

    @Override
    public String toString() {
        return "Site{" + "siteId=" + siteId + ", coordinate=" + coordinate + ", radius=" + radius + ", ignore1=" + ignore1 + ", ignore2=" + ignore2 + ", structureType=" + structureType + ", owner=" + owner + ", param1=" + param1 + ", param2=" + param2 + '}';
    }
}
