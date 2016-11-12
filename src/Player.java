import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyongliu on 11/11/16.
 */
public class Player {
    public static final int GOD_DAYS = 5;
    private String name;
    private STATUS status = STATUS.TURN_START;
    private Dice dice;
    private GameMap map;
    private int position = 0;
    private int money = 10000;
    private List<Place> places = new ArrayList<>();
    private int hasGod = 0;
    private int inPrison = 0;
    private int inHosipatil = 0;

    public Player(String name, Dice dice, GameMap map) {
        this.name = name;
        this.dice = dice;
        this.map = map;
    }

    public STATUS getStatus() {
        return status;
    }

    public void roll() {
        position = map.move(position, dice.roll());
        Place place = map.getPlace(position);
        if (place instanceof EmptyLand) {
            Player owner = place.getOwner();
            if (owner == null) {
                status = STATUS.WAIT_FOR_BUY_COMMAND;
            } else if (owner == this) {
                status = STATUS.WAIT_FOR_UPGRADE_COMMAND;
            } else {
                if (hasGod + owner.getInPrison() + owner.getInHosipatil() == 0) {
                    payForOthersLand(place);
                } else {
                    status = STATUS.TURN_END;
                }
            }
        }
    }

    private void payForOthersLand(Place place) {
        if (money > place.getPrice()) {
            money -= place.getPrice();
            status = STATUS.TURN_END;
        } else {
            status = STATUS.GAME_OVER;
        }
    }

    public List<Place> getPlaces() {
        return places;
    }

    public int getMoney() {
        return money;
    }

    public void getGod() {
        hasGod = GOD_DAYS;
    }

    public void sayYes() {
        status.sayYes(this);
    }

    public void sayNo() {
        status.sayNo(this);
    }

    public void intoPrison() {
        inPrison = 2;
    }

    public void intoHosipital() {
        inHosipatil = 3;
    }

    public int getHasGod() {
        return hasGod;
    }

    public int getInPrison() {
        return inPrison;
    }

    public int getInHosipatil() {
        return inHosipatil;
    }

    public enum STATUS {
        TURN_END, WAIT_FOR_BUY_COMMAND {
            @Override
            public void sayYes(Player player) {
                player.status = STATUS.TURN_END;
                Place place = player.map.getPlace(player.position);
                if (player.money >= place.getPrice()) {
                    place.setOwner(player);
                    player.places.add(place);
                    player.money -= place.getPrice();
                }
            }
        }, WAIT_FOR_UPGRADE_COMMAND {
            @Override
            public void sayYes(Player player) {
                player.status = STATUS.TURN_END;
                Place place = player.map.getPlace(player.position);
                if (player.money >= place.getPrice() & place.getLevel() < EmptyLand.MAXLEVEL) {
                    place.levelUP();
                    player.money -= place.getPrice();
                }
            }
        }, TURN_START, GAME_OVER;

        public void sayYes(Player player) {
            player.status = STATUS.TURN_END;
        }

        public void sayNo(Player player) {
            player.status = STATUS.TURN_END;
        }
    }

}
