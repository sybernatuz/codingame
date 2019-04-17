package managers;

import builder.ZoneBuilder;
import enums.TeamEnum;
import initializers.GraphInitializer;
import initializers.ZoneInitializer;
import objects.Graph;
import objects.Zone;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Stream;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ZoneBuilder.class)
public class ZoneManagerTest {

    private ZoneManager zoneManager;
    private Graph graph;
    private Scanner scanner;

    @Before
    public void init() {
        graph = GraphInitializer.initGraph();
        zoneManager = new ZoneManager();
        scanner = new Scanner(System.in);
    }

    @Test
    public void updateTest() {
        Zone zoneToUpdate = ZoneInitializer.initFriendBase();
        zoneToUpdate.team = TeamEnum.ENEMY;

        ZoneBuilder zoneBuilderMock = Mockito.mock(ZoneBuilder.class);
        Mockito.when(zoneBuilderMock.update(Mockito.any(Scanner.class), Mockito.any(Integer.class)))
                .thenReturn(zoneBuilderMock);
        Mockito.when(zoneBuilderMock.build())
                .thenReturn(zoneToUpdate);

        PowerMockito.mockStatic(ZoneBuilder.class);
        PowerMockito.when(ZoneBuilder.init(Mockito.any()))
                .thenReturn(zoneBuilderMock);
        zoneManager.updateZone(graph, scanner, 1);
        graph.zonesByLinkedZone.entrySet().stream()
                .flatMap(entry -> Stream.of(
                        Stream.of(entry.getKey()),
                        entry.getValue().stream()))
                .flatMap(Function.identity())
                .filter(zone -> zone.id == 1)
                .forEach(zone -> Assert.assertEquals(TeamEnum.ENEMY, zone.team));
    }


}
