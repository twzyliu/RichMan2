/**
 * Created by zyongliu on 11/11/16.
 */
public class EmptyLand implements Place {
    public static final int MAXLEVEL = 3;
    private Player owner;
    private int price;
    private int level = 0;

    public EmptyLand(int price) {
        this.price = price;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void setOwner(Player player) {
        owner = player;
        level = 1;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public void levelUP() {
        level += 1;
    }

    @Override
    public int getLevel() {
        return level;
    }
}
