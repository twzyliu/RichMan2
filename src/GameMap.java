import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by zyongliu on 11/11/16.
 */
public class GameMap {

    public static final int ROBOT_STEP = 10;
    private List<Place> places = new ArrayList<>();

    public GameMap(Place... gameMaps) {
        this.places = asList(gameMaps);
    }

    public int move(Player player, int step) {
        int position = player.getPosition();
        int target = (position + step) % getSize();
        for (int index = position + 1; index < (position + 1 + step); index++) {
            int status = getPlace(index).getStatus();
            if (status == Place.BARRICADE) {
                target = index;
            } else if (status == Place.BOMB) {
                target = getHosipitalPosition();
                player.gotoHosipital();
            }
        }
        getPlace(target).setStatus(Place.PLAYER);
        return target;
    }

    public int getHosipitalPosition() {
        for (int index = 0; index < getSize(); index++) {
            if (getPlace(index) instanceof Hospital) {
                return index;
            }
        }
        return 0;
    }

    public Place getPlace(int position) {
        return places.get(position % getSize());
    }

    public void clearTool(int position) {
        for (int index = position; index < ROBOT_STEP; index++) {
            Place place = getPlace(index % getSize());
            place.clearToolStatus();
        }
    }

    private int getSize() {
        return places.size();
    }
}
