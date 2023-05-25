package strategies;

import objects.Game;
import objects.Item;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BuyStrategy {

    public Optional<String> process(Game game, List<Item> items) {
        double healthPercent = game.currentHero.entity.getCurrentHealthPercent();
        if (healthPercent < 60) {
            if (game.currentHero.items.size() == 4) {
                Optional<Item> lowestCostOwnedItem = findLowestCostOwnedItem(game);
                lowestCostOwnedItem.ifPresent(item -> game.currentHero.items.remove(item));
                return lowestCostOwnedItem.map(item -> String.format("SELL %s", item.itemName));
            }
            return buyHealthPotion(game, items)
                    .map(item -> String.format("BUY %s", item.itemName));
        }

        if (game.currentHero.items.size() < 4) {
            return buyBlade(game, items)
                    .map(item -> String.format("BUY %s", item.itemName));
        }

        return sellUselessBlade(game, items)
                .map(item -> String.format("SELL %s", item.itemName));
    }

    private Optional<Item> sellUselessBlade(Game game, List<Item> items) {
        Optional<Item> lowestCostOwnedItem = findLowestCostOwnedItem(game);

        if (!lowestCostOwnedItem.isPresent())
            return Optional.empty();

        Optional<Item> bestBuyableBlade = bestBuyableBlade(game, items)
                .filter(item -> item.itemCost > lowestCostOwnedItem.get().itemCost);

        if (!bestBuyableBlade.isPresent()) {
            return Optional.empty();
        }
        game.currentHero.items.remove(lowestCostOwnedItem.get());
        return lowestCostOwnedItem;
    }

    private Optional<Item> buyBlade(Game game, List<Item> items) {
        Optional<Item> blade = bestBuyableBlade(game, items);
        blade.ifPresent(item -> {
            game.currentHero.items.add(item);
            game.gold -= item.itemCost;
        });
        return blade;
    }

    private Optional<Item> bestBuyableBlade(Game game, List<Item> items) {
        return items.stream()
                .filter(item -> item.itemName.contains("Blade"))
                .filter(item -> item.itemCost <= game.gold)
                .max(Comparator.comparing(item -> item.itemCost));
    }

    private Optional<Item> buyHealthPotion(Game game, List<Item> items) {
        Optional<Item> healthPotion = items.stream()
                .filter(Item::isPotion)
                .filter(item -> item.health > 0)
                .filter(item -> item.itemCost < game.gold)
                .max(Comparator.comparing(item -> item.itemCost));
        healthPotion.ifPresent(item -> game.gold -= item.itemCost);
        return healthPotion;
    }

    private Optional<Item> findLowestCostOwnedItem(Game game) {
        return game.currentHero.items.stream()
                .min(Comparator.comparing(item -> item.itemCost));
    }
}
