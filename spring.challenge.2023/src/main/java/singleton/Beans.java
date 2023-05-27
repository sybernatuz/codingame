package singleton;

import search.BfsSearch;
import search.SearchClosestEgg;
import search.SearchClosestFood;
import search.SearchSpecificZone;
import strategies.LowCrystalStrategy;
import strategies.MainStrategy;
import strategies.StrategiesBridge;

public class Beans {

    public final static BfsSearch searchClosestFood = new SearchClosestFood();
    public final static BfsSearch searchClosestEgg = new SearchClosestEgg();
    public final static BfsSearch searchSpecificZone = new SearchSpecificZone();
    public final static StrategiesBridge strategiesBridge = new StrategiesBridge();
    public final static MainStrategy mainStrategy = new MainStrategy();
    public final static LowCrystalStrategy lowCrystalStrategy = new LowCrystalStrategy();
}
