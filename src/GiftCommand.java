/**
 * Created by zyongliu on 14/11/16.
 */
public class GiftCommand implements CommandPreprocess {
    private Command command;

    public GiftCommand(String command) {
        if (command.equals(Input.GIFT_GOD)) {
            this.command = Command.GIFT_GOD;
        } else if (command.equals(Input.GIFT_MONEY)) {
            this.command = Command.GIFT_MONEY;
        } else if (command.equals(Input.GIFT_POINT)) {
            this.command = Command.GIFT_POINT;
        } else {
            this.command = Command.GIFT_WRONG_COMMAND;
        }
    }

    @Override
    public void action(Player player) {
        command.action(player);
    }
}
