package game;

import initializers.FactoryInitializer;
import objects.Side;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class SideInitializerTest {

    @Before
    public void initLists() {
        Game.getInstance().factories.clear();
        FactoryInitializer.initFactories();
    }

    @Test
    public void test() {
       SideInitializer.getInstance().initialize();
        Assertions.assertThat(Game.getInstance().factories)
                .anySatisfy(factory -> {
                    Assertions.assertThat(factory.id).isEqualTo(1);
                    Assertions.assertThat(factory.side).isEqualTo(Side.ENEMY_SIDE);
                })
                .anySatisfy(factory -> {
                    Assertions.assertThat(factory.id).isEqualTo(4);
                    Assertions.assertThat(factory.side).isEqualTo(Side.CONTESTED);
                });
    }

}