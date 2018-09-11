package scripts.TheivingAssist;

import org.tribot.api2007.Inventory;
import org.tribot.script.interfaces.EventBlockingOverride;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class OpenPouch implements EventBlockingOverride {


    public boolean active() {
        return Inventory.getCount(22521) >= 5;
    }

    public void activate() {
        Inventory.find(22521)[0].click("Open-all");
    }
    @Override
    public OVERRIDE_RETURN overrideKeyEvent(KeyEvent arg0) {
        if (Inventory.getCount(22521) >= 5) {
            arg0.consume();
            return OVERRIDE_RETURN.DISMISS;
        } else{
            return OVERRIDE_RETURN.PROCESS;
        }
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
