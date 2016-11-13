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

    private Items(int point, String symbol) {
        this.point = point;
        this.symbol = symbol;
    }

    public int getPoint() {
        return point;
    }

    public String getSymbol() {
        return symbol;
    }

    public static final Items Barricade = new Items(BARRICADE_POINT, BARRICADE_SYMBOL);
    public static final Items Bomb = new Items(BOMB_POINT, BOMB_SYMBOL);
    public static final Items Robot = new Items(ROBOT_POINT,"");
}
