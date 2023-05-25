package objects;

import enums.HeroEnum;

import java.util.ArrayList;
import java.util.List;

public class Hero {

    public Entity entity;
    public HeroEnum heroEnum;
    public List<Item> items = new ArrayList<>();

    public Hero(HeroEnum heroEnum) {
        this.heroEnum = heroEnum;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "entity=" + entity +
                ", heroEnum=" + heroEnum +
                ", items=" + items +
                '}';
    }
}
