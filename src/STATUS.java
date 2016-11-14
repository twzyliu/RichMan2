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
            new BuyToolCommand(command).action(player);
        }
    }, WAIT_FOR_GIFT_COMMAND {
        @Override
        public void command(Player player, String command) {
            new ChoseGiftCommand(command).action(player);
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
