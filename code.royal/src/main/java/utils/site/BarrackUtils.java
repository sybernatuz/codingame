package utils.site;


import enums.BarrackTypeEnum;
import enums.OwnerEnum;
import enums.StructureTypeEnum;
import objects.Site;

import java.util.List;
import java.util.stream.Collectors;

public class BarrackUtils {

    public static List<Site> findTrainingBarrack(List<Site> sites, OwnerEnum owner) {
        return FindUtils.findByStructureTypeAndOwner(sites, StructureTypeEnum.BARRACK, owner).stream()
                .filter(site -> site.param2 > 0)
                .collect(Collectors.toList());
    }

    public static List<Site> findWaitingBarrack(List<Site> sites, OwnerEnum owner) {
        return FindUtils.findByStructureTypeAndOwner(sites, StructureTypeEnum.BARRACK, owner).stream()
                .filter(site -> site.param1 == 0)
                .collect(Collectors.toList());
    }

    public static List<Site> findByBarrackType(List<Site> sites, BarrackTypeEnum barrackType) {
        return FindUtils.findByStructureType(sites, StructureTypeEnum.BARRACK).stream()
                .filter(site -> BarrackTypeEnum.get(site.param2).equals(barrackType))
                .collect(Collectors.toList());
    }

    public static List<Site> findByBarrackTypeAndOwner(List<Site> sites, BarrackTypeEnum barrackType, OwnerEnum owner) {
        return findByBarrackType(FindUtils.findByOwner(sites, owner), barrackType);
    }
}
