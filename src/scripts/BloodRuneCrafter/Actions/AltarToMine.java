package scripts.BloodRuneCrafter.Actions;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.BloodRuneCrafter.Data.Constants;
import scripts.BloodRuneCrafter.Data.Vars;
import scripts.BloodRuneCrafter.Utils.Walker;

public class AltarToMine extends Action{


    @Override
    public void activate() {
        Walker.enableRun();
        switch (getState()) {
            case WALK_TO_OBS:
                Vars.setState("WALKING TO OBSTACLE");
                Walker.walkTo(Constants.OBS2_AREA_NORTH.getRandomTile(), new Condition() {

                    @Override
                    public boolean active() {
                        return Constants.OBS2_AREA_NORTH.contains(Player.getPosition());
                    }
                });
                break;
            case WALK_TO_MINE:
                Vars.setState("WALKING TO MINE");
                Walker.walkTo(new RSTile(1762, 3853, 0), new Condition() {

                    @Override
                    public boolean active() {
                        return Constants.MINING_AREA.contains(Player.getPosition());
                    }
                });
                break;
            case CLIMB_OBS:
                Vars.setState("CLIMBING OBSTACLE");
                climbObs();
                break;

        }
    }

    @Override
    public boolean active() {
        return (Walker.areaContainsPlayer(Constants.OBS2_AREA_SOUTH) || Walker.areaContainsPlayer(Constants.OBS2_AREA_NORTH) ||
                Walker.areaContainsPlayer(Constants.BLOOD_ATLAR_AREA)) && (Inventory.getCount(13446) <= 0
                && Inventory.getCount(7938) <= 0) && !Inventory.isFull();
    }

    private enum SubState {
        WALK_TO_OBS, CLIMB_OBS, WALK_TO_MINE
    }

    private SubState getState() {
        if (Walker.areaContainsPlayer(Constants.BLOOD_ATLAR_AREA)) {
            return SubState.WALK_TO_OBS;
        } else if (Walker.areaContainsPlayer(Constants.OBS2_AREA_NORTH)) {
            return SubState.CLIMB_OBS;
        } else {
            return SubState.WALK_TO_MINE;
        }
    }

    private void climbObs() {
        RSObject[] obs = Objects.findNearest(10, Constants.NORTH_ROCK);
        if (obs.length >= 1)
            if (obs[0] != null)
                if (!obs[0].isClickable()) {
                    Camera.turnToTile(obs[0]);
                }
                if (obs[0].click("Climb")) {
                    Timing.waitCondition(new Condition() {

                        @Override
                        public boolean active() {
                            General.sleep(100, 250);
                            return Constants.OBS2_AREA_SOUTH.contains(Player.getPosition());
                        }
                    }, General.random(1000, 1800));
                }
    }
}
