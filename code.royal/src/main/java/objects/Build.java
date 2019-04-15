package objects;


import enums.BarrackTypeEnum;
import enums.StructureTypeEnum;

public class Build {

    public Site site;
    public StructureTypeEnum structureType = StructureTypeEnum.BARRACK;
    public BarrackTypeEnum barrackType;

    public Build(Site site, BarrackTypeEnum barrackType) {
        this.site = site;
        this.barrackType = barrackType;
    }

    public Build(Site site, StructureTypeEnum structureType) {
        this.site = site;
        this.structureType = structureType;
    }

    @Override
    public String toString() {
        return "Build{" +
                "site=" + site +
                ", structureType=" + structureType +
                ", barrackType=" + barrackType +
                '}';
    }
}
