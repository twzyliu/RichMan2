/**
 * Created by zyongliu on 14/11/16.
 */
public class BuyToolCommand {
    private Command command;

    public BuyToolCommand(String command) {
        if (command.equals(Input.TOOLS_EXIT)) {
            this.command = Command.TOOLS_EXIT;
        } else if (command.equals(Input.TOOLS_BARRICADE)) {
            this.command = Command.TOOLS_BARRICADE;
        } else if (command.equals(Input.TOOLS_ROBOT)) {
            this.command = Command.TOOLS_ROBOT;
        } else if (command.equals(Input.TOOLS_BOMB)) {
            this.command = Command.TOOLS_BOMB;
        }
    }

    public void buyToolAction(Player player) {
        command.action(player);
    }
}
