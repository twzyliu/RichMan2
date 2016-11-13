import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyongliu on 11/11/16.
 */
public class Place {
    public static final int EMPTY = 0;
    public static final int PLAYER = 1;
    public static final int BARRICADE = 2;
    public static final int BOMB = 3;
    public static final String PLAYER_SYMBOL = "!";
    public static final String GIFTLAND_SYMBOL = "G";
    public static final String HOSIPITAL_SYMBOL = "H";
    public static final String MAGICLAND_SYMBOL = "M";
    public static final String MINELAND_SYMBOL = "$";
    public static final String PRISON_SYMBOL = "P";
    public static final String STARTPOINT_SYMBOL = "S";
    public static final String TOOLSLAND_SYMBOL = "T";
    private int status = 0;
    private List<String> playerSymbol = new ArrayList<>();

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        if (status != EMPTY | this.status != PLAYER | playerSymbol.size() == 0) {
            this.status = status;
        }
    }

    public boolean isEmpty() {
        return status == EMPTY;
    }

    public void clearToolStatus() {
        if (status > 1) {
            status = 0;
        }
    }

    public String getSymbol() {
        if (status == PLAYER) {
            return PLAYER_SYMBOL;
        } else if (status == BARRICADE) {
            return Barricade.BARRICADE_SYMBOL;
        } else if (status == BOMB) {
            return Bomb.BOMB_SYMBOL;
        } else {
            return getStatusSymbol();
        }
    }

    public void setPlayerSymbol(String playerSymbol) {
        this.playerSymbol.add(playerSymbol);
    }

    public String getPlayerSymbol() {
        return playerSymbol.get(playerSymbol.size() - 1);
    }

    public String getStatusSymbol() {
        return "";
    }

    public void removePlayerSymbol(String symbol) {
        playerSymbol.remove(symbol);
    }
}
