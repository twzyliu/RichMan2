import static java.lang.System.out;

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
        if (player.getToolsNum() < Player.MAX_TOOLS_NUM & player.getPoint() >= getPoint()) {
            gainItem();
            player.gainPoint(0 - getPoint());
            out.print("恭喜购买道具成功!\n");
        } else {
            out.print("购买失败!请检查道具是否达到上限(10个),点数是否足够!\n按F键退出\n");
        }
    }
}
