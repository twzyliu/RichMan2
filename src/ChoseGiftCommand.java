/**
 * Created by zyongliu on 14/11/16.
 */
public class ChoseGiftCommand implements CommandPreprocess {
    private Command command;

    public ChoseGiftCommand(String command) {
        if (command.equals(Input.GIFT_MONEY)) {
            this.command = Command.GIFT_MONEY;
        } else if (command.equals(Input.GIFT_POINT)) {
            this.command = Command.GIFT_POINT;
        } else if (command.equals(Input.GIFT_GOD)) {
            this.command = Command.GIFT_GOD;
        }
    }

    @Override
    public void action(Player player) {
        command.action(player);
    }
}
