package scripts.BloodRuneCrafter.Actions;

import org.tribot.api.General;
import org.tribot.api2007.WorldHopper;
import scripts.BloodRuneCrafter.Data.Vars;

public class WorldHop extends Action {

    @Override
    public void activate() {
        WorldHopper.changeWorld(WorldHopper.getRandomWorld(true));
        Vars.lastWorldHop = System.currentTimeMillis();
        Vars.nextHop = 30000 + General.random(-2000, 2000);
    }

    @Override
    public boolean active() {
        long timeSinceHop = System.currentTimeMillis() - Vars.lastWorldHop;
        return timeSinceHop >= Vars.nextHop;
    }
}
