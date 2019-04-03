package main.java.compete.code_royal.objects;

import main.java.compete.code_royal.enums.BarrackTypeEnum;
import main.java.compete.code_royal.enums.StructureTypeEnum;

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
}
