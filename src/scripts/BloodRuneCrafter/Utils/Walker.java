package scripts.BloodRuneCrafter.Utils;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Options;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSInterfaceChild;

public class Walker {


    public static boolean areaContainsPlayer(RSArea area) {
        General.println(Player.getPosition().distanceToDouble(area.getRandomTile()));
        return area.contains(Player.getPosition()) ||
                Player.getPosition().distanceToDouble(area.getRandomTile()) <= 7.0;
    }
    public static void walkTo(Positionable pos, Condition con) {

        if (Walking.walkPath(Walking.generateStraightPath(pos))) {
            Timing.waitCondition(con, General.random(3000, 5000));
        }
    }

    public static void enableRun() {
        if (!Options.isRunEnabled()) {
            RSInterfaceChild pray = Interfaces.get(160).getChild(24);
            Mouse.clickBox((int) pray.getAbsoluteBounds().getMinX(), (int)pray.getAbsoluteBounds().getMinY(),
                    (int) pray.getAbsoluteBounds().getMaxX(), (int) pray.getAbsoluteBounds().getMaxY(),1);
        }

    }
}
