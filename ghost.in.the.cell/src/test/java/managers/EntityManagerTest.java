package managers;

import enums.EntityTypeEnum;
import objects.Bomb;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.mockito.Matchers.any;

public class EntityManagerTest {

    @Test
    public void updateEntityTest() {
        List<Bomb> bombs = new ArrayList<>();
        try (InputStream is = getClass().getResourceAsStream("/managers/entitymanager/update_bomb.txt")) {
            EntityManager entityManager = new EntityManager();
            Scanner scanner = new Scanner(is);
            entityManager.updateEntity(scanner, null, null, bombs);
            Assert.assertEquals(1, bombs.size());
        } catch (IOException e) {
            Assert.fail("Exception");
        }
    }
}
