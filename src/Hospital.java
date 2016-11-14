import static java.lang.System.out;

/**
 * Created by zyongliu on 12/11/16.
 */
public class Hospital extends Place {

    private static final String HOSIPITAL_SYMBOL = "H";

    @Override
    public String getStatusSymbol() {
        return HOSIPITAL_SYMBOL;
    }

    @Override
    public void playerCome(Player player) {
        out.print("Welcome to Hospital! But we have nothing to do...\n");
        player.setStatus(STATUS.TURN_END);
    }

}
