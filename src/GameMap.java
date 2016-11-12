import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by zyongliu on 11/11/16.
 */
public class GameMap {

    private List<Place> places = new ArrayList<>();

    public GameMap(Place... gameMaps) {
        this.places = asList(gameMaps);
    }

    public int move(int position, int roll) {
        int target = (position + roll) % places.size();
        getPlace(target).setStatus(Place.PLAYER);
        return target;
    }

    public Place getPlace(int position) {
        return places.get(position % places.size());
    }

}
