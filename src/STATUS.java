/**
 * Created by zyongliu on 14/11/16.
 */
public enum STATUS {
    TURN_END, WAIT_FOR_BUY_COMMAND {
        @Override
        public void sayYes(Player player) {
            player.setStatus(STATUS.TURN_END);
            player.buyLand();
        }
    }, WAIT_FOR_UPGRADE_COMMAND {
        @Override
        public void sayYes(Player player) {
            player.setStatus(STATUS.TURN_END);
            player.upgradeLand();
        }
    }, TURN_START, GAME_OVER, WAIT_FOR_TOOLS_COMMAND {
        @Override
        public void command(Player player, String command) {
            int point = player.getPoint();
            if (command.equals(Command.TOOLS_EXIT)) {
                player.setStatus(STATUS.TURN_END);
            } else if (command.equals(Command.TOOLS_BARRICADE)) {
                if (point >= Items.Barricade.getPoint() & player.getToolsNum() < Player.MAX_TOOLS_NUM) {
                    player.gainPoint(0 - Items.Barricade.getPoint());
                    player.gainBarricade();
                    player.setStatus(STATUS.WAIT_FOR_TOOLS_COMMAND);
                }
            } else if (command.equals(Command.TOOLS_ROBOT)) {
                if (point >= Items.Robot.getPoint() & player.getToolsNum() < Player.MAX_TOOLS_NUM) {
                    player.gainPoint(0 - Items.Robot.getPoint());
                    player.gainRobot();
                    player.setStatus(STATUS.WAIT_FOR_TOOLS_COMMAND);
                }
            } else if (command.equals(Command.TOOLS_BOMB)) {
                if (point >= Items.Bomb.getPoint() & player.getToolsNum() < Player.MAX_TOOLS_NUM) {
                    player.gainPoint(0 - Items.Bomb.getPoint());
                    player.gainBomb();
                    player.setStatus(STATUS.WAIT_FOR_TOOLS_COMMAND);
                }
            }
        }
    }, WAIT_FOR_GIFT_COMMAND {
        @Override
        public void command(Player player, String command) {
            if (command.equals(Command.GIFT_MONEY)) {
                player.gainMoney(Player.GIFT_MONEY);
                player.setStatus(STATUS.TURN_END);
            } else if (command.equals(Command.GIFT_POINT)) {
                player.gainPoint(Player.GIFT_POINT);
            } else if (command.equals(Command.GIFT_GOD)) {
                player.gainGod();
            }
            player.setStatus(STATUS.TURN_END);
        }
    }, WAIT_FOR_COMMAND;

    public void sayYes(Player player) {
        player.setStatus(STATUS.TURN_END);
    }

    public void sayNo(Player player) {
        player.setStatus(STATUS.TURN_END);
    }

    public void command(Player player, String command) {

    }
}
