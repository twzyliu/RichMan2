import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyongliu on 11/11/16.
 */
public class Player {
    public static final int GOD_DAYS = 5;
    public static final int MAX_TOOLS_NUM = 10;
    public static final int PRISON_DAY = 2;
    public static final int HOSIPITAL_DAY = 3;
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
    private int hosipitalDays = 0;
    private int point = 0;
    private int barricades = 0;
    private int robots = 0;
    private int bombs = 0;

    public Player(String name, Dice dice, GameMap map) {
        this.name = name;
        this.dice = dice;
        this.map = map;
        map.move(this, 0);
    }

    public STATUS getStatus() {
        return status;
    }

    public void roll() {
        position = map.move(this, dice.roll());
        Place place = map.getPlace(position);
        if (place instanceof EmptyLand) {
            Player owner = ((EmptyLand) place).getOwner();
            if (owner == null) {
                status = STATUS.WAIT_FOR_BUY_COMMAND;
            } else if (owner == this) {
                status = STATUS.WAIT_FOR_UPGRADE_COMMAND;
            } else {
                if (godDays + owner.getPrisonDays() + owner.getHosipitalDays() == 0) {
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
        } else if (place instanceof StartingPoint) {
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
        hosipitalDays = HOSIPITAL_DAY;
    }

    public int getPoint() {
        return point;
    }

    public int getPrisonDays() {
        return prisonDays;
    }

    public int getHosipitalDays() {
        return hosipitalDays;
    }

    public void command(String command) {
        status.command(this, command);
    }

    public int getToolsNum() {
        return barricades + robots + bombs;
    }

    public void gainPoint(int point) {
        this.point += point;
    }

    public void gainMoney(int money) {
        this.money += money;
    }

    public void gainBarricade() {
        barricades += 1;
    }

    public int getGodDays() {
        return godDays;
    }

    public int getPosition() {
        return position;
    }

    public boolean block(int step) {
        status = STATUS.WAIT_FOR_COMMAND;
        boolean hasBarricade = barricades >= 1;
        boolean notFar = step > -11 & step < 11;
        int target = position + step;
        Place place = map.getPlace(target);
        if (hasBarricade & notFar & place.isEmpty()) {
            place.setStatus(Place.BARRICADE);
            barricades -= 1;
            return true;
        }
        return false;
    }

    public boolean bomb(int step) {
        status = STATUS.WAIT_FOR_COMMAND;
        boolean hasBombs = bombs >= 1;
        boolean notFar = step > -11 & step < 11;
        int target = position + step;
        Place place = map.getPlace(target);
        if (hasBombs & notFar & place.isEmpty()) {
            place.setStatus(Place.BOMB);
            bombs -= 1;
            return true;
        }
        return false;
    }

    public void gainBomb() {
        bombs += 1;
    }

    public boolean sell(int myland) {
        status = STATUS.WAIT_FOR_COMMAND;
        EmptyLand emptyLand = (EmptyLand) map.getPlace(myland);
        if (places.contains(emptyLand)) {
            places.remove(emptyLand);
            money += emptyLand.getSellMoney();
            emptyLand.setOwner(null);
            emptyLand.initLevel();
            return true;
        }
        return false;
    }

    public void buyLand() {
        EmptyLand place = (EmptyLand) map.getPlace(position);
        if (money >= place.getPrice()) {
            place.setOwner(this);
            places.add(place);
            money -= place.getPrice();
        }
    }

    public void upgradeLand() {
        EmptyLand place = (EmptyLand) map.getPlace(position);
        if (money >= place.getPrice() & place.getLevel() < EmptyLand.MAXLEVEL) {
            place.levelUP();
            money -= place.getPrice();
        }
    }


    public boolean sellTool(Item item) {
        status = STATUS.WAIT_FOR_COMMAND;
        if (item instanceof Barricade) {
            if (barricades > 0) {
                point += item.getPoint();
                barricades -= 1;
                return true;
            }
        } else if (item instanceof Robot) {
            if (robots > 0) {
                point += item.getPoint();
                robots -= 1;
                return true;
            }
        } else if (item instanceof Bomb) {
            if (bombs > 0) {
                point += item.getPoint();
                bombs -= 1;
                return true;
            }
        }
        return false;
    }

    public void gainRobot() {
        robots += 1;
    }

    public boolean robot() {
        status = STATUS.WAIT_FOR_COMMAND;
        if (robots > 0) {
            map.clearTool(position);
            robots -= 1;
            return true;
        }
        return false;
    }

    public enum STATUS {
        TURN_END, WAIT_FOR_BUY_COMMAND {
            @Override
            public void sayYes(Player player) {
                player.status = STATUS.TURN_END;
                player.buyLand();
            }
        }, WAIT_FOR_UPGRADE_COMMAND {
            @Override
            public void sayYes(Player player) {
                player.status = STATUS.TURN_END;
                player.upgradeLand();
            }
        }, TURN_START, GAME_OVER, WAIT_FOR_TOOLS_COMMAND {
            @Override
            public void command(Player player, String command) {
                if (command.equals(Command.TOOLS_EXIT)) {
                    player.status = STATUS.TURN_END;
                } else if (command.equals(Command.TOOLS_BARRICADE)) {
                    if (player.point >= Item.BARRICADE_POINT & player.getToolsNum() < MAX_TOOLS_NUM) {
                        player.point -= Item.BARRICADE_POINT;
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
