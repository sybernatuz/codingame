package managers;

import builder.ZoneBuilder;
import initializers.GraphInitializer;
import initializers.ZoneInitializer;
import objects.Graph;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import java.util.Scanner;

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
        ZoneBuilder zoneBuilder = Mockito.mock(ZoneBuilder.class);
        Mockito.when(zoneBuilder.update(Mockito.any(), Mockito.any()))
                .thenReturn(zoneBuilder);
        Mockito.when(zoneBuilder.build())
                .thenReturn(ZoneInitializer.initFriendZone1());
        PowerMockito.mockStatic(ZoneBuilder.class);
        PowerMockito.when(ZoneBuilder.init(Mockito.any()))
                .thenReturn(zoneBuilder);
        zoneManager.updateZone(graph, scanner, 1);
    }




}
