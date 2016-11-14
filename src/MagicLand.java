/**
 * Created by zyongliu on 12/11/16.
 */
public class MagicLand extends Place {

    private static final String MAGICLAND_SYMBOL = "M";

    @Override
    public String getStatusSymbol() {
        return MAGICLAND_SYMBOL;
    }

    @Override
    public void playerCome(Player player) {
        player.gotoMagicLand();
    }

}
