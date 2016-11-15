/**
 * Created by zyongliu on 12/11/16.
 */
public class MineLand extends Place {
    private static final String MINELAND_SYMBOL = "$";
    private int point;

    public MineLand(int point) {
        this.point = point;
    }

    public int getPoint() {
        return point;
    }

    @Override
    public String getStatusSymbol() {
        return MINELAND_SYMBOL;
    }

    @Override
    public void playerCome(Player player) {
        player.gotoMineLand(getPoint());
    }

}
