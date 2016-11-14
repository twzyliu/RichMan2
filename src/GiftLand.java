/**
 * Created by zyongliu on 12/11/16.
 */
public class GiftLand extends Place{

    private static final String GIFTLAND_SYMBOL = "G";

    @Override
    public String getStatusSymbol() {
        return GIFTLAND_SYMBOL;
    }

    @Override
    public void playerCome(Player player) {
        player.setStatus(STATUS.WAIT_FOR_GIFT_COMMAND);
    }

}
