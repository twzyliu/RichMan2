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
            player.getBarricade().buy(player);
        }
    }, TOOLS_ROBOT {
        @Override
        public void action(Player player) {
            player.getRobot().buy(player);
        }
    }, TOOLS_BOMB {
        @Override
        public void action(Player player) {
            player.getBomb().buy(player);
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
