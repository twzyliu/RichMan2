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
            if (player.getPoint() >= player.getBarricade().getPoint() & player.getToolsNum() < Player.MAX_TOOLS_NUM) {
                player.gainPoint(0 - player.getBarricade().getPoint());
                player.getBarricade().gainItem();
                player.setStatus(STATUS.WAIT_FOR_TOOLS_COMMAND);
            }
        }
    }, TOOLS_ROBOT {
        @Override
        public void action(Player player) {
            if (player.getPoint() >= player.getRobot().getPoint() & player.getToolsNum() < Player.MAX_TOOLS_NUM) {
                player.gainPoint(0 - player.getRobot().getPoint());
                player.getRobot().gainItem();
                player.setStatus(STATUS.WAIT_FOR_TOOLS_COMMAND);
            }
        }
    }, TOOLS_BOMB {
        @Override
        public void action(Player player) {
            if (player.getPoint() >= player.getBomb().getPoint() & player.getToolsNum() < Player.MAX_TOOLS_NUM) {
                player.gainPoint(0 - player.getBomb().getPoint());
                player.getBomb().gainItem();
                player.setStatus(STATUS.WAIT_FOR_TOOLS_COMMAND);
            }
        }
    }, GIFT_MONEY {
        @Override
        public void action(Player player) {
            player.gainMoney(Player.GIFT_MONEY);
            player.setStatus(STATUS.TURN_END);
        }
    }, GIFT_POINT {
        @Override
        public void action(Player player) {
            player.gainPoint(Player.GIFT_POINT);
        }
    }, GIFT_GOD {
        @Override
        public void action(Player player) {
            player.gainGod();
        }
    }, YES, NO;


    Command() {
    }

    public void action(Player player) {

    }
}
