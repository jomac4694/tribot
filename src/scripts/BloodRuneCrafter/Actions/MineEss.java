package scripts.BloodRuneCrafter.Actions;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObject;
import scripts.BloodRuneCrafter.Data.Constants;
import scripts.BloodRuneCrafter.Data.Vars;
import scripts.BloodRuneCrafter.Utils.Walker;

import java.util.Random;

public class MineEss extends Action {

    ABCUtil abc;
    public MineEss(ABCUtil abc) {
        this.abc = abc;
    }

    @Override
    public void activate() {
        Walker.enableRun();
        switch (getState()) {
            case MINE:

                Vars.setState("CLICKING RUNESTONE");
                mineRunestone();
                break;
            case MINING:
                Vars.setState("CURRENTLY MINING, PERFORMING ANTIBAN");
                RSObject[] rocks = Objects.findNearest(50, "Dense runestone");
                for (RSObject r : rocks) {
                    if (!r.isClickable())
                        Camera.turnToTile(r);
                }
                randomAntiBan();
                General.sleep(1000, 1800);
                break;
        }
    }

    @Override
    public boolean active() {
        return Walker.areaContainsPlayer(Constants.MINING_AREA) && !Inventory.isFull();
    }

    private enum SubState {
        MINE, MINING
    }

    private SubState getState() {
        if (Player.getAnimation() >= 0) {
            return SubState.MINING;
        }
        else {
            return SubState.MINE;
        }
    }

    private void mineRunestone() {
        RSObject[] rock = Objects.findNearest(50, "Dense runestone");
        if (rock.length >=1)
            if (rock[0] != null) {
                if (!rock[0].isClickable()) {
                    Camera.turnToTile(rock[0]);
                }
                if (rock[0].click("Chip")) {
                    Timing.waitCondition(new Condition() {

                        @Override
                        public boolean active() {
                            return Player.getAnimation() >= 0;
                        }
                    }, General.random(2000, 3000));
                }
            }
    }

    private void randomAntiBan() {
        Random rando = new Random();

        int num = rando.nextInt(6);

        if (num == 0) {
            if (abc.shouldCheckTabs())
                abc.checkTabs();
        } else if (num == 1) {
            if (abc.shouldCheckXP())
                abc.checkXP();
        } else if (num == 2) {
            if (abc.shouldExamineEntity())
                abc.examineEntity();
        } else if (num == 3) {
            if (abc.shouldMoveMouse())
                abc.moveMouse();
        } else if (num == 4) {
            if (abc.shouldRotateCamera())
                abc.rotateCamera();
        } else if (num == 5) {
            if (abc.shouldPickupMouse())
                abc.pickupMouse();
        } else if (num == 6) {
            if (abc.shouldRightClick())
                abc.rightClick();
        }
    }
}
