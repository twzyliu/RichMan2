import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyongliu on 11/11/16.
 */
public class Player {
    private String name;
    private STATUS status = STATUS.TURN_START;
    private Dice dice;
    private GameMap map;
    private int position = 0;
    private int money = 10000;
    private List<Place> places = new ArrayList<>();

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
            if (place.getOwner() == null) {
                status = STATUS.WAIT_FOR_BUY_COMMAND;
            } else if (place.getOwner() == this) {
                status = STATUS.WAIT_FOR_UPGRADE_COMMAND;
            }
        }
    }

    public List<Place> getPlaces() {
        return places;
    }

    public int getMoney() {
        return money;
    }

    public void sayYes() {
        status.sayYes(this);
    }

    public void sayNo() {
        status.sayNo(this);
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
        }, TURN_START;

        public void sayYes(Player player) {
            player.status = STATUS.TURN_END;
        }

        public void sayNo(Player player) {
            player.status = STATUS.TURN_END;
        }
    }

}
