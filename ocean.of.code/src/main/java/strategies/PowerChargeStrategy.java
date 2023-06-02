package strategies;

import objects.Game;
import objects.actions.Type;

public class PowerChargeStrategy {

    private static final PowerChargeStrategy INSTANCE = new PowerChargeStrategy();

    public static PowerChargeStrategy getInstance() {
        return INSTANCE;
    }

    public Type getPowerToCharge() {
        if (Game.getInstance().silenceCooldown > 0) {
            return Type.SILENCE;
        } else if (Game.getInstance().torpedoCooldown > 0) {
            return Type.TORPEDO;
        } else if (Game.getInstance().sonarCooldown > 0){
            return Type.SONAR;
        } else {
            return Type.MINE;
        }
    }

}
