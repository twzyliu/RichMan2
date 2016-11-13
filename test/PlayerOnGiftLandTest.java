import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by zyongliu on 12/11/16.
 */
public class PlayerOnGiftLandTest {
    private Dice dice;
    private GameMap map;
    private Player player;
    private GiftLand giftLand;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        map = mock(GameMap.class);
        giftLand = mock(GiftLand.class);
        player = new Player(TestHelper.PLAYER_1, dice, map);
        when(map.getPlace(anyInt())).thenReturn(giftLand);
    }

    @Test
    public void should_wait_for_input_when_player_walk_to_gift_land() throws Exception {
        assertThat(player.getStatus(), is(Player.STATUS.TURN_START));
        player.roll();
        assertThat(player.getStatus(), is(Player.STATUS.WAIT_FOR_GIFT_COMMAND));
    }

    @Test
    public void can_get_money_when_choose_gift_one() throws Exception {
        player.roll();
        int money = player.getMoney();
        player.command(Command.GIFT_MONEY);

        assertThat(player.getMoney(), is(money + Player.GIFT_MONEY));
        assertThat(player.getStatus(), is(Player.STATUS.TURN_END));
    }

    @Test
    public void can_get_point_when_choose_gift_two() throws Exception {
        player.roll();
        int point = player.getPoint();
        player.command(Command.GIFT_POINT);

        assertThat(player.getPoint(), is(point + Player.GIFT_POINT));
        assertThat(player.getStatus(), is(Player.STATUS.TURN_END));
    }

    @Test
    public void can_get_god_when_choose_gift_three() throws Exception {
        player.roll();
        int godDays = player.getGodDays();
        player.command(Command.GIFT_GOD);

        assertThat(player.getGodDays(), is(godDays + Player.GOD_DAYS));
        assertThat(player.getStatus(), is(Player.STATUS.TURN_END));
    }
}
