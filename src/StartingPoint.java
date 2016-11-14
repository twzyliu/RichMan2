/**
 * Created by zyongliu on 12/11/16.
 */
public class StartingPoint extends Place {

    private static final String STARTPOINT_SYMBOL = "S";

    @Override
    public String getStatusSymbol() {
        return STARTPOINT_SYMBOL;
    }

    @Override
    public void playerCome(Player player) {
        player.gotoStartingPoint();
    }
}
