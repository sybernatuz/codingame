package main.java.compete.legends_of_code_magic.managers;

import main.java.compete.legends_of_code_magic.objects.*;
import main.java.compete.legends_of_code_magic.strategies.AttackStrategy;
import main.java.compete.legends_of_code_magic.strategies.SpellStrategy;
import main.java.compete.legends_of_code_magic.strategies.SummonStrategy;
import main.java.compete.legends_of_code_magic.utils.FindUtils;
import main.java.compete.legends_of_code_magic.utils.LoggerUtils;

import java.util.List;

public class ActionManager {

    private SummonStrategy summonStrategy = new SummonStrategy();
    private AttackStrategy attackStrategy = new AttackStrategy();
    private SpellStrategy spellStrategy = new SpellStrategy();

    public String computeAction(List<Card> cardsPlayable, Legend me, Board board) {
        StringBuilder actions = new StringBuilder();
        if (board.myCards.size() > 0)
            actions.append(useSpells(cardsPlayable, me, board));

        if (FindUtils.findByAttackAvailable(board.myCards).size() > 0)
            actions.append(attack(board));

        if (me.getCardsPlayable().isEmpty())
            actions.append("PASS;");
        else if(board.myCards.size() < 6)
            actions.append(summonCards(cardsPlayable, me, board));

        if (isPossibleAction(me, actions, board))
            return actions.append(computeAction(cardsPlayable, me, board)).toString();

        return actions.toString();
    }

    private boolean isPossibleAction(Legend me, StringBuilder actions, Board board) {
        List<Card> cardsPlayable = me.getCardsPlayable();
        boolean isCardPlayable = cardsPlayable.size() > 0;
        boolean avoidInfiniteLoop = !actions.toString().isEmpty();
        boolean isPossibleAttack = board.myCards.stream().anyMatch(Card::isAttackAvailable);
        return (isCardPlayable || isPossibleAttack) && avoidInfiniteLoop;
    }

    private String useSpells(List<Card> cardsPlayable, Legend me, Board board) {
        LoggerUtils.title("SPELLS");
        StringBuilder spellsUsed = new StringBuilder();
        List<UseSpell> spellsToUse = spellStrategy.computeSpellsToUse(cardsPlayable, me, board);
        spellsToUse
                .forEach(spellToUse -> spellsUsed.append("USE ")
                        .append(spellToUse.spell.instanceId)
                        .append(" ")
                        .append(spellToUse.target.instanceId)
                        .append(";"));

        LoggerUtils.subTitle("Spells to use");
        LoggerUtils.listSpells(spellsToUse);
        return spellsUsed.toString();
    }

    private String summonCards(List<Card> cardsPlayable, Legend me, Board board) {
        LoggerUtils.title("SUMMON");
        StringBuilder cardsUsed = new StringBuilder();
        List<Card> creaturesToSummon = summonStrategy.computeCardsToSummon(cardsPlayable, me, board);
        creaturesToSummon
                .forEach(card -> cardsUsed.append("SUMMON ")
                        .append(card.instanceId)
                        .append(";"));

        LoggerUtils.subTitle("Cards to summon");
        LoggerUtils.listCards(creaturesToSummon);
        return cardsUsed.toString();
    }

    private String attack(Board board) {
        LoggerUtils.title("ATTACK");
        StringBuilder attacks = new StringBuilder();
        List<Attack> attacksToDo = attackStrategy.computeAttacks(board);
        attacksToDo.forEach(attack ->
                attack.attackerCards.forEach(attacker -> attacks.append("ATTACK ")
                        .append(attacker.instanceId)
                        .append(" ")
                        .append(attack.targetCard.instanceId)
                        .append(";")));

        LoggerUtils.subTitle("Attacks to do");
        LoggerUtils.listAttacks(attacksToDo);
        return attacks.toString();
    }
}
