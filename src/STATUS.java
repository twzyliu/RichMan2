import java.util.Scanner;

import static java.lang.System.out;

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

        @Override
        public void action(Player player, Game game) {
            player.getMap().printMap();
            game.buyAndUpgradeActioin(player, "是否购买该处空地，%s元（Y/N）?\n");
        }
    }, WAIT_FOR_UPGRADE_COMMAND {
        @Override
        public void sayYes(Player player) {
            player.setStatus(STATUS.TURN_END);
            player.upgradeLand();
        }

        @Override
        public void action(Player player, Game game) {
            player.getMap().printMap();
            game.buyAndUpgradeActioin(player, "是否升级该处地产，%s元（Y/N）?\n");
        }
    }, TURN_START, GAME_OVER, WAIT_FOR_TOOLS_COMMAND {
        @Override
        public void command(Player player, String command) {
            new ToolsCommand(command).action(player);
        }

        @Override
        public void action(Player player, Game game) {
            out.printf("欢迎光临道具屋， 请选择您所需要的道具：\n" +
                    " 道 具     编号     价值（点数）     显示方式\n" +
                    " 路 障\t1\t50\t＃\n" +
                    "机器娃娃\t2\t30\n" +
                    " 炸 弹\t3\t50\t@\n" +
                    "你现在拥有%s点,按F键退出\n", player.getPoint());
            boolean notFinish = true;
            while (notFinish) {
                Scanner scanner = new Scanner(System.in);
                String command = scanner.nextLine().toLowerCase();
                ToolsCommand toolsCommand = new ToolsCommand(command);
                notFinish = toolsCommand.buy(game, player);
            }
        }
    }, WAIT_FOR_GIFT_COMMAND {
        @Override
        public void command(Player player, String command) {
            new ChoseGiftCommand(command).action(player);
            player.setStatus(STATUS.TURN_END);
        }

        @Override
        public void action(Player player, Game game) {
            player.setStatus(STATUS.TURN_END);
            out.printf("欢迎光临礼品屋，请选择一件您 喜欢的礼品：\n" +
                    "礼品    编号\n" +
                    "奖 金    1\n" +
                    "点数卡   2\n" +
                    "福 神    3\n");
            new GiftCommand(new Scanner(System.in).nextLine().toLowerCase()).action(player);
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

    public void action(Player player, Game game) {
    }
}
