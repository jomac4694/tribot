package scripts.BloodRuneCrafter.Actions;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.BloodRuneCrafter.Data.Constants;
import scripts.BloodRuneCrafter.Data.Vars;
import scripts.BloodRuneCrafter.Utils.Walker;

public class WalkToMine extends Action {


    @Override
    public void activate() {
        Walker.enableRun();
        switch (getState()) {
            case CLIMB_OBS:
                Vars.setState("CLIMBING OBSTACLE");
                climbObs();
                break;
            case WALK_TO_OBS:
                Vars.setState("WALKING TO OBSTACLE");
                walkToObs();
                break;
            case WALK_TO_MINE:
                Vars.setState("WALKING TO MINE");
                walkToMine();
                break;
            case DEFAULT:
                Vars.setState("DEFAULT STATE");
                General.sleep(100, 200);
                break;
        }
    }

    @Override
    public boolean active() {
        return !Inventory.isFull() &&
                (Walker.areaContainsPlayer(Constants.DARK_ALTAR_AREA)
                || Walker.areaContainsPlayer(Constants.OBS1_AREA_NORTH)
                || Walker.areaContainsPlayer(Constants.OBS1_AREA_SOUTH));
    }

    private enum SubState {
        DEFAULT, WALK_TO_OBS, CLIMB_OBS, WALK_TO_MINE
    }

    private void walkToObs() {
        if (Walking.walkPath(Walking.generateStraightPath(Constants.OBS1_TILE))) {
            Timing.waitCondition(new Condition() {

                @Override
                public boolean active() {
                    General.sleep(100, 250);
                    return Constants.OBS1_AREA_NORTH.contains(Player.getPosition());
                }
            }, General.random(1000, 1800));
        }
    }
    private void walkToMine() {
        if (Walking.walkPath(Walking.generateStraightPath(new RSTile(1762, 3853, 0)))) {
            Timing.waitCondition(new Condition() {

                @Override
                public boolean active() {
                    General.sleep(100, 250);
                    return Constants.MINING_AREA.contains(Player.getPosition());
                }
            }, General.random(1000, 1800));
        }
    }
    private SubState getState() {
        if (Walker.areaContainsPlayer(Constants.DARK_ALTAR_AREA)) {
            return SubState.WALK_TO_OBS;
        } else if (!Player.getPosition().equals(Constants.OBS1_TILE2) && Walker.areaContainsPlayer(Constants.OBS1_AREA_NORTH)) {
            return SubState.CLIMB_OBS;
        } else if (Walker.areaContainsPlayer(Constants.OBS1_AREA_SOUTH)) {
            return SubState.WALK_TO_MINE;
        }
        else {
            return SubState.DEFAULT;
        }
    }

    private void climbObs() {
        RSObject[] obs = Objects.findNearest(10, Constants.NORTH_ROCK);
        if (obs.length >= 1)
            if (obs[0] != null)
                if (!obs[0].isClickable()) {
                    Camera.turnToTile(obs[0]);
                }
                if (obs.length >= 1 && obs[0] != null) {
                    if (obs[0].click("Climb")) {
                        Timing.waitCondition(new Condition() {

                            @Override
                            public boolean active() {
                                General.sleep(100, 250);
                                return Constants.OBS1_AREA_SOUTH.contains(Player.getPosition());
                            }
                        }, General.random(1000, 1800));
                    }
                }
    }

}
