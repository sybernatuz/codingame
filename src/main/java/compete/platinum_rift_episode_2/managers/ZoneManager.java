package main.java.compete.platinum_rift_episode_2.managers;

import main.java.compete.platinum_rift_episode_2.objects.Link;
import main.java.compete.platinum_rift_episode_2.objects.Zone;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ZoneManager {

    public boolean isZoneUpdate = false;

    public void updateZone(Zone rootZone, int zoneId, Scanner in) {
        if (isZoneUpdate)
            return;

        if (zoneId == 0) {
            rootZone.update(in);
            isZoneUpdate = true;
        }

        Optional<Zone> zoneToUpdate = rootZone.linkedZones.stream()
                .filter(zone -> zone.zoneId == zoneId)
                .findFirst();

        if (zoneToUpdate.isPresent()) {
            rootZone.linkedZones.get(rootZone.linkedZones.indexOf(zoneToUpdate.get())).update(in);
            isZoneUpdate = true;
        }

        rootZone.linkedZones.forEach(zone -> updateZone(zone, zoneId, in));
    }

    public Zone getZone(Zone rootZone, int zoneId) {
        if (zoneId == 0) {
            return rootZone;
        }

        Optional<Zone> zoneById = rootZone.linkedZones.stream()
                .filter(zone -> zone.zoneId == zoneId)
                .findFirst();

        return zoneById.orElseGet(() -> rootZone.linkedZones
                .stream()
                .filter(zone -> getZone(zone, zoneId) != null)
                .findFirst()
                .orElse(null));
    }

    public void organizeLinkToZone(List<Zone> zones, List<Link> links, Zone rootZone) {
        List<Link> rootLink = links.stream()
                .filter(link -> link.zone1.equals(rootZone) || link.zone2.equals(rootZone))
                .collect(Collectors.toList());
        List<Zone> linkedZone = rootLink.stream()
                .flatMap(link -> Stream.of(link.zone1, link.zone2))
                .collect(Collectors.toList())
                .stream()
                .filter(zone -> zone.zoneId != rootZone.zoneId)
                .collect(Collectors.toList());

        if (linkedZone.isEmpty())
            return;

        rootZone.linkedZones.addAll(linkedZone);
        zones.remove(rootZone);
        rootLink.forEach(links::remove);
        linkedZone.forEach(zone -> organizeLinkToZone(zones, links, zone));
    }
}
