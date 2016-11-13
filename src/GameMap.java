import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.System.out;
import static java.util.Arrays.asList;

/**
 * Created by zyongliu on 11/11/16.
 */
public class GameMap {
    private List<Place> places = new ArrayList<>();

    public GameMap(Place... gameMaps) {
        this.places = asList(gameMaps);
    }

    public GameMap(List<Place> places) {
        this.places = places;
    }

    public static final HashMap<Integer, Integer> defaultGameMap = new HashMap<Integer, Integer>() {{
        for (int index = 0; index < 29; index++) {
            put(index, index);
        }
        for (int index = 29; index < 35; index++) {
            put(28 + (index - 28) * 30, index);
        }
        for (int index = 35; index < 64; index++) {
            put(((30 * 8 - 2) - (index - 35)), index);
        }
        for (int index = 64; index < 70; index++) {
            put((70 - index) * 30, index);
        }
    }};

    public int move(Player player, int step) {
        int position = player.getPosition();
        Place lastPlace = getPlace(position);
        lastPlace.removePlayerSymbol(player.getSymbol());
        lastPlace.setStatus(Place.EMPTY);

        int target = (position + step) % getSize();
        for (int index = position + 1; index < (position + 1 + step); index++) {
            Place place = getPlace(index);
            int status = place.getStatus();
            if (status == Place.BARRICADE) {
                place.setStatus(Place.EMPTY);
                out.print("撞到路障停了下来...\n");
                target = index;
                break;
            } else if (status == Place.BOMB) {
                place.setStatus(Place.EMPTY);
                out.printf("踩到炸弹,送到医院休息%s天\n", Player.HOSIPITAL_DAY);
                target = getHosipitalPosition();
                player.gotoHosipital();
                break;
            }
        }
        Place targetPlace = getPlace(target);
        targetPlace.setStatus(Place.PLAYER);
        targetPlace.setPlayerSymbol(player.getSymbol());
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

    public void robotsTool(int position) {
        for (int index = position + 1; index < position + 1 + Items.ROBOT_STEP; index++) {
            Place place = getPlace(index % getSize());
            place.clearToolStatus();
        }
    }

    private int getSize() {
        return places.size();
    }

    public void printMap() {
        String gamemapStr = "";
        for (int index = 0; index < Game.GAMEMAP_SIZE; index++) {
            Place place = places.get(index);
            String symbol = place.getSymbol();
            if (symbol.equals(Place.PLAYER_SYMBOL)) {
                symbol = place.getPlayerSymbol();
            }
            gamemapStr += symbol;
        }
        out.print(transform(gamemapStr));
    }

    private String transform(String mapStr) {
        String gamemapStr = "";
        for (int index = 0; index < 30 * 8; index++) {
            Integer position = defaultGameMap.getOrDefault(index, -1);
            if ((index + 1) % 30 == 0) {
                gamemapStr += "\n";
                continue;
            } else if (position >= 0) {
                gamemapStr += mapStr.charAt(position);
            } else {
                gamemapStr += " ";
            }
        }
        return gamemapStr;
    }
}
