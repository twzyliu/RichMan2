/**
 * Created by zyongliu on 11/11/16.
 */
public interface Place {
    int getPrice();

    void setOwner(Player player);

    Player getOwner();

    void levelUP();

    int getLevel();

    int getBill();

}
