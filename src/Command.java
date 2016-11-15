import static java.lang.System.exit;
import static java.lang.System.out;

/**
 * Created by zyongliu on 14/11/16.
 */
public enum Command {
    TOOLS_EXIT {
        @Override
        public void action(Player player) {
            player.setStatus(STATUS.TURN_END);
        }
    }, TOOLS_BARRICADE {
        @Override
        public void action(Player player) {
            player.buyBarricade();
        }

        @Override
        public boolean buyTools(Game game, Player player) {
            player.buyBarricade();
            return true;
        }
    }, TOOLS_ROBOT {
        @Override
        public boolean buyTools(Game game, Player player) {
            player.buyRobot();
            return true;
        }
    }, TOOLS_BOMB {
        @Override
        public boolean buyTools(Game game, Player player) {
            player.buyBomb();
            return true;
        }
    }, TOOLS_WRONG_COMMAND {
        @Override
        public boolean buyTools(Game game, Player player) {
            game.query(player);
            out.print("按F键退出\n");
            return true;
        }
    }, GIFT_MONEY {
        @Override
        public void action(Player player) {
            player.gainMoney(Player.GIFT_MONEY);
            player.setStatus(STATUS.TURN_END);
        }

        @Override
        public void gainGift(Player player) {
            player.gainMoney(Player.GIFT_MONEY);
            out.print("恭喜获得2000元!\n");
        }
    }, GIFT_POINT {
        @Override
        public void action(Player player) {
            player.gainPoint(Player.GIFT_POINT);
        }

        @Override
        public void gainGift(Player player) {
            player.gainPoint(Player.GIFT_POINT);
            out.print("恭喜获得200点!\n");
        }
    }, GIFT_GOD {
        @Override
        public void action(Player player) {
            player.gainGod();
        }

        @Override
        public void gainGift(Player player) {
            player.gainGod();
            out.print("恭喜获得福神附身5天!路过其它玩家地盘，均可免费!\n");
        }
    }, GIFT_WRONG_COMMAND, GAME_COMMAND_ROLL {
        @Override
        public void gameCommand(Player player, Game game, int gameCommandNum) {
            player.roll();
        }
    }, GAME_COMAND_BLOCK {
        @Override
        public void gameCommand(Player player, Game game, int gameCommandNum) {
            player.block(gameCommandNum);
            player.getMap().printMap();
        }
    }, GAME_COMMAND_BOMB {
        @Override
        public void gameCommand(Player player, Game game, int gameCommandNum) {
            player.bomb(gameCommandNum);
            player.getMap().printMap();
        }
    }, GAME_COMMAND_ROBOT {
        @Override
        public void gameCommand(Player player, Game game, int gameCommandNum) {
            player.robot();
            player.getMap().printMap();
        }
    }, GAME_COMMAND_SELL {
        @Override
        public void gameCommand(Player player, Game game, int gameCommandNum) {
            player.sell(gameCommandNum);
            game.query(player);
        }
    }, GAME_COMMAND_SELLTOOL {
        @Override
        public void gameCommand(Player player, Game game, int gameCommandNum) {
            player.sellTool(game.getItems().get(gameCommandNum - 1));
            game.query(player);
        }
    }, GAME_COMMAND_QUERY {
        @Override
        public void gameCommand(Player player, Game game, int gameCommandNum) {
            game.query(player);
        }
    }, GAME_COMMAND_HELP {
        @Override
        public void gameCommand(Player player, Game game, int gameCommandNum) {
            game.printHelp();
        }
    }, GAME_COMMAND_QUIT {
        @Override
        public void gameCommand(Player player, Game game, int gameCommandNum) {
            out.print("你失去了一个成为大富翁的机会...\n");
            exit(0);
        }
    }, GAME_WRONG_COMMAND;

    Command() {
    }

    public void action(Player player) {
    }

    public boolean buyTools(Game game, Player player) {
        player.setStatus(STATUS.TURN_END);
        return false;
    }

    public void gainGift(Player player) {
        out.print("输入错误,自动退出礼品屋! What a pity!\n");
    }

    public void gameCommand(Player player, Game game, int gameCommandNum) {
        out.print(Game.WRONG_COMMAND);
    }
}
