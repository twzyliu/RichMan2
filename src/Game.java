import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.lang.System.*;
import static java.util.Arrays.asList;

/**
 * Created by zyongliu on 13/11/16.
 */
public class Game {
    public static final String INPUT_AGAIN = "请重新输入:\n";
    public static final String WRONG_COMMAND = "指令错误! 输入help可以查询指令 " + INPUT_AGAIN;
    public static final String ILLEGAL_NUMBER = "非法数字!\n" + INPUT_AGAIN;
    public static final int GAMEMAP_SIZE = 70;
    public static final int DEFAULT_MONEY = 10000;
    public static final List<List<String>> playersName = new ArrayList<>(
            asList(asList("1.钱夫人(Q)", "Q", "钱夫人Q"), asList("2.阿土伯(A)", "A", "阿土伯A"), asList("3.孙小美(S)", "S", "孙小美S"), asList("4.金贝贝(J)", "J", "金贝贝J"))
    );
    private Scanner input = new Scanner(System.in);
    private String command = "";
    private List<Player> players = new ArrayList<>();
    private List<Integer> playersNum;
    private int initFund = 0;
    private GameMap gameMap;
    private Player winner = null;
    private int round = 1;
    private List<Items> items = new ArrayList<>(
            asList(new Barricade(), new Robot(), new Bomb())
    );

    public Game() {
        init();
    }

    public void round() {
        while (winner == null) {
            out.print("第" + round + "回合开始!\n");
            for (Player player : players) {
                player.setStatus(STATUS.TURN_START);
                gameMap.printMap();
                if (!player.checkDays()) {
                    while (player.getStatus() != STATUS.TURN_END & player.getStatus() != STATUS.GAME_OVER) {
                        selectCommand(player);
                        player.getStatus().action(player, this);
                    }
                }
                player.updateDays();
                if (player.getStatus() == STATUS.GAME_OVER) {
                    players.remove(player);
                    out.printf("%s 你破产了!哈哈哈!\n胜败乃兵家常事,大侠请重新来过!\n", player.getName());
                }
                if (players.size() == 1) {
                    winner = players.get(0);
                }
            }
            round += 1;
        }
        out.printf("恭喜 %s 获得胜利!\n", players.get(0).getName());
        exit(0);
    }

    public void buyAndUpgradeActioin(Player player, String format) {
        int price = ((EmptyLand) gameMap.getPlace(player.getPosition())).getPrice();
        out.printf(format, price);
        out.printf("你现在拥有%s元\n", player.getMoney());
        boolean finish = false;
        while (!finish) {
            getInput();
            if (command.equals(Input.YES)) {
                player.sayYes();
                finish = true;
            } else if (command.equals(Input.NO)) {
                player.sayNo();
                finish = true;
            } else {
                out.print(INPUT_AGAIN);
            }
        }
    }

    public void getInput() {
        command = input.nextLine().toLowerCase();
    }

    private void selectCommand(Player player) {
        out.print(player.getName() + ">");
        command = input.nextLine().toLowerCase();
        new GameCommand(command).gameAction(player, this);
    }

    public List<Items> getItems() {
        return items;
    }

    public void query(Player player) {
        int money = player.getMoney();
        int point = player.getPoint();
        int barricades = player.getBarricade().getNum();
        int bombs = player.getBomb().getNum();
        int robots = player.getRobot().getNum();
        int landLevel0 = player.getPlaceByLevel(0);
        int landLevel1 = player.getPlaceByLevel(1);
        int landLevel2 = player.getPlaceByLevel(2);
        int landLevel3 = player.getPlaceByLevel(3);
        String queryStr = "资金：%s元\n" +
                "点数：%s点\n" +
                "地产：空地%s处；茅屋%s处；洋房%s处；摩天楼%s处。\n" +
                "道具：1.路障 %s个；2.机器娃娃%s个；3.炸弹%s个\n";
        out.printf(queryStr, money, point, landLevel0, landLevel1, landLevel2, landLevel3, barricades, robots, bombs);
    }

    public void printHelp() {
        String helpStr = "命令一览表\n" +
                "roll        掷骰子命令，行走1~6步。步数由随即算法产生。   \n" +
                "block n     玩家拥有路障后，可将路障放置到离当前位置前后10步的距离，任一玩家经过路障，都将被拦截。该道具一次有效。n 前后的相对距离，负数表示后方。\n" +
                "bomb n      可将路障放置到离当前位置前后10步的距离，任一玩家j 经过在该位置，将被炸伤，送往医院，住院三天。n 前后的相对距离，负数表示后方。\n" +
                "robot       使用该道具，可清扫前方路面上10步以内的其它道具，如炸弹、路障。\n" +
                "sell x      出售自己的房产，x 地图上的绝对位置，即地产的编号。\n" +
                "sellTool x  出售道具，x 道具编号\n" +
                "query       显示自家资产信息   \n" +
                "help        查看命令帮助   \n" +
                "quit        强制退出\n";
        out.print(helpStr);
    }

    private void init() {
        out.print("Welcome To RichMan World!\n");
        while (!command.equals("rich")) {
            out.print("Please command rich to start game.\n");
            getInput();
        }

        initGameMap();

        out.print("设置玩家初始资金，范围1000～50000(默认10000,命令N)\n");
        while (initFund == 0) {
            getInput();
            if (command.equals("n")) {
                initFund = DEFAULT_MONEY;
                break;
            }
            initFund = checkInputMoney(command);
        }
        out.printf("初始资金设置为默认%s元!\n", initFund);

        out.print("请选择2~4位不重复玩家，输入编号即可。(1.钱夫人; 2.阿土伯; 3.孙小美; 4.金贝贝):\n");
        boolean legal = false;
        while (!legal) {
            getInput();
            legal = checkInputPlayers(command);
        }

        initPlayers();

        out.print("游戏开始!\n");
        round();
    }

    private void initPlayers() {
        for (Integer platerNum : playersNum) {
            Player player = new Player(platerNum, new Dice(), gameMap);
            player.gainMoney(initFund);
            players.add(player);
        }
    }

    private void initGameMap() {
        List<Integer> mineLandPointS = new ArrayList<>(
                asList(20, 80, 100, 40, 80, 60)
        );

        List<Place> places = new ArrayList<>();
        places.add(new StartingPoint());

        for (int index = 0; index < 13 * 2 + 1; index++) {
            if (index == 13) {
                places.add(new Hospital());
            } else {
                places.add(new EmptyLand(200));
            }
        }

        places.add(new ToolsLand());

        for (int index = 0; index < 6; index++) {
            places.add(new EmptyLand(500));
        }

        places.add(new GiftLand());

        for (int index = 0; index < 13 * 2 + 1; index++) {
            if (index == 13) {
                places.add(new Prison());
            } else {
                places.add(new EmptyLand(300));
            }
        }

        places.add(new MagicLand());

        for (int index = 0; index < 6; index++) {
            places.add(new MineLand(mineLandPointS.get(index)));
        }

        gameMap = new GameMap(places);
    }

    private boolean checkInputPlayers(String command) {
        try {
            playersNum = Arrays.stream(command.split("")).map(Integer::parseInt).collect(Collectors.toList());
            int size = playersNum.size();
            if (size < 2 | size > 4) {
                out.print("只允许2-4位玩家!\n" + INPUT_AGAIN);
                return false;
            }
            for (Integer playerNum : playersNum) {
                if (playerNum < 1 | playerNum > 4) {
                    out.print("玩家编号只允许1-4!\n" + INPUT_AGAIN);
                    return false;
                }
            }
            List<Integer> distinct = playersNum.stream().distinct().collect(Collectors.toList());
            if (distinct.size() < size) {
                out.print("玩家编号重复!" + INPUT_AGAIN);
                return false;
            }
            String output = "您选择的玩家是: ";
            for (Integer playerNum : playersNum) {
                output += Player.getPlayerName(playerNum) + " ";
            }
            out.print(output + "\n");
            return true;
        } catch (Exception e) {
            out.print(ILLEGAL_NUMBER);
            return false;
        }
    }

    private int checkInputMoney(String command) {
        try {
            int money = Integer.parseInt(command);
            if (money < 1000 | money > 50000) {
                out.print("初始资金超出范围!\n" + INPUT_AGAIN);
                return 0;
            }
            return money;
        } catch (Exception e) {
            out.print(ILLEGAL_NUMBER);
            return 0;
        }
    }

    public static void main(String[] args) {
        new Game();
    }
}
