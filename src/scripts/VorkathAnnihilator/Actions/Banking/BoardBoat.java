package scripts.VorkathAnnihilator.Actions.Banking;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import scripts.VorkathAnnihilator.Utils.ACamera;
import scripts.VorkathAnnihilator.Actions.Action;
import scripts.VorkathAnnihilator.Data.Variables;

public class BoardBoat extends Action {

    RSArea fremmyArea;
    RSArea dockArea;

    public BoardBoat(ACamera camera) {
        super(camera);
        fremmyArea = new RSArea(new RSTile(2646,3671,0), new RSTile(2637,3682,0));
        dockArea = new RSArea(new RSTile(2641,3690,0),new RSTile(2640,3699,9));
    }
    @Override
    public boolean active() {
        return fremmyArea.contains(Player.getPosition()) || dockArea.contains(Player.getPosition());
    }

    @Override
    public void activate() {

        switch (getState()) {
            case WALK_TO_DOCK:
                Variables.setState("WALKING TO DOCK");
                walkToDock();
                break;
            case TRAVEL:
                Variables.setState("TRAVELING TO VORK");
                travel();
                break;
        }
    }

    private enum SubState {
        WALK_TO_DOCK, TRAVEL
    }

    private SubState getState() {
        if (fremmyArea.contains(Player.getPosition())) {
            return SubState.WALK_TO_DOCK;
        }
        else if (dockArea.contains(Player.getPosition())) {
            return SubState.TRAVEL;
        }
        else {
            return SubState.TRAVEL;
        }
    }

    private void travel() {
        RSNPC[] torfinn = NPCs.findNearest(7504);
        if (torfinn.length >=1) {
            if (torfinn[0].click("Travel")) {
                Timing.waitCondition(new Condition() {

                    @Override
                    public boolean active() {
                        General.sleep(100, 150);
                        return !dockArea.contains(Player.getPosition());
                    }
                }, General.random(1000, 1200));
            }
        }
    }
    private void walkToDock() {
        RSTile[] path = Walking.generateStraightPath(dockArea.getRandomTile());
        if (Walking.walkPath(path)) {
            Timing.waitCondition(new Condition() {

                @Override
                public boolean active() {
                    return dockArea.contains(Player.getPosition());
                }
            }, General.random(1000, 1500));
        }
    }
}
