package scripts.VorkathAnnihilator.Actions.FightThreads;

import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import scripts.VorkathAnnihilator.Data.Variables;
import scripts.VorkathAnnihilator.Utils.VorkathUtil;

public class SlimeThread extends Thread {


    @Override
    public void run() {

        if (!Variables.slimePhase) {
            General.sleep(250);
        }
        while (true) {
            if (!slimePhaseStarting()) {
                General.sleep(350, 500);
                continue;
            }
            else {
                Variables.slimePhase = true;
                Variables.setState("SLIME WALKING");
                Variables.slimePhaseStart = System.currentTimeMillis();
                VorkathUtil.disableRun();
                VorkathUtil.slimeWalk();
            }
        }
        }








    public boolean slimePhaseStarting() {
        RSProjectile[] projects = Projectiles.getAll();
        for (RSProjectile p : projects) {
            if (p.getGraphicID() == 1483) {
                Variables.slimePhase = true;
                return true;
            }
        }
        return false;
    }
}
