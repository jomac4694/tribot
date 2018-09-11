package scripts.BloodRuneCrafter.Data;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

public class Constants {
    //items
    public static final int DARK__ESS_BLOCK = 13446;
    public static final int CHISEL = 1755;
    public static final int NORMAL_ESS_BLOCK = 13445;
    public static final int ESS_FRAGMENTS = 7938;
    public static final int NORTH_ROCK = 27984;
    public static final int SOUTH_ROCK = 27985;
    public static final int DARK_ALTAR = 27979;
    public static final int BLOOD_RUNE = 565;
    public static final int BLOOD_ALTAR = 27978;

    //area tiles
    public static final RSTile OBS1_TILE = new RSTile(1761, 3874, 0);
    public static final RSTile OBS1_TILE2 = new RSTile(1761, 3871, 0);
    public static final RSTile ALTAR_AREA_TILE1 = new RSTile(1660, 3882, 0);
    public static final RSTile ALTAR_AREA_TILE2 = new RSTile(1662, 3862, 0);
    public static final RSTile ALTAR_AREA_TILE3 = new RSTile(1736, 3853, 0);
    public static final RSTile ALTAR_AREA_TILE4 = new RSTile(1736, 3831, 0);
    public static final RSTile FUCK_UP_TILE = new RSTile(1769,3870, 0);

    //areas
    public static final RSArea DARK_ALTAR_AREA = new RSArea(new RSTile(1720, 3880, 0), new RSTile(1713, 3887, 0));
    public static final RSArea OBS1_AREA_NORTH = new RSArea(new RSTile(1757, 3879, 0), new RSTile(1764, 3874, 0));
    public static final RSArea OBS1_AREA_SOUTH = new RSArea(new RSTile(1757, 3867, 0), new RSTile(1764, 3871, 0));
    public static final RSArea MINING_AREA = new RSArea(new RSTile(1767, 3861, 0), new RSTile(1758, 3843, 0));
    public static final RSArea BLOOD_ATLAR_AREA = new RSArea(new RSTile(1713, 3832, 0), new RSTile(1720, 3825, 0));
    public static final RSArea OBS2_AREA_NORTH = new RSArea(new RSTile(1742, 3851, 0), new RSTile(1738, 3852, 0));
    public static final RSArea OBS2_AREA_SOUTH = new RSArea(new RSTile(1752, 3857, 0), new RSTile(1755, 3852, 0));
    public static final RSArea TO_ATLAR_AREA = new RSArea(ALTAR_AREA_TILE1, 5);
    public static final RSArea TO_ATLAR_AREA3 = new RSArea(ALTAR_AREA_TILE3, 5);
    public static final RSArea TO_ATLAR_AREA2 = new RSArea(ALTAR_AREA_TILE2, 5);
    public static final RSArea TO_ALTAR_AREA4 = new RSArea(ALTAR_AREA_TILE4, 4);
    public static final RSTile[] OBS1_PATH = {new RSTile(1756, 3866, 0), new RSTile(1761, 3870, 0)};
    public static final RSArea FUCK_UP_AREA = new RSArea(FUCK_UP_TILE, 4);


}
