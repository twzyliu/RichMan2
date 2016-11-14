/**
 * Created by zyongliu on 12/11/16.
 */
public class Prison extends Place {

    private static final String PRISON_SYMBOL = "P";

    @Override
    public String getStatusSymbol() {
        return PRISON_SYMBOL;
    }

    @Override
    public void playerCome(Player player) {
        player.gotoPrison();
    }
}
