package strategies;

import objects.Game;
import objects.actions.Type;

public class PowerChargeStrategy {

    private static final PowerChargeStrategy INSTANCE = new PowerChargeStrategy();

    public static PowerChargeStrategy getInstance() {
        return INSTANCE;
    }

    public Type getPowerToCharge() {
        if (Game.getInstance().enemySubmarine.coordinate != null && Game.getInstance().torpedoCooldown > 0)
            return Type.TORPEDO;

        if (Game.getInstance().silenceCooldown > 0)
            return Type.SILENCE;
        if (Game.getInstance().torpedoCooldown > 0)
            return Type.TORPEDO;
        if (Game.getInstance().sonarCooldown > 0)
            return Type.SONAR;
        return Type.MINE;
    }
}
