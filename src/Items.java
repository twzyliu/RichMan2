import java.util.HashMap;

import static java.lang.System.out;

/**
 * Created by zyongliu on 14/11/16.
 */
public class Items {
    public static final int CHEAPEST = 30;
    public static final int ROBOT_STEP = 10;
    private static final String BARRICADE_SYMBOL = "#";
    private static final String BOMB_SYMBOL = "@";
    private static final int BARRICADE_POINT = 50;
    private static final int ROBOT_POINT = 30;
    private static final int BOMB_POINT = 50;
    private int point;
    private String symbol;
    private String command;
    private HashMap<Player, Integer> itemsMap = new HashMap<>();

    private Items(int point, String symbol, String command) {
        this.point = point;
        this.symbol = symbol;
        this.command = command;
    }

    public int getPoint() {
        return point;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getNum(Player player) {
        return itemsMap.getOrDefault(player, 0);
    }

    public void gainItem(Player player) {
        itemsMap.put(player, getNum(player) + 1);
    }

    public void loseItem(Player player) {
        itemsMap.put(player, getNum(player) - 1);
    }

    public static Items Barricade = new Items(BARRICADE_POINT, BARRICADE_SYMBOL, Command.TOOLS_BARRICADE);
    public static Items Bomb = new Items(BOMB_POINT, BOMB_SYMBOL, Command.TOOLS_BOMB);
    public static Items Robot = new Items(ROBOT_POINT, "", Command.TOOLS_ROBOT);

    public boolean useBarricade(int step, Player player) {
        int target = player.getPosition() + step;
        Place place = player.getMap().getPlace(target);
        boolean hasBarricade = getNum(player) >= 1;
        boolean notFar = step > -11 & step < 11;
        if (hasBarricade & notFar & place.isEmpty()) {
            out.print("成功放置路障!\n");
            place.setStatus(Place.BARRICADE);
            loseItem(player);
            return true;
        } else {
            out.print("放置路障失败!\n");
            return false;
        }
    }

    public boolean useBomb(int step, Player player) {
        int target = player.getPosition() + step;
        Place place = player.getMap().getPlace(target);
        boolean hasBombs = getNum(player) >= 1;
        boolean notFar = step > -11 & step < 11;
        if (hasBombs & notFar & place.isEmpty()) {
            place.setStatus(Place.BOMB);
            loseItem(player);
            out.print("炸弹已经成功放置!\n");
            return true;
        } else {
            out.print("炸弹放置失败!\n");
            return false;
        }
    }

    public boolean useRobot(Player player) {
        if (getNum(player) > 0) {
            player.getMap().robotsTool(player.getPosition());
            loseItem(player);
            out.print("机器娃娃已经出动!\n");
            return true;
        } else {
            out.print("对不起,你穷的连机器娃娃都没有...\n");
            return false;
        }
    }
}
