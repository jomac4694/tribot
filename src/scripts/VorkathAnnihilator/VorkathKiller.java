package scripts.VorkathAnnihilator;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Projection;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;
import scripts.VorkathAnnihilator.Actions.Action;
import scripts.VorkathAnnihilator.Actions.Banking.Bank;
import scripts.VorkathAnnihilator.Actions.Banking.BoardBoat;
import scripts.VorkathAnnihilator.Actions.Banking.House;
import scripts.VorkathAnnihilator.Actions.Banking.WalkToVorkath;
import scripts.VorkathAnnihilator.Actions.FightThreads.DodgeBomb;
import scripts.VorkathAnnihilator.Actions.FightThreads.SlimeThread;
import scripts.VorkathAnnihilator.Actions.FightThreads.VorkathFight;
import scripts.VorkathAnnihilator.Data.Variables;
import scripts.VorkathAnnihilator.Utils.ACamera;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;

@ScriptManifest(name = "Vorkath Killer v1.0", category = "Vorkath", authors = "Joe")
public class VorkathKiller extends Script implements Painting{


    @Override
    public void run() {
        for(Thread thread:Thread.getAllStackTraces().keySet()){
            if(thread.getName().contains("Antiban")||thread.getName().contains("Fatigue")){
                thread.suspend();
            }
        }
        ACamera camera = new ACamera(this);
        Variables.TESTING = JOptionPane.showInputDialog(null, "Are you testing?");
        Variables.FOOD_ID = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter food ID"));
        Variables.t = new Thread(new DodgeBomb(camera));
        Variables.t2 = new SlimeThread();
        Variables.t3 = new Thread(new VorkathFight(camera));
        Mouse.setSpeed(5000);
        ArrayList<scripts.VorkathAnnihilator.Actions.Action> actions = new ArrayList<>();
        Collections.addAll(actions, new House(camera), new Bank(camera), new BoardBoat(camera), new WalkToVorkath(camera));
        Variables.t.start();
        Variables.t2.start();
        Variables.t3.start();
        Variables.startTime = System.currentTimeMillis();
        while (true) {
            for (Action a : actions) {
                if (a.active()){
                    a.activate();
                }
            }


        }
    }

    private String time(long time) {
        long seconds = time /1000;
        long minutes = time / 1000 / 60;
        long hours = time / 1000 / 60 / 60;
        return hours % 24 + " : " + minutes % 60 + " : " + seconds % 60;
    }


    @Override
    public void onPaint(Graphics graphics) {
        Variables.timePassed = System.currentTimeMillis() - Variables.startTime;
        Variables.moneyPerHr = (long) (Variables.profit * ((double) (3600000/Variables.timePassed)));
        graphics.setColor(Color.RED);
        graphics.drawString("Current Script State: " + Variables.CURRENT_SCRIPT_STATE, 50, 50);
        graphics.drawString("Previous Script State: " + Variables.PREVIOUS_SCRIPT_STATE, 50, 65);
        graphics.drawString(time(Variables.timePassed), 50, 80);
        graphics.drawString("Profit: " + Variables.profit + "  (" + Variables.moneyPerHr + ")" , 50, 95);
        graphics.drawPolygon(Projection.getTileBoundsPoly(Variables.bombTile, 0));
    }



}
