package scripts.BloodRuneCrafter.Actions;


import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSObjectDefinition;
import scripts.BloodRuneCrafter.Data.Constants;
import scripts.BloodRuneCrafter.Data.Vars;
import scripts.BloodRuneCrafter.Utils.Walker;

public class BindRunes extends Action {


    @Override
    public void activate() {
        switch (getState()) {
            case WALK_TO_ALTAR_AREA:
                Vars.setState("WALKING TO ALTAR AREA 1");
                General.println("WALKING TO ATLAR AREA");
                Walker.walkTo(Constants.ALTAR_AREA_TILE1, new Condition() {

                    @Override
                    public boolean active() {
                        return Walker.areaContainsPlayer(Constants.TO_ATLAR_AREA);
                    }
                });
                break;
            case WALK_TO_ATLAR_AREA2:
                Vars.setState("WALKING TO ALTAR AREA 2");
                Walker.walkTo(Constants.ALTAR_AREA_TILE2, new Condition() {

                    @Override
                    public boolean active() {
                        return Walker.areaContainsPlayer(Constants.TO_ATLAR_AREA2);
                    }
                });
                break;
            case WALK_TO_ALTAR:
                Vars.setState("WALKING TO BLOOD ALTAR");
                Walker.walkTo(Constants.BLOOD_ATLAR_AREA.getRandomTile(), new Condition() {

                    @Override
                    public boolean active() {
                        return Walker.areaContainsPlayer(Constants.BLOOD_ATLAR_AREA);
                    }
                });
                break;
            case WALK_TO_ALTAR_AREA3:
                Vars.setState("WALKING TO ALTAR AREA 3");
                Walker.walkTo(Constants.ALTAR_AREA_TILE3, new Condition() {

                    @Override
                    public boolean active() {
                        return Walker.areaContainsPlayer(Constants.TO_ATLAR_AREA3);
                    }
                });
                break;
            case WALK_TO_ALTAR_AREA4:
                Vars.setState("WALKING TO ALTAR AREA 4");
                Walker.walkTo(Constants.ALTAR_AREA_TILE4, new Condition() {

                    @Override
                    public boolean active() {
                        return Walker.areaContainsPlayer(Constants.TO_ALTAR_AREA4);
                    }
                });
                break;
            case BREAK_ESS:
                Vars.setState("BREAKING ESSENCE");
                breakEss();
                break;
            case BIND_RUNES:
                Vars.setState("BINDING RUNES");
                bindRunes();
                break;
            case DEFAULT:
                Vars.setState("DEFAULT STATE");
                General.sleep(100, 200);
                break;

        }
    }

    @Override
    public boolean active() {
        return   Walker.areaContainsPlayer(Constants.BLOOD_ATLAR_AREA) || Inventory.getCount(13446) >= 1 && Inventory.getCount(7938) >= 1 &&
                (Walker.areaContainsPlayer(Constants.DARK_ALTAR_AREA)
                || Walker.areaContainsPlayer(Constants.TO_ATLAR_AREA)
                || Walker.areaContainsPlayer(Constants.TO_ATLAR_AREA2)
                        || Walker.areaContainsPlayer(Constants.TO_ATLAR_AREA3)
                        || Walker.areaContainsPlayer(Constants.TO_ALTAR_AREA4));
    }


    private enum SubState {
        WALK_TO_ALTAR_AREA4, DEFAULT, WALK_TO_ALTAR_AREA, WALK_TO_ATLAR_AREA2, WALK_TO_ALTAR_AREA3, WALK_TO_ALTAR, BIND_RUNES, BREAK_ESS
    }

    private void breakEss() {
        while (Inventory.getCount(13446) >= 1) {
            RSItem[] chisel = Inventory.find(1755);
            RSItem[] ess = Inventory.find(13446);

            if (chisel.length >= 1 && ess.length >= 1)
                if (chisel[0] != null && ess[0] != null) {
                    if (Inventory.getCount(13446) >= 1)
                    ess[ess.length - 1].click("Use");
                    if (Inventory.getCount(13446) >= 1)
                    chisel[0].click("Use");
                }
        }
    }

    private SubState getState() {
        if (Walker.areaContainsPlayer(Constants.DARK_ALTAR_AREA)) {
            return SubState.WALK_TO_ALTAR_AREA;
        } else if (Walker.areaContainsPlayer(Constants.TO_ATLAR_AREA)) {
            return SubState.WALK_TO_ATLAR_AREA2;
        } else if (Walker.areaContainsPlayer(Constants.TO_ALTAR_AREA4)) {
            return SubState.WALK_TO_ALTAR;
        } else if (Walker.areaContainsPlayer(Constants.TO_ATLAR_AREA3)) {
            return SubState.WALK_TO_ALTAR_AREA4;
        } else if (Walker.areaContainsPlayer(Constants.BLOOD_ATLAR_AREA) && Inventory.getCount(7938) >= 1) {
            return SubState.BIND_RUNES;
        } else if (Walker.areaContainsPlayer(Constants.BLOOD_ATLAR_AREA) && Inventory.getCount(13446) >= 1) {
            return SubState.BREAK_ESS;
        } else if (Walker.areaContainsPlayer(Constants.TO_ATLAR_AREA2)) {
            return SubState.WALK_TO_ALTAR_AREA3;
        }
        else {
            return SubState.DEFAULT;
        }

    }

    private void bindRunes() {
        RSObject[] altar = Objects.findNearest(15, Constants.BLOOD_ALTAR);

        if (altar.length >=1)
            if (altar[0] != null) {

                altar[0].hover();
                for (String s : ChooseOption.getOptions()) {
                    if (s.contains("Use")) {
                        Mouse.click(1);
                    }
                }
                if (altar[0].click("Bind")) {
                    General.sleep(1000, 2000);
                }
            }
    }
}
