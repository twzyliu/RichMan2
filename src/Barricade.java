import static java.lang.System.out;

/**
 * Created by zyongliu on 14/11/16.
 */
public class Barricade extends Items {
    public static final String SYMBOL = "#";

    public Barricade() {
        super();
        point = 50;
    }

    @Override
    public boolean use(GameMap gameMap, int position, int step) {
        int target = position + step;
        Place place = gameMap.getPlace(target);
        boolean hasBarricade = getNum() >= 1;
        boolean notFar = step > -11 & step < 11;
        if (hasBarricade & notFar & place.isEmpty()) {
            out.print("成功放置路障!\n");
            place.setStatus(Place.BARRICADE);
            loseItem();
            return true;
        } else {
            out.print("放置路障失败!\n");
            return false;
        }
    }
}
