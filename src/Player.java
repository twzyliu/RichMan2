import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyongliu on 11/11/16.
 */
public class Player {
    public static final int GOD_DAYS = 5;
    public static final int MAX_TOOLS_NUM = 10;
    public static final int PRISON_DAY = 2;
    public static final int HOSIPATIL_DAY = 3;
    public static final int GIFT_MONEY = 2000;
    public static final int GIFT_POINT = 200;
    private String name;
    private STATUS status = STATUS.TURN_START;
    private Dice dice;
    private GameMap map;
    private int position = 0;
    private int money = 10000;
    private List<Place> places = new ArrayList<>();
    private int godDays = 0;
    private int prisonDays = 0;
    private int hosipatilDays = 0;
    private int point = 0;
    private int barricade = 0;
    private int robot = 0;
    private int bomb = 0;
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
            Player owner = ((EmptyLand) place).getOwner();
            if (owner == null) {
                status = STATUS.WAIT_FOR_BUY_COMMAND;
            } else if (owner == this) {
                status = STATUS.WAIT_FOR_UPGRADE_COMMAND;
            } else {
                if (godDays + owner.getPrisonDays() + owner.getHosipatilDays() == 0) {
                    payForOthersLand((EmptyLand) place);
                } else {
                    status = STATUS.TURN_END;
                }
            }
        } else if (place instanceof ToolsLand) {
            if (point >= Item.CHEAPEST) {
                status = STATUS.WAIT_FOR_TOOLS_COMMAND;
            } else {
                status = STATUS.TURN_END;
            }
        } else if (place instanceof GiftLand) {
            status = STATUS.WAIT_FOR_GIFT_COMMAND;
        } else if (place instanceof MineLand) {
            point += ((MineLand) place).getPoint();
            status = STATUS.TURN_END;
        } else if (place instanceof Hospital) {
            status = STATUS.TURN_END;
        } else if (place instanceof Prison) {
            gotoPrison();
            status = STATUS.TURN_END;
        } else if (place instanceof MagicLand) {
            status = STATUS.TURN_END;
        } else if (place instanceof StartingLand) {
            status = STATUS.TURN_END;
        }
    }

    private void payForOthersLand(EmptyLand place) {
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

    public void gainGod() {
        godDays = GOD_DAYS;
    }

    public void sayYes() {
        status.sayYes(this);
    }

    public void sayNo() {
        status.sayNo(this);
    }

    public void gotoPrison() {
        prisonDays = PRISON_DAY;
    }

    public void gotoHosipital() {
        hosipatilDays = HOSIPATIL_DAY;
    }

    public int getPoint() {
        return point;
    }

    public int getPrisonDays() {
        return prisonDays;
    }

    public int getHosipatilDays() {
        return hosipatilDays;
    }

    public void command(String command) {
        status.command(this, command);
    }

    public int getToolsNum() {
        return barricade + robot + bomb;
    }

    public void gainPoint(int point) {
        this.point += point;
    }

    public void gainMoney(int money) {
        this.money += money;
    }

    public void gainBarricade() {
        barricade += 1;
    }

    public int getGodDays() {
        return godDays;
    }

    public int getPosition() {
        return position;
    }

    public void block(int step) {
        boolean hasBarricade = barricade >= 1;
        boolean notFar = step > -11 & step < 11;
        int target = position + step;
        Place place = map.getPlace(target);
        if (hasBarricade & notFar & place.isEmpty()) {
            place.setStatus(Place.BARRICADE);
            barricade -= 1;
        }
        status = STATUS.WAIT_FOR_COMMAND;
    }

    public enum STATUS {
        TURN_END, WAIT_FOR_BUY_COMMAND {
            @Override
            public void sayYes(Player player) {
                player.status = STATUS.TURN_END;
                EmptyLand place = (EmptyLand) player.map.getPlace(player.position);
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
                EmptyLand place = (EmptyLand) player.map.getPlace(player.position);
                if (player.money >= place.getPrice() & place.getLevel() < EmptyLand.MAXLEVEL) {
                    place.levelUP();
                    player.money -= place.getPrice();
                }
            }
        }, TURN_START, GAME_OVER, WAIT_FOR_TOOLS_COMMAND {
            @Override
            public void command(Player player, String command) {
                if (command.equals(Command.TOOLS_EXIT)) {
                    player.status = STATUS.TURN_END;
                } else if (command.equals(Command.TOOLS_BARRICADE)) {
                    if (player.point >= Barricade.POINT & player.getToolsNum() < MAX_TOOLS_NUM) {
                        player.point -= Barricade.POINT;
                        player.gainBarricade();
                    }
                    player.status = STATUS.WAIT_FOR_TOOLS_COMMAND;
                }
            }
        }, WAIT_FOR_GIFT_COMMAND {
            @Override
            public void command(Player player, String command) {
                if (command.equals(Command.GIFT_MONEY)) {
                    player.gainMoney(GIFT_MONEY);
                    player.status = STATUS.TURN_END;
                } else if (command.equals(Command.GIFT_POINT)) {
                    player.gainPoint(GIFT_POINT);
                } else if (command.equals(Command.GIFT_GOD)) {
                    player.gainGod();
                }
                player.status = STATUS.TURN_END;
            }
        }, WAIT_FOR_COMMAND;

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
