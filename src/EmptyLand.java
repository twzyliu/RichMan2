import static java.lang.Math.pow;

/**
 * Created by zyongliu on 11/11/16.
 */
public class EmptyLand extends Place {
    public static final int MAXLEVEL = 3;
    private Player owner;
    private int price;
    private int level = 0;

    public EmptyLand(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setOwner(Player player) {
        owner = player;
    }

    public Player getOwner() {
        return owner;
    }

    public void levelUP() {
        level += 1;
    }

    public int getLevel() {
        return level;
    }

    public int getBill() {
        return (int) ((price / 2) * pow(2, level));
    }

    public int getSellMoney() {
        return price * (level + 1);
    }

    public void initLevel() {
        level = 0;
    }

    @Override
    public String getStatusSymbol() {
        return String.valueOf(level);
    }

    @Override
    public void playerCome(Player player) {
        player.gotoEmptyLand(getOwner(), getBill());
    }

}
