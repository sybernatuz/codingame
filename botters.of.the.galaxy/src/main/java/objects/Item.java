package objects;

import enums.ItemRarityEnum;

import java.util.Scanner;

public class Item {
    public String itemName; // contains keywords such as BRONZE, SILVER and BLADE, BOOTS connected by "_" to help you sort easier
    public int itemCost; // BRONZE items have lowest cost, the most expensive items are LEGENDARY
    public int damage; // keyword BLADE is present if the most important item stat is damage
    public int health;
    public int maxHealth;
    public int mana;
    public int maxMana;
    public int moveSpeed; // keyword BOOTS is present if the most important item stat is moveSpeed
    public int manaRegeneration;
    public int isPotion; // 0 if it's not instantly consumed
    public ItemRarityEnum itemRarityEnum;

    public Item(Scanner in) {
        itemName = in.next(); // contains keywords such as BRONZE, SILVER and BLADE, BOOTS connected by "_" to help you sort easier
        itemCost = in.nextInt(); // BRONZE items have lowest cost, the most expensive items are LEGENDARY
        damage = in.nextInt(); // keyword BLADE is present if the most important item stat is damage
        health = in.nextInt();
        maxHealth = in.nextInt();
        mana = in.nextInt();
        maxMana = in.nextInt();
        moveSpeed = in.nextInt(); // keyword BOOTS is present if the most important item stat is moveSpeed
        manaRegeneration = in.nextInt();
        isPotion = in.nextInt(); // 0 if it's not instantly consumed
        itemRarityEnum = ItemRarityEnum.fromString(itemName);
    }

    public boolean isPotion() {
        return isPotion > 0;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemName='" + itemName + '\'' +
                '}';
    }
}
