package strategies;

import enums.AbilitiesEnum;
import managers.UseManager;
import objects.Attack;
import objects.Board;
import objects.Card;
import utils.FindUtils;
import utils.SortUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AttackStrategy {

    private UseManager useManager = new UseManager();

    public List<Attack> computeAttacks(Board board) {
        List<Attack> attacks = new ArrayList<>();
        List<Card> opponentGuardCards = FindUtils.findByAbility(board.opponentCards, AbilitiesEnum.GUARD);
        if (!opponentGuardCards.isEmpty())
            attacks.addAll(attackOpponentGuardCards(opponentGuardCards, board));

        if (FindUtils.findByAttackAvailable(board.myCards).size() > 0)
            attacks.addAll(computeOptimalAttack(board));

        if (FindUtils.findByAttackAvailable(board.myCards).size() > 0)
            attacks.add(attackOpponentLegend(board));

        return attacks;
    }

    private List<Attack> computeOptimalAttack(Board board) {
        List<Attack> optimalAttackers = new ArrayList<>();
        board.opponentCards.forEach(opponentCard -> addOptimalAttack(opponentCard, board, optimalAttackers));
        optimalAttackers.forEach(attack -> board.opponentCards.remove(attack.targetCard));
        return optimalAttackers;
    }

    private void addOptimalAttack(Card opponentCard, Board board, List<Attack> optimalAttackers) {
        List<Card> possibleAttackers = FindUtils.findByAttackAvailable(board.myCards);
        Card optimalAttacker = FindUtils.findOptimalAttackerWithPossibleDeath(opponentCard, possibleAttackers);
        if (optimalAttacker == null)
            return;

        Attack attack = new Attack(optimalAttacker, opponentCard);
        useManager.useAttack(board.myCards, attack.attackerCards.get(0), attack.targetCard);
        optimalAttackers.add(attack);
    }

    private Attack computeOpponentCardAttack(Card opponentGuardCard, Board board) {
        Attack attack = new Attack();
        attack.targetCard = opponentGuardCard;
        Card optimalAttacker = FindUtils.findOptimalAttackerWithPossibleDeath(opponentGuardCard, board.myCards);
        if (optimalAttacker != null) {
            attack.attackerCards.add(optimalAttacker);
            useManager.useAttack(board.myCards, optimalAttacker, opponentGuardCard);
            if (!opponentGuardCard.abilities.contains(AbilitiesEnum.WARD))
                board.opponentCards.remove(opponentGuardCard);
            return attack;
        }

        int defense = opponentGuardCard.defense;
        board.myCards = SortUtils.sortByCost(board.myCards);
        List<Card> myCards = new ArrayList<>(board.myCards);
        for (Card card : myCards) {
            if (card.attack == 0 || !card.isAttackAvailable)
                continue;
            if (defense > 0) {
                attack.attackerCards.add(card);
                useManager.useAttack(board.myCards, card, opponentGuardCard);
                if (!opponentGuardCard.abilities.contains(AbilitiesEnum.WARD))
                    defense -= card.attack;
            } else
                board.opponentCards.remove(opponentGuardCard);
        }
        return attack;
    }

    private Attack attackOpponentLegend(Board board) {
        Attack attackLegend = new Attack();
        Card legend = new Card();
        legend.instanceId = -1;
        attackLegend.targetCard = legend;
        attackLegend.attackerCards = FindUtils.findByAttackAvailable(board.myCards);
        attackLegend.attackerCards.forEach(card -> useManager.useAttack(board.myCards, card, legend));
        return attackLegend;
    }

    private List<Attack> attackOpponentGuardCards(List<Card> opponentGuardCards, Board board) {
        return opponentGuardCards.stream()
                .map(card -> computeOpponentCardAttack(card, board))
                .collect(Collectors.toList());
    }
}
