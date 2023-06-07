package game;

import enums.EntityTypeEnum;
import enums.OwnerEnum;
import objects.Bomb;
import objects.Troop;

import java.util.Scanner;

class GameUpdater {

    private static final GameUpdater INSTANCE = new GameUpdater();

    public static GameUpdater getInstance() {
        return INSTANCE;
    }

    public void update(Scanner in) {
        int entityCount = in.nextInt(); // the number of entities (e.g. factories and troops)
        for (int i = 0; i < entityCount; i++) {
            updateEntity(in);
        }
        updateTroopsNumber();
    }

    private void updateEntity(Scanner in) {
        int entityId = in.nextInt();
        String entityType = in.next();
        switch (EntityTypeEnum.valueOf(entityType)) {
            case TROOP:
                Troop troop = new Troop(in, entityId);
                Game.getInstance().troops.add(troop);
                break;
            case FACTORY:
                updateFactory(in, entityId);
                break;
            case BOMB:
                Bomb bomb = new Bomb(in, entityId);
                Game.getInstance().bombs.add(bomb);
                break;
        }
    }

    private void updateFactory(Scanner in, int entityId) {
        Game.getInstance().factories.stream()
                .filter(factory -> factory.id == entityId)
                .findFirst()
                .ifPresent(factory -> factory.update(in));
    }

    private void updateTroopsNumber() {
        Game.getInstance().myTroopsNumber = computeTroopsNumberForOwner(OwnerEnum.FRIEND);
        Game.getInstance().enemyTroopsNumber = computeTroopsNumberForOwner(OwnerEnum.ENEMY);
    }

    private int computeTroopsNumberForOwner(OwnerEnum owner) {
        int troopsInBases = Game.getInstance().factories.stream()
                .filter(factory -> owner.equals(factory.owner))
                .mapToInt(factory -> factory.troopNumber)
                .sum();

        int troopsInMove = Game.getInstance().troops.stream()
                .filter(troop -> owner.equals(troop.owner))
                .mapToInt(troop -> troop.number)
                .sum();

        return troopsInBases + troopsInMove;
    }
}
