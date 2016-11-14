import static java.lang.System.out;

/**
 * Created by zyongliu on 14/11/16.
 */
public class Bomb extends Items {
    public static final String SYMBOL = "@";
    private static final int point = 50;

    public Bomb() {
        super();
    }

    @Override
    public boolean use(GameMap gameMap, int position, int step) {
        int target = position + step;
        Place place = gameMap.getPlace(target);
        boolean hasBombs = getNum() >= 1;
        boolean notFar = step > -11 & step < 11;
        if (hasBombs & notFar & place.isEmpty()) {
            place.setStatus(Place.BOMB);
            loseItem();
            out.print("炸弹已经成功放置!\n");
            return true;
        } else {
            out.print("炸弹放置失败!\n");
            return false;
        }
    }
}
