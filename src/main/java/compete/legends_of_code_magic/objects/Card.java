package main.java.compete.legends_of_code_magic.objects;

import main.java.compete.legends_of_code_magic.enums.AbilitiesEnum;
import main.java.compete.legends_of_code_magic.enums.CardTypeEnum;

import java.util.List;
import java.util.Scanner;

public class Card {

    public int cardNumber;
    public int instanceId;
    public int location;
    public CardTypeEnum cardType;
    public int cost;
    public int attack;
    public int defense;
    public List<AbilitiesEnum> abilities;
    public int myHealthChange;
    public int opponentHealthChange;
    public int cardDraw;
    public boolean isAttackAvailable = true;

    public Card() {}

    public Card(Scanner in) {
        this.cardNumber = in.nextInt();
        this.instanceId = in.nextInt();
        this.location = in.nextInt();
        this.cardType = CardTypeEnum.get(in.nextInt());
        this.cost = in.nextInt();
        this.attack = in.nextInt();
        this.defense = in.nextInt();
        this.abilities = AbilitiesEnum.get(in.next());
        this.myHealthChange = in.nextInt();
        this.opponentHealthChange = in.nextInt();
        this.cardDraw = in.nextInt();
    }

    public int getAttack() {
        return attack;
    }

    public int getCost() {
        return cost;
    }

    public boolean isAttackAvailable() {
        return isAttackAvailable;
    }
}
