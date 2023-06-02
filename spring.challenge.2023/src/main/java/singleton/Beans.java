package singleton;

import inmemory.NodesToGoComputer;
import search.BfsSearch;
import search.SearchClosestEgg;
import search.SearchSpecificZone;
import strategies.*;

public class Beans {

    public final static BfsSearch searchClosestEgg = new SearchClosestEgg();
    public final static BfsSearch searchSpecificZone = new SearchSpecificZone();
    public final static StrategiesBridge strategiesBridge = new StrategiesBridge();
    public final static MainStrategy mainStrategy = new MainStrategy();
    public final static LowCrystalStrategy lowCrystalStrategy = new LowCrystalStrategy();
    public final static HighCrystalStrategy highCrystalStrategy = new HighCrystalStrategy();
    public final static NodesToGoComputer nodesToGoComputer = new NodesToGoComputer();
    public final static EggsStrategy eggsStrategy = new EggsStrategy();
    public final static BlitzkriegStrategy blitzkriegStrategy = new BlitzkriegStrategy();
}
