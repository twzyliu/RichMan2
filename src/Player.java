import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyongliu on 11/11/16.
 */
public class Player {
    public static final int GOD_DAYS = 5;
    public static final String EXIT_BUY_TOOLS = "F";
    public static final String BARRICADE = "1";
    public static final int MAX_TOOLS_NUM = 10;
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
    private int point = 0;
    private List<Item> items = new ArrayList<>();

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
        } else if (place instanceof ToolsLand) {
            status = STATUS.WAIT_FOR_TOOLS_COMMAND;
        }
    }

    private void payForOthersLand(Place place) {
        if (money > place.getPrice()) {
            money -= place.getBill();
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

    public int getPoint() {
        return point;
    }

    public int getInPrison() {
        return inPrison;
    }

    public int getInHosipatil() {
        return inHosipatil;
    }

    public void command(String command) {
        status.command(this, command);
    }

    public int getItemsNum() {
        return items.size();
    }

    public List<Item> getItems() {
        return items;
    }

    public void gainPoint(int point) {
        this.point += point;
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
        }, TURN_START, GAME_OVER, WAIT_FOR_TOOLS_COMMAND {
            @Override
            public void command(Player player, String command) {
                if (command.equals(EXIT_BUY_TOOLS)) {
                    player.status = STATUS.TURN_END;
                } else if (command.equals(BARRICADE)) {
                    if (player.point >= Barricade.POINT & player.getItemsNum()< MAX_TOOLS_NUM) {
                        player.point -= Barricade.POINT;
                        player.items.add(new Barricade());
                    }
                    player.status = STATUS.WAIT_FOR_TOOLS_COMMAND;
                }
            }
        };


        public void sayYes(Player player) {
            player.status = STATUS.TURN_END;
        }

        public void sayNo(Player player) {
            player.status = STATUS.TURN_END;
        }

        public void command(Player player, String command) {

        }
    }

}
