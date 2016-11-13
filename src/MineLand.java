/**
 * Created by zyongliu on 12/11/16.
 */
public class MineLand extends Place{
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
}
