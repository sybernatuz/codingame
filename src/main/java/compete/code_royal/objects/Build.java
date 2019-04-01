package main.java.compete.code_royal.objects;

import main.java.compete.code_royal.enums.BarrackTypeEnum;

public class Build {

    public Site site;
    public BarrackTypeEnum barrackType;

    public Build(Site site, BarrackTypeEnum barrackType) {
        this.site = site;
        this.barrackType = barrackType;
    }
}
