/**
 * Created by zyongliu on 14/11/16.
 */
public class ToolsCommand implements CommandPreprocess {
    private Command command;

    public ToolsCommand(String command) {
        if (command.equals(Input.TOOLS_EXIT)) {
            this.command = Command.TOOLS_EXIT;
        } else if (command.equals(Input.TOOLS_BARRICADE)) {
            this.command = Command.TOOLS_BARRICADE;
        } else if (command.equals(Input.TOOLS_ROBOT)) {
            this.command = Command.TOOLS_ROBOT;
        } else if (command.equals(Input.TOOLS_BOMB)) {
            this.command = Command.TOOLS_BOMB;
        } else {
            this.command = Command.TOOLS_WRONG_COMMAND;
        }
    }

    @Override
    public void action(Player player) {
        command.action(player);
    }

    public boolean buy(Game game, Player player) {
        return command.buyTools(game, player);
    }
}
