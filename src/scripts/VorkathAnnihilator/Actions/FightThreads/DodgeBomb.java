package scripts.VorkathAnnihilator.Actions.FightThreads;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Player;
import org.tribot.api2007.Projectiles;
import org.tribot.api2007.types.RSProjectile;
import org.tribot.api2007.types.RSTile;
import scripts.VorkathAnnihilator.Utils.ACamera;
import scripts.VorkathAnnihilator.Actions.Action;
import scripts.VorkathAnnihilator.Data.Variables;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DodgeBomb extends Action implements Runnable {

    long totalTime = System.currentTimeMillis() - Variables.bombRunStartTime;

    public DodgeBomb(ACamera camera) {
        super(camera);
    }

    @Override
    public void run() {
        Mouse.setSpeed(5000);
        if (!droppingBomb() && Variables.safeFromBomb) {
            General.sleep(600);
        }

        while (true) {
            if (!active()) {
                General.sleep(350, 500);
                continue;
            }
            else {
                activate();
            }
        }
    }
    public void dodge() {
        totalTime = System.currentTimeMillis() - Variables.bombRunStartTime;
        Variables.setState("DODGING BOMB");
        if (totalTime > 2600 && Variables.bombTile.getX() < Player.getPosition().getX() && Math.abs(Variables.bombTile.getX() - Player.getPosition().getX()) < 2) {
            Variables.expectedTile = new RSTile(Player.getPosition().getX() + 4, Player.getPosition().getY(), 0);
                DynamicClicking.clickRSTile(Variables.expectedTile, "Walk here");
                Variables.bombRunStartTime = System.currentTimeMillis();
                Variables.droppingBomb = false;
                Timing.waitCondition(new Condition() {

                    @Override
                    public boolean active() {
                        General.sleep(100, 200);
                        return !Player.isMoving();
                    }
                }, General.random(100, 200));

        } else if (totalTime > 2600 && Variables.bombTile.getX() > Player.getPosition().getX() && Math.abs(Variables.bombTile.getX() - Player.getPosition().getX()) < 2) {
            Variables.expectedTile = new RSTile(Player.getPosition().getX() - 4, Player.getPosition().getY(), 0);
            DynamicClicking.clickRSTile(Variables.expectedTile, "Walk here");
                Variables.bombRunStartTime = System.currentTimeMillis();
                Variables.droppingBomb = false;
                Timing.waitCondition(new Condition() {

                    @Override
                    public boolean active() {
                        General.sleep(100, 200);
                        return !Player.isMoving();
                    }
                }, General.random(100, 200));

        } else if ((totalTime > 2600 && Player.getPosition().getX() > Variables.vorkinstance.getFightTile().getX() && Math.abs(Variables.bombTile.getX() - Player.getPosition().getX()) < 2)) {
            Variables.expectedTile = new RSTile(Player.getPosition().getX() - 4, Player.getPosition().getY(), 0);
            DynamicClicking.clickRSTile(Variables.expectedTile, "Walk here");
                Variables.bombRunStartTime = System.currentTimeMillis();
                Variables.droppingBomb = false;
                Timing.waitCondition(new Condition() {

                    @Override
                    public boolean active() {
                        General.sleep(100, 200);
                        return !Player.isMoving();
                    }
                }, General.random(100, 200));

        }else if ((totalTime > 2600 && Math.abs(Variables.bombTile.getX() - Player.getPosition().getX()) < 2) || (Variables.droppingBomb && Math.abs(Variables.bombTile.getX() - Player.getPosition().getX()) < 2)) {
            Variables.expectedTile = new RSTile(Player.getPosition().getX() + 4, Player.getPosition().getY(), 0);
            DynamicClicking.clickRSTile(Variables.expectedTile, "Walk here");
                Variables.bombRunStartTime = System.currentTimeMillis();
                Variables.droppingBomb = false;
                Timing.waitCondition(new Condition() {

                    @Override
                    public boolean active() {
                        General.sleep(100, 200);
                        return !Player.isMoving();
                    }
                }, General.random(100, 200));
        }


    }
    public boolean droppingBomb() {
        RSProjectile[] projects = Projectiles.getAll();
        for (RSProjectile p : projects) {
            if (p.getGraphicID() == 1481) {
                General.sleep(200, 250);
                if (totalTime > 2500) {
                    Variables.bombTile = Player.getPosition();
                }
                return true;
            }
        }
        Variables.droppingBomb = false;
        return false;
    }

    @Override
    public boolean active() {
        return droppingBomb();
    }

    @Override
    public void activate() {
        General.println("DODGE THREAD ACTIVATED");
        Variables.droppingBomb = true;
        dodge();

    }
}
