package scripts;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;
import scripts.BloodRuneCrafter.Data.Constants;

import java.awt.*;
@ScriptManifest(name = "Tester", category = "Vorkath", authors = "Me")
public class CodePieceTester extends Script implements Painting {
    public void activateQuickPrayer() {
        if (!Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MISSILES)) {
            RSInterfaceChild pray = Interfaces.get(160).getChild(16);
            Mouse.clickBox((int) pray.getAbsoluteBounds().getMinX(), (int)pray.getAbsoluteBounds().getMinY(),
                    (int) pray.getAbsoluteBounds().getMaxX(), (int) pray.getAbsoluteBounds().getMaxY(),1);
        }
    }

    public void deactivateQuickPrayer() {
        if (Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MISSILES)) {
            RSInterfaceChild pray = Interfaces.get(160).getChild(16);
            Mouse.clickBox((int) pray.getAbsoluteBounds().getMinX(), (int)pray.getAbsoluteBounds().getMinY(),
                    (int) pray.getAbsoluteBounds().getMaxX(), (int) pray.getAbsoluteBounds().getMaxY(),1);
        }
    }
    @Override
    public void run() {
        while (true) {

            WebWalking.walkTo(Constants.DARK_ALTAR_AREA.getRandomTile());
            sleep(2000);
        }
    }

    @Override
    public void onPaint(Graphics graphics) {

        graphics.setColor(Color.red);
            RSObject[] rock = Objects.findNearest(50, "Depleted Runestone");
            if (rock.length >= 1) {
                if (rock[0] != null) {
                    RSModel model = rock[0].getModel();
                    if (model != null) {
                        graphics.drawPolygon(model.getEnclosedArea());
                    }
                }
            }


        graphics.setColor(Color.red);
        RSObject[] rocks = Objects.findNearest(50,"Dense Runestone");
        if (rocks.length >= 1) {
            for (RSObject r : rocks) {
                if (r != null) {
                    RSModel model = r.getModel();
                    if (model != null) {
                        graphics.drawPolygon(model.getEnclosedArea());
                    }
                }
            }
        }
    }

}
