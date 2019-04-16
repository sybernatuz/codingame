package managers;

import enums.AbilitiesEnum;
import enums.CardTypeEnum;
import objects.Board;
import objects.Card;
import objects.Legend;
import objects.UseSpell;
import utils.FindUtils;

import java.util.List;
import java.util.stream.Collectors;

public class UseManager {

    public void addCard(Card card, List<Card> cardsToUse, Legend me, Board board) {
        if (card.cost > me.playerMana)
            return;
        if (!card.abilities.contains(AbilitiesEnum.CHARGE))
            card.isAttackAvailable = false;
        manaUse(card, me);
        addCardToUse(card, cardsToUse, me, board);
    }

    public void addSpell(UseSpell useSpell, List<UseSpell> spellsToUse, Legend me, Board board) {
        if (useSpell.spell.cost > me.playerMana)
            return;
        manaUse(useSpell.spell, me);
        addSpellToUse(useSpell, spellsToUse, me);
        if (useSpell.spell.cardType.equals(CardTypeEnum.GREEN_ITEM))
            updateCreatureStat(useSpell, board);
    }

    public void useAttack(List<Card> cards, Card card, Card opponent) {
        if (opponent.attack >= card.defense)
            cards.remove(card);
        else {
            FindUtils.findCardInList(card, cards).isAttackAvailable = false;
            FindUtils.findCardInList(card, cards).defense -= opponent.attack;
        }
    }

    private void manaUse(Card card, Legend me) {
        me.playerMana -= card.cost;
    }

    private void addCardToUse(Card card, List<Card> cardsToUse, Legend me, Board board) {
        cardsToUse.add(card);
        board.myCards.add(card);
        me.deck.remove(card);
    }

    private void addSpellToUse(UseSpell useSpell, List<UseSpell> spellsToUse, Legend me) {
        spellsToUse.add(useSpell);
        me.deck.remove(useSpell.spell);
    }

    private void updateCreatureStat(UseSpell useSpell, Board board) {
        Card card = FindUtils.findCardInList(useSpell.target, board.myCards);
        card.defense += useSpell.spell.defense;
        card.attack += useSpell.spell.attack;
        List<AbilitiesEnum> differentAbilities = useSpell.spell.abilities.stream()
                .filter(ability -> !card.abilities.contains(ability))
                .collect(Collectors.toList());
        card.abilities.addAll(differentAbilities);
        card.cost += useSpell.spell.cost;
        Integer position = FindUtils.findPositionById(card.instanceId, board.myCards);
        if (position != null)
            board.myCards.set(position, card);
    }

}
