import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by zyongliu on 12/11/16.
 */
public class CommandSellTest {
    private static final String PLAYER_A = "A";
    public static final int PRICE = 200;
    public static final int MYLAND = 1;
    public static final int INIT_LEVEL = 0;
    private Dice dice;
    private EmptyLand emptyLand;
    private GameMap gameMap;
    private Player player;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        gameMap = mock(GameMap.class);
        emptyLand = new EmptyLand(PRICE);
        player = new Player(PLAYER_A, dice, gameMap);
        when(gameMap.getPlace(anyInt())).thenReturn(emptyLand);
    }

    @Test
    public void cannot_sell_land_when_not_have_land() throws Exception {
        assertThat(player.sell(MYLAND), is(false));
        assertThat(player.getStatus(), is(Player.STATUS.WAIT_FOR_COMMAND));
    }

    @Test
    public void should_change_money_and_emptyland_status_when_sell() throws Exception {
        player.buyLand();
        int money = player.getMoney();
        int placeNum = player.getPlaces().size();

        assertThat(player.sell(MYLAND), is(true));
        assertThat(player.getMoney(), is(money + emptyLand.getSellMoney()));
        assertThat(emptyLand.getLevel(), is(INIT_LEVEL));
        assertNull(emptyLand.getOwner());
        assertThat(player.getPlaces().size(), is(placeNum - 1));
        assertThat(player.getStatus(), is(Player.STATUS.WAIT_FOR_COMMAND));
    }
}
