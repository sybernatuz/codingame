package game;

import initializers.FactoryInitializer;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Scanner;

public class LinkInitializerTest {

    @Before
    public void initLists() {
        Game.getInstance().factories.clear();
        FactoryInitializer.initFactories();
    }

    @Test
    public void addLinkTest() {
        InputStream is = getClass().getResourceAsStream("/game/add_link.txt");
        Scanner scanner = new Scanner(is);

        LinkInitializer.getInstance().initialize(scanner);
        Assertions.assertThat(Game.getInstance().factories)
                .hasSize(4)
                .anySatisfy(factory -> {
                    Assertions.assertThat(factory.id).isEqualTo(2);
                    Assertions.assertThat(factory.neighbours)
                            .map(link -> link.neighbour.id)
                            .contains(3);
                })
                .anySatisfy(factory -> {
                    Assertions.assertThat(factory.id).isEqualTo(3);
                    Assertions.assertThat(factory.neighbours)
                            .map(link -> link.neighbour.id)
                            .contains(2);
                });
    }
}