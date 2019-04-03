package main.java.compete.code_royal.utils.site;

import main.java.compete.code_royal.enums.OwnerEnum;
import main.java.compete.code_royal.enums.StructureTypeEnum;
import main.java.compete.code_royal.objects.Site;

import java.util.List;
import java.util.Optional;

public class TowerUtils {

    public static List<Site> findByOwner(List<Site> sites, OwnerEnum owner) {
        return FindUtils.findByStructureTypeAndOwner(sites, StructureTypeEnum.TOWER, owner);
    }

    public static Optional<Site> findByLowRadius(List<Site> sites, OwnerEnum owner) {
        return findByOwner(sites, owner).stream()
                .filter(tower -> tower.param2 < 150)
                .findFirst();
    }
}
