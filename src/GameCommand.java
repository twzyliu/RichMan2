import java.util.List;

import static java.lang.System.out;
import static java.util.Arrays.asList;

/**
 * Created by zyongliu on 15/11/16.
 */
public class GameCommand implements CommandPreprocess {
    private Command command;
    private int gameCommandNum;

    public GameCommand(String command) {
        if (command.equals(Input.GAME_COMMAND_ROLL)) {
            this.command = Command.GAME_COMMAND_ROLL;
        } else if (command.contains(Input.GAME_COMAND_BLOCK)) {
            this.gameCommandNum = getCommandNum(command);
            this.command = Command.GAME_COMAND_BLOCK;
        } else if (command.contains(Input.GAME_COMMAND_BOMB)) {
            this.gameCommandNum = getCommandNum(command);
            this.command = Command.GAME_COMMAND_BOMB;
        } else if (command.equals(Input.GAME_COMMAND_ROBOT)) {
            this.command = Command.GAME_COMMAND_ROBOT;
        } else if (command.contains(Input.GAME_COMMAND_SELL)) {
            this.gameCommandNum = getCommandNum(command);
            this.command = Command.GAME_COMMAND_SELL;
        } else if (command.contains(Input.GAME_COMMAND_SELLTOOL)) {
            this.gameCommandNum = getCommandNum(command);
            this.command = Command.GAME_COMMAND_SELLTOOL;
        } else if (command.equals(Input.GAME_COMMAND_QUERY)) {
            this.command = Command.GAME_COMMAND_QUERY;
        } else if (command.equals(Input.GAME_COMMAND_HELP)) {
            this.command = Command.GAME_COMMAND_HELP;
        } else if (command.equals(Input.GAME_COMMAND_QUIT)) {
            this.command = Command.GAME_COMMAND_QUIT;
        } else {
            this.command = Command.GAME_WRONG_COMMAND;
        }
    }

    public void gameAction(Player player, Game game) {
        command.gameCommand(player, game, this.gameCommandNum);
    }

    @Override
    public void action(Player player) {

    }

    private int getCommandNum(String command) {
        List<String> commandList = asList(command.split(" "));
        if (commandList.size() != 2) {
            out.print(Game.WRONG_COMMAND);
            return -100;
        }
        try {
            return Integer.parseInt(commandList.get(1));
        } catch (Exception e) {
            out.print(Game.WRONG_COMMAND);
            return -100;
        }
    }
}
