import enums.HeroEnum;
import objects.Entity;
import objects.Game;
import objects.Hero;
import objects.Item;
import strategies.AttackStrategy;
import strategies.BuyStrategy;
import strategies.JungleStrategy;
import strategies.MoveStrategy;
import utils.SearchUtils;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Player {

    public final static BuyStrategy buyStrategy = new BuyStrategy();
    public final static MoveStrategy moveStrategy = new MoveStrategy();
    public final static AttackStrategy attackStrategy = new AttackStrategy();
    public final static JungleStrategy jungleStrategy = new JungleStrategy();


    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int myTeam = in.nextInt();
        int bushAndSpawnPointCount = in.nextInt(); // useful from wood1, represents the number of bushes and the number of places where neutral units can spawn
        for (int i = 0; i < bushAndSpawnPointCount; i++) {
            String entityType = in.next(); // BUSH, from wood1 it can also be SPAWN
            int x = in.nextInt();
            int y = in.nextInt();
            int radius = in.nextInt();
        }
        int itemCount = in.nextInt(); // useful from wood2
        List<Item> items = IntStream.range(0, itemCount)
                .mapToObj(i -> new Item(in))
                .collect(Collectors.toList());

        Game game = new Game();
        Hero ranged = new Hero(HeroEnum.IRONMAN);
        Hero tank = new Hero(HeroEnum.HULK);

        // game loop
        while (true) {
            game.update(in);
            int entityCount = in.nextInt();
            game.entities = IntStream.range(0, entityCount)
                    .mapToObj(value -> new Entity(in, myTeam))
                    .collect(Collectors.toList());
            game.updateSide();

            if (game.isFirstRound()) {
                System.out.println(ranged.heroEnum);
                System.out.println(tank.heroEnum);
            } else {
                SearchUtils.findMyHero(game.entities, ranged.heroEnum)
                        .ifPresent(hero -> {
                            ranged.entity = hero;
                            game.currentHero = ranged;
                            System.err.println(ranged);
                            System.out.println(computeRangedAction(game, items));
                        });
                SearchUtils.findMyHero(game.entities, tank.heroEnum)
                        .ifPresent(hero -> {
                            tank.entity = hero;
                            game.currentHero = tank;
                            System.err.println(tank);
                            System.out.println(computeTankAction(game, items));
                        });
            }
        }
    }

    private static String computeRangedAction(Game game, List<Item> items) {
        return buyStrategy.process(game, items)
                .orElseGet(() -> moveStrategy.process(game)
                .orElseGet(() -> attackStrategy.process(game)));
    }

    private static String computeTankAction(Game game, List<Item> items) {
        return buyStrategy.process(game, items)
                .orElseGet(() -> jungleStrategy.process(game));
    }
}
