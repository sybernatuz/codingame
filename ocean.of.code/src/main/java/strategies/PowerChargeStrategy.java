package strategies;

import objects.Game;
import objects.actions.Action;
import objects.actions.Type;

import java.util.Collections;
import java.util.List;

public class PowerChargeStrategy {

    private static final PowerChargeStrategy INSTANCE = new PowerChargeStrategy();

    public static PowerChargeStrategy getInstance() {
        return INSTANCE;
    }

    public Type getPowerToCharge() {
        return getPowerToCharge(Collections.emptyList());
    }

    public Type getPowerToCharge(List<Action> actions) {
        if (needChargeTorpedo(actions))
            return Type.TORPEDO;

        if (Game.getInstance().silenceCooldown > 0)
            return Type.SILENCE;
        if (Game.getInstance().torpedoCooldown > 0)
            return Type.TORPEDO;
        if (Game.getInstance().sonarCooldown > 0)
            return Type.SONAR;
        return Type.MINE;
    }

    private boolean needChargeTorpedo(List<Action> actions) {
        return Game.getInstance().enemySubmarine.coordinate != null
                && (
                        Game.getInstance().torpedoCooldown > 0
                        || actions.stream().anyMatch(action -> Type.TORPEDO.equals(action.type))
                );
    }
}
