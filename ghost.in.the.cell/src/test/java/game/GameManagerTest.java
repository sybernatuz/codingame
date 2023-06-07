package game;

import enums.OwnerEnum;
import initializers.FactoryInitializer;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Scanner;

public class GameManagerTest {

    @Before
    public void initLists() {
        Game.getInstance().factories.clear();
        FactoryInitializer.initFactories();
    }

    @Test
    public void updateEntityAddBombTest() {
        InputStream is = getClass().getResourceAsStream("/managers/entitymanager/update_entity_add_bomb.txt");
        Scanner scanner = new Scanner(is);

        GameManager.getInstance().update(scanner);
        Assertions.assertThat(Game.getInstance().bombs)
                .hasSize(1)
                .anySatisfy(bomb -> Assertions.assertThat(bomb.id).isEqualTo(1));
    }

    @Test
    public void updateEntityAddTroopTest() {
        InputStream is = getClass().getResourceAsStream("/managers/entitymanager/update_entity_add_troop.txt");
        Scanner scanner = new Scanner(is);

        GameManager.getInstance().update(scanner);
        Assertions.assertThat(Game.getInstance().troops)
                .hasSize(1)
                .anySatisfy(troop -> Assertions.assertThat(troop.id).isEqualTo(1));
    }

    @Test
    public void updateEntityRootFactoryTest() {
        InputStream is = getClass().getResourceAsStream("/managers/entitymanager/update_entity_factory.txt");
        Scanner scanner = new Scanner(is);

        GameManager.getInstance().update(scanner);
        Assertions.assertThat(Game.getInstance().factories)
                .hasSize(4)
                .anySatisfy(factory -> {
                    Assertions.assertThat(factory.id).isEqualTo(1);
                    Assertions.assertThat(factory.owner).isEqualTo(OwnerEnum.ENEMY);
                });
    }

    @Test
    public void updateEntityChildFactoriesTest() {
        InputStream is = getClass().getResourceAsStream("/managers/entitymanager/update_entity_factory.txt");
        Scanner scanner = new Scanner(is);

        GameManager.getInstance().update(scanner);
        Game.getInstance().factories.stream()
                .flatMap(factory -> factory.neighbours.stream())
                .map(link -> link.neighbour)
                .filter(factory -> factory.id == 1)
                .forEach(factory -> Assert.assertEquals(OwnerEnum.ENEMY, factory.owner));
    }
}