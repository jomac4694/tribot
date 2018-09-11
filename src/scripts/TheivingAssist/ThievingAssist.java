package scripts.TheivingAssist;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.EventBlockingOverride;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

@ScriptManifest(name = "Thievingassist", category = "Thieving", authors = "Joe")
public class ThievingAssist extends Script implements EventBlockingOverride {
    @Override
    public void run() {

        while (true) {
            if (Inventory.getCount(22521) >= 5) {
                Inventory.find(22521)[0].click("Open-all");
            }
            else {
                General.sleep(500, 1000);
            }
            General.sleep(500);
        }
    }

    @Override
    public OVERRIDE_RETURN overrideKeyEvent(KeyEvent keyEvent) {
        return null;
    }

    @Override
    public OVERRIDE_RETURN overrideMouseEvent(MouseEvent arg0) {
        if (Inventory.getCount(22521) >= 5) {
            arg0.consume();
            return OVERRIDE_RETURN.DISMISS;
        } else{
            return OVERRIDE_RETURN.PROCESS;
        }
    }
}
