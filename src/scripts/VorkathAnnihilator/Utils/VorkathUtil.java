package scripts.VorkathAnnihilator.Utils;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSTile;
import scripts.VorkathAnnihilator.Data.Variables;

public class VorkathUtil {

    public static void slimeWalk() {
        RSTile tile1;
        RSTile tile2;
        RSTile farLeft;
        RSTile farRight;
        RSArea safeArea;
        if (Player.getPosition().getX() < Variables.vorkinstance.getFightTile().getX() && Variables.vorkinstance.getSlimeRight().isOnScreen()) {
            tile1 = Variables.vorkinstance.getSlimeRight();
            tile2 = Variables.vorkinstance.getSlimeLeft();
            farRight = new RSTile(Variables.vorkinstance.getSlimeRight().getX() + 2, Variables.vorkinstance.getSlimeRight().getY(), 0);
            farLeft = new RSTile(Variables.vorkinstance.getSlimeLeft().getX() - 2, Variables.vorkinstance.getSlimeLeft().getY(), 0);
        }
        else {
            tile1 =  Variables.vorkinstance.getSlimeLeft();
            tile2 = Variables.vorkinstance.getSlimeRight();
            farRight = new RSTile(Variables.vorkinstance.getSlimeLeft().getX() - 2, Variables.vorkinstance.getSlimeRight().getY(), 0);
            farLeft = new RSTile(Variables.vorkinstance.getSlimeRight().getX() + 2, Variables.vorkinstance.getSlimeLeft().getY(), 0);
        }
        safeArea = new RSArea(farLeft,farRight);
        DynamicClicking.clickRSTile(tile1, "Walk here");
        deactivateQuickPrayer();
        while (Variables.slimePhase) {
            long time = System.currentTimeMillis() - Variables.slimePhaseStart;
            if (time < 5000 || !safeArea.contains(Player.getPosition())) {
                if ((Player.getPosition().distanceToDouble(tile1) >= 2.85)) {
                    DynamicClicking.clickRSTile(tile1, "Walk here");
                    if (FoodUtil.lowHealth(43) && Game.getDestination() != null && safeArea.contains(Player.getPosition()))
                        FoodUtil.eat();

                } else if ((Player.getPosition().distanceTo(tile2) > 2)) {
                    DynamicClicking.clickRSTile(tile2, "Walk here");
                    if (FoodUtil.lowHealth(43) && Game.getDestination() != null && safeArea.contains(Player.getPosition()))
                        FoodUtil.eat();

                }
            }
            else {
                if ((Player.getPosition().distanceTo(tile1) > 2)) {
                    DynamicClicking.clickRSTile(farRight, "Walk here");
                    if (FoodUtil.lowHealth(43)  && Game.getDestination() != null && safeArea.contains(Player.getPosition()) )  FoodUtil.eat();;

                } else if ((Player.getPosition().distanceTo(tile2) > 2)) {
                    DynamicClicking.clickRSTile(farLeft, "Walk here");
                    if (FoodUtil.lowHealth(43) && Game.getDestination() != null && safeArea.contains(Player.getPosition()))  FoodUtil.eat();

                }
            }
            if (Objects.findNearest(50, 32000).length < 1 && time > 6000) {
                Variables.setState("SLIME PHASE END");
                Variables.slimePhase = false;
                Variables.vorkinstance.getFightTile().click("Walk here");
                enableRun();
            }
            General.sleep(50,150);
        }
    }
    public static void enableRun() {
        if (!Options.isRunEnabled()) {
            RSInterfaceChild pray = Interfaces.get(160).getChild(24);
            Mouse.clickBox((int) pray.getAbsoluteBounds().getMinX(), (int)pray.getAbsoluteBounds().getMinY(),
                    (int) pray.getAbsoluteBounds().getMaxX(), (int) pray.getAbsoluteBounds().getMaxY(),1);
        }

    }
    public static void disableRun() {
        if (Options.isRunEnabled()) {
            RSInterfaceChild pray = Interfaces.get(160).getChild(24);
            Mouse.clickBox((int) pray.getAbsoluteBounds().getMinX(), (int)pray.getAbsoluteBounds().getMinY(),
                    (int) pray.getAbsoluteBounds().getMaxX(), (int) pray.getAbsoluteBounds().getMaxY(),1);
        }
    }

    public static void activateQuickPrayer() {
        if (!Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MISSILES)) {
            RSInterfaceChild pray = Interfaces.get(160).getChild(16);
            Mouse.clickBox((int) pray.getAbsoluteBounds().getMinX(), (int)pray.getAbsoluteBounds().getMinY(),
                    (int) pray.getAbsoluteBounds().getMaxX(), (int) pray.getAbsoluteBounds().getMaxY(),1);
            General.sleep(350, 500);
        }

    }


    public static void deactivateQuickPrayer() {
        if (Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MISSILES)) {
            RSInterfaceChild pray = Interfaces.get(160).getChild(16);
            Mouse.clickBox((int) pray.getAbsoluteBounds().getMinX(), (int)pray.getAbsoluteBounds().getMinY(),
                    (int) pray.getAbsoluteBounds().getMaxX(), (int) pray.getAbsoluteBounds().getMaxY(),1);
            General.sleep(350, 500);
        }
    }
}
