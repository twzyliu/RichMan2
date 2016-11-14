/**
 * Created by zyongliu on 14/11/16.
 */
public class Items {
    public static final int CHEAPEST = 30;
    protected int point = 0;
    protected int num = 0;

    public Items() {
    }

    public int getPoint() {
        return point;
    }

    public int getNum() {
        return num;
    }

    public void gainItem() {
        num += 1;
    }

    public void loseItem() {
        num -= 1;
    }

    public boolean use(GameMap gameMap, int position, int step) {
        return true;
    }

    public void buy(Player player) {
        if (player.getPoint() >= getPoint() & player.getToolsNum() < Player.MAX_TOOLS_NUM) {
            player.gainPoint(0 - getPoint());
            gainItem();
            player.setStatus(STATUS.WAIT_FOR_TOOLS_COMMAND);
        }
    }
}
