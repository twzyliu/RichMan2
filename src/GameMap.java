import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by zyongliu on 11/11/16.
 */
public class GameMap {

    public static final int ROBOT_SETP = 10;
    private List<Place> places = new ArrayList<>();

    public GameMap(Place... gameMaps) {
        this.places = asList(gameMaps);
    }

    public int move(int position, int roll) {
        int target = (position + roll) % getSize();
        getPlace(target).setStatus(Place.PLAYER);
        return target;
    }

    public Place getPlace(int position) {
        return places.get(position % getSize());
    }

    public void clearTool(int position) {
        for (int index = 0; index < ROBOT_SETP; index++) {
            Place place = getPlace((position + index) % getSize());
            place.clearToolStatus();
        }
    }

    private int getSize() {
        return places.size();
    }
}
