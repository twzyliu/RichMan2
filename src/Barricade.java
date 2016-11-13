/**
 * Created by zyongliu on 12/11/16.
 */
public class Barricade implements Item {
    public static final String BARRICADE_SYMBOL = "#";
    private int point = 50;

    public Barricade() {
    }

    @Override
    public int getPoint() {
        return point;
    }

}
