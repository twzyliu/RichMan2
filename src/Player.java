import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;
import static java.util.Arrays.asList;

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
    public static final List<List<String>> playersName = new ArrayList<>(
            asList(asList("1.钱夫人(Q)", "Q", "钱夫人Q"), asList("2.阿土伯(A)", "A", "阿土伯A"), asList("3.孙小美(S)", "S", "孙小美S"), asList("4.金贝贝(J)", "J", "金贝贝J"))
    );
    public static final int DEFAULT_MONEY = 10000;
    private STATUS status = STATUS.TURN_START;
    private Dice dice;
    private GameMap map;
    private int position = 0;
    private int money = 0;
    private List<EmptyLand> places = new ArrayList<>();
    private int godDays = 0;
    private int prisonDays = 0;
    private int hosipitalDays = 0;
    private int point = 0;
    private int barricades = 0;
    private int robots = 0;
    private int bombs = 0;
    private int playerNum = 0;

    public Player(int playerNum, Dice dice, GameMap map) {
        this.playerNum = playerNum;
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
                int prisonDays = owner.getPrisonDays();
                int hosipitalDays = owner.getHosipitalDays();
                if (godDays > 0) {
                    out.printf("恭喜福神附体剩余%s天,免交过路费!\n", godDays);
                }
                if (prisonDays > 0) {
                    out.printf("房屋主人进监狱啦,还有%s天出狱,免交过路费!\n", prisonDays);
                }
                if (hosipitalDays > 0) {
                    out.printf("房屋主人进医院啦,还有%s天出院,免交过路费!\n", hosipitalDays);
                }
                status = STATUS.TURN_END;
                if (godDays + prisonDays + hosipitalDays == 0) {
                    payForOthersLand((EmptyLand) place);
                }
            }
        } else if (place instanceof ToolsLand) {
            if (point >= Items.CHEAPEST) {
                status = STATUS.WAIT_FOR_TOOLS_COMMAND;
            } else {
                out.print("欢迎来到道具屋,但是你的点数不够,拜拜~\n");
                status = STATUS.TURN_END;
            }
        } else if (place instanceof GiftLand) {
            status = STATUS.WAIT_FOR_GIFT_COMMAND;
        } else if (place instanceof MineLand) {
            int point = ((MineLand) place).getPoint();
            this.point += point;
            out.printf("欢迎进入矿地,你已获得%s点,现在拥有%s点\n", point, this.point);
            status = STATUS.TURN_END;
        } else if (place instanceof Hospital)

        {
            out.print("Welcome to Hospital! But we have nothing to do...\n");
            status = STATUS.TURN_END;
        } else if (place instanceof Prison)

        {
            out.printf("恭喜进监狱,休息休息%s天\n", PRISON_DAY);
            gotoPrison();
            status = STATUS.TURN_END;
        } else if (place instanceof MagicLand)

        {
            out.print("Welcome to MagicHouse! But we have nothing to do...\n");
            status = STATUS.TURN_END;
        } else if (place instanceof StartingPoint)

        {
            out.print("This is StartingPoint, your new start!\n");
            status = STATUS.TURN_END;
        }
    }

    private void payForOthersLand(EmptyLand place) {
        int bill = place.getBill();
        String playerName = place.getOwner().getName();
        if (money >= bill) {
            status = STATUS.TURN_END;
        } else {
            status = STATUS.GAME_OVER;
        }
        money -= bill;
        place.getOwner().gainMoney(bill);
        out.printf("已支付%s的地产过路费%s元,剩余金钱%s元!\n", playerName, bill, money);

    }

    public List<EmptyLand> getPlaces() {
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
            out.print("成功放置路障!\n");
            place.setStatus(Place.BARRICADE);
            barricades -= 1;
            return true;
        } else {
            out.print("放置路障失败!\n");
            return false;
        }
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
            out.print("炸弹已经成功放置!\n");
            return true;
        } else {
            out.print("炸弹放置失败!\n");
            return false;
        }
    }

    public void gainBomb() {
        bombs += 1;
    }

    public boolean sell(int myland) {
        status = STATUS.WAIT_FOR_COMMAND;
        EmptyLand emptyLand = (EmptyLand) map.getPlace(myland);
        if (places.contains(emptyLand)) {
            int sellMoney = emptyLand.getSellMoney();
            places.remove(emptyLand);
            money += sellMoney;
            emptyLand.setOwner(null);
            emptyLand.initLevel();
            out.printf("恭喜地产变卖成功,获得%s元\n", sellMoney);
            return true;
        } else {
            out.print("对不起,这里地产不是你的...\n");
        }
        return false;
    }

    public void buyLand() {
        EmptyLand place = (EmptyLand) map.getPlace(position);
        if (money >= place.getPrice()) {
            place.setOwner(this);
            places.add(place);
            money -= place.getPrice();
            out.printf("购买成功!剩余金钱%s元\n", money);
        } else {
            out.print("你已经穷的买不起房了...\n");
        }
    }

    public void upgradeLand() {
        EmptyLand place = (EmptyLand) map.getPlace(position);
        if (money >= place.getPrice() & place.getLevel() < EmptyLand.MAXLEVEL) {
            place.levelUP();
            money -= place.getPrice();
            out.printf("恭喜地产升级成功,现在地产等级为%s\n", place.getLevel());
        } else {
            out.print("没钱就不要升级地产了...\n");
        }
    }

    public boolean sellTool(Items item) {
        status = STATUS.WAIT_FOR_COMMAND;
        if (item.equals(Items.Barricade)) {
            if (barricades > 0) {
                point += item.getPoint();
                barricades -= 1;
                out.print("成功卖出路障!\n");
                return true;
            }
        } else if (item.equals(Items.Robot)) {
            if (robots > 0) {
                point += item.getPoint();
                robots -= 1;
                out.print("成功卖出机器娃娃!\n");
                return true;
            }
        } else if (item.equals(Items.Bomb)) {
            if (bombs > 0) {
                point += item.getPoint();
                bombs -= 1;
                out.print("成功卖出炸弹!\n");
                return true;
            }
        }
        out.print("什么都没有,你想卖个啥...\n");
        return false;
    }

    public boolean buyTool(Items item) {
        status = STATUS.WAIT_FOR_COMMAND;
        int itemPoint = item.getPoint();
        if (getToolsNum() < 10 & point >= itemPoint) {
            if (item.equals(Items.Barricade)) {
                barricades += 1;
                out.print("恭喜购买路障成功!\n");
            } else if (item.equals(Items.Robot)) {
                robots += 1;
                out.print("恭喜购买机器娃娃成功!\n");
            } else if (item.equals(Items.Bomb)) {
                bombs += 1;
                out.print("恭喜购买炸弹成功!\n");
            }
            point -= itemPoint;
            return true;
        } else {
            out.print("购买失败!请检查道具是否达到上限(10个),点数是否足够!\n按F键退出\n");
            return false;
        }
    }

    public int getBarricades() {
        return barricades;
    }

    public int getRobots() {
        return robots;
    }

    public int getBombs() {
        return bombs;
    }

    public int getPlaceByLevel(int level) {
        int placeNum = 0;
        for (EmptyLand place : places) {
            if (place.getLevel() == level) {
                placeNum += 1;
            }
        }
        return placeNum;
    }

    public void gainRobot() {
        robots += 1;
    }

    public boolean robot() {
        status = STATUS.WAIT_FOR_COMMAND;
        if (robots > 0) {
            map.robotsTool(position);
            robots -= 1;
            out.print("机器娃娃已经出动!\n");
            return true;
        } else {
            out.print("对不起,你穷的连机器娃娃都没有...\n");
            return false;
        }
    }

    public static String getPlayerName(Integer playerNum) {
        return playersName.get(playerNum - 1).get(0);
    }

    public String getName() {
        return playersName.get(playerNum - 1).get(2);
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public String getSymbol() {
        return playersName.get(playerNum - 1).get(1);
    }

    public void updateDays() {
        if (godDays > 0) {
            godDays -= 1;
        }
        if (prisonDays > 0) {
            if (prisonDays == 1) {
                out.print("恭喜你下回合将被刑满释放\n");
            }
            prisonDays -= 1;
        }
        if (hosipitalDays > 0) {
            if (hosipitalDays == 1) {
                out.print("恭喜你下回合将健康出院\n");
            }
            hosipitalDays -= 1;
        }
    }

    public boolean checkDays() {
        if (prisonDays > 0) {
            out.printf("%s>你的监狱生活剩余%s天...\n", getName(), prisonDays);
            return true;
        }
        if (hosipitalDays > 0) {
            out.printf("%s>你还需要在医院休养生息%s天...\n", getName(), hosipitalDays);
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
                    if (player.point >= Items.Barricade.getPoint() & player.getToolsNum() < MAX_TOOLS_NUM) {
                        player.point -= Items.Barricade.getPoint();
                        player.gainBarricade();
                    }
                    player.status = STATUS.WAIT_FOR_TOOLS_COMMAND;
                } else if (command.equals(Command.TOOLS_ROBOT)) {
                    if (player.point >= Items.Robot.getPoint() & player.getToolsNum() < MAX_TOOLS_NUM) {
                        player.point -= Items.Robot.getPoint();
                        player.gainRobot();
                    }
                } else if (command.equals(Command.TOOLS_BOMB)) {
                    if (player.point >= Items.Bomb.getPoint() & player.getToolsNum() < MAX_TOOLS_NUM) {
                        player.point -= Items.Bomb.getPoint();
                        player.gainBomb();
                    }
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
