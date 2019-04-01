package main.java.compete.legends_of_code_magic.utils;

import main.java.compete.legends_of_code_magic.objects.Attack;
import main.java.compete.legends_of_code_magic.objects.Card;
import main.java.compete.legends_of_code_magic.objects.UseSpell;

import java.util.List;

public class LoggerUtils {

    public static void title(String title) {
        System.err.println("--------------");
        System.err.println("--------------");
        System.err.println(title);
        System.err.println("--------------");
        System.err.println("--------------");
    }

    public static void subTitle(String subTitle) {
        System.err.println("--------------");
        System.err.println(subTitle);
        System.err.println("--------------");
    }

    public static void listCards(List<Card> cards) {
        cards.forEach(LoggerUtils::card);
    }

    public static void listAttacks(List<Attack> attacks) {
        attacks.forEach(LoggerUtils::attack);
    }

    public static void listSpells(List<UseSpell> useSpells) {
        useSpells.forEach(LoggerUtils::spell);
    }

    public static void spell(UseSpell useSpell) {
        System.err.println("Spell id : " + useSpell.spell.instanceId);
        System.err.println("Spell target : " + useSpell.target.instanceId);
    }

    public static void card(Card card) {
        System.err.println("Card id : " + card.instanceId);
        System.err.println("Card cost : " + card.cost);
    }

    public static void attack(Attack attack) {
        System.err.println("Attack target : " + attack.targetCard.instanceId);
        System.err.println("Attack attackers : ");
        listCards(attack.attackerCards);
    }

    public static void card(String name, Card card) {
        System.err.println(name);
        card(card);
    }
}
