package main.java.compete.platinum_rift_episode_2;

import main.java.compete.platinum_rift_episode_2.managers.ZoneManager;
import main.java.compete.platinum_rift_episode_2.objects.Link;
import main.java.compete.platinum_rift_episode_2.objects.Zone;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    private static ZoneManager zoneManager = new ZoneManager();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int playerCount = in.nextInt(); // the amount of players (always 2)
        int myId = in.nextInt(); // my player ID (0 or 1)
        int zoneCount = in.nextInt(); // the amount of zones on the map
        int linkCount = in.nextInt(); // the amount of links between all zones
        List<Zone> zones = new ArrayList<>();
        for (int i = 0; i < zoneCount; i++) {
            zones.add(new Zone(in, i));
        }
        List<Link> links = new ArrayList<>();
        for (int i = 0; i < linkCount; i++) {
            links.add(new Link(in, zones));
        }

        Zone rootZone = getRootZone(zones);
        zoneManager.organizeLinkToZone(zones, links, rootZone);

        // game loop
        while (true) {
            int myPlatinum = in.nextInt(); // your available Platinum
            for (int i = 0; i < zoneCount; i++) {
                int zoneId = in.nextInt();
                zoneManager.isZoneUpdate = false;
                zoneManager.updateZone(rootZone, zoneId, in);
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");


            // first line for movement commands, second line no longer used (see the protocol in the statement for details)
            System.out.println("WAIT");
            System.out.println("WAIT");
        }
    }

    private static Zone getRootZone(List<Zone> zones) {
        return zones.stream()
                .filter(zone -> zone.zoneId == 0)
                .findFirst()
                .orElse(null);
    }
}