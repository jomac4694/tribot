package scripts.VorkathAnnihilator;

import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

public class VorkathInstance {

    private RSTile areaTile1;
    private RSTile areaTile2;
    private RSTile startTile;
    private RSTile tile1;
    private RSTile tile2;
    private RSTile fightTile;
    private RSArea area;

    public VorkathInstance(RSTile startTile) {
        this.startTile = startTile;
        init(this.startTile);
    }

    private void init(RSTile startTile) {
        tile1 = new RSTile(startTile.getX() + 3, startTile.getY(), 0);
        tile2 = new RSTile(startTile.getX() - 3, startTile.getY(), 0);
        areaTile1 = new RSTile(startTile.getX() +5, startTile.getY()+2, 0);
        areaTile2 = new RSTile(startTile.getX() -5, startTile.getY()+5, 0);
        fightTile = new RSTile(startTile.getX(), startTile.getY() + 4, 0);
        area = new RSArea(areaTile1, areaTile2);
    }

    public RSTile getSlimeLeft() {
        return tile2;
    }
    public RSTile getSlimeRight() {
        return tile1;
    }
    public RSTile getFightTile() {
        return fightTile;
    }
    public RSArea getArea() {
        return area;
    }


}
