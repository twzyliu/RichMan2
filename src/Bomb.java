/**
 * Created by zyongliu on 12/11/16.
 */
public class Bomb implements Item {
    public static final String BOMB_SYMBOL = "@";
    private int point = 50;

    @Override
    public int getPoint() {
        return point;
    }
}
