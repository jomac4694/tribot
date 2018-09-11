package scripts.BloodRuneCrafter.Actions;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.BloodRuneCrafter.Data.Constants;
import scripts.BloodRuneCrafter.Data.Vars;
import scripts.BloodRuneCrafter.Utils.Walker;

public class VenerateEss extends Action {
    @Override
    public void activate() {
        Walker.enableRun();
        switch (getState()) {
            case WALK_TO_OBS:
                Vars.setState("WALKING TO OBSTACLE");
                walkToObs();
                break;
            case CLIMB_OBS:
                Vars.setState("CLIMBING OBSTACLE");
                climbObs();
                break;
            case WALK_TO_ALTAR:
                Vars.setState("WALKING TO DARK ALTAR");
                walkToAltar();
                break;
            case VENERATE_ESS:
                Vars.setState("VENERATING ESSENCE");
                venerateEss();
                break;
            case BREAK_ESS:
                Vars.setState("BREAKING ESS");
                breakEss();
                break;
        }
    }

    @Override
    public boolean active() {
        return (Inventory.getCount(13445) >= 1 ||Inventory.getCount(13446) >= 1)  && Inventory.isFull() && (Constants.MINING_AREA.contains(Player.getPosition())
                || Player.getPosition().equals(Constants.OBS1_TILE)
                || Walker.areaContainsPlayer(Constants.OBS1_AREA_NORTH)
                || Walker.areaContainsPlayer(Constants.DARK_ALTAR_AREA));
    }


    private enum SubState {
        WALK_TO_OBS, CLIMB_OBS, WALK_TO_ALTAR, VENERATE_ESS, BREAK_ESS
    }

    private SubState getState() {
        if (Walker.areaContainsPlayer(Constants.MINING_AREA)) {
            return SubState.WALK_TO_OBS;
        }
        else if (Walker.areaContainsPlayer(Constants.OBS1_AREA_SOUTH) && !Player.getPosition().equals(Constants.OBS1_TILE)) {
            return SubState.CLIMB_OBS;
        }
        else if (Walker.areaContainsPlayer(Constants.DARK_ALTAR_AREA) && Inventory.getCount(13445) >= 1) {
            return SubState.VENERATE_ESS;
        }
        else if (Inventory.getCount(13446) >= 1 && Inventory.getCount(7938) <= 0) {
            return SubState.BREAK_ESS;
        }
        else {
            return SubState.WALK_TO_ALTAR;
        }
    }

    private void walkToObs() {
        Walker.enableRun();
        Camera.setCameraAngle(90);
        Camera.setCameraRotation(0);
        if (Walking.walkPath(Walking.generateStraightPath(Constants.OBS2_AREA_SOUTH.getRandomTile()))) {
            Timing.waitCondition(new Condition() {

                @Override
                public boolean active() {
                    General.sleep(100, 250);
                    return Walker.areaContainsPlayer(Constants.OBS2_AREA_SOUTH);
                }
            }, General.random(1000, 2000));
        }
        if (Walking.walkPath(Constants.OBS1_PATH)) {
            Timing.waitCondition(new Condition() {

                @Override
                public boolean active() {
                    General.sleep(100, 250);
                    return Walker.areaContainsPlayer(Constants.OBS1_AREA_SOUTH);
                }
            }, General.random(1000, 2000));
        }
    }

    private void climbObs() {
        RSObject[] obs = Objects.findNearest(10, Constants.SOUTH_ROCK);
        if (obs.length >= 1)
            if (obs[0] != null && !Player.isMoving())
                if (!obs[0].isClickable()) {
                    Camera.turnToTile(obs[0]);
                }
                if (obs[0].click("Climb")) {
                    General.sleep(300, 700);
                    Timing.waitCondition(new Condition() {

                        @Override
                        public boolean active() {
                            General.sleep(100, 250);
                            return Walker.areaContainsPlayer(Constants.OBS1_AREA_NORTH)
                                    && Player.getAnimation() < 0;
                        }
                    }, General.random(1000, 2000));
                }
    }

    private void walkToAltar() {
        if (Walking.walkPath(Walking.generateStraightPath(Constants.DARK_ALTAR_AREA.getRandomTile()))) {
            Timing.waitCondition(new Condition() {

                @Override
                public boolean active() {
                    General.sleep(100, 250);
                    return Walker.areaContainsPlayer(Constants.DARK_ALTAR_AREA);
                }
            }, General.random(1000, 1800));
        }
    }

    private void venerateEss() {
        RSObject[] altar = Objects.find(20, "Dark Altar");
        if (altar.length >= 1)
            if (altar[0] != null)
                if (altar[0].click("Venerate")) {
                    General.sleep(1000, 2200);
                }
    }

    private void breakEss() {
        while (Inventory.getCount(13446) >= 1) {
            RSItem[] chisel = Inventory.find(1755);
            RSItem[] ess = Inventory.find(13446);

            if (chisel.length >= 1 && ess.length >= 1)
                if (chisel[0] != null && ess[ess.length -1] != null) {
                    if (Inventory.getCount(13446) >= 1)
                        ess[ess.length - 1].click("Use");
                    if (Inventory.getCount(13446) >= 1)
                        chisel[0].click("Use");

                }
        }
    }
}
