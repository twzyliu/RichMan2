import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by zyongliu on 11/11/16.
 */
public class PlayerOnEmptyLandTest {

    private Dice dice;
    private GameMap map;
    private EmptyLand emptyLand;
    private Player player;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        map = mock(GameMap.class);
        emptyLand = new EmptyLand(TestHelper.LOWPRICE);
        player = new Player(TestHelper.PLAYER_1, dice, map);
        player.gainMoney(Game.DEFAULT_MONEY);
        when(map.getPlace(anyInt())).thenReturn(emptyLand);
    }

    @Test
    public void should_wait_for_input_when_player_walk_to_empty_land() throws Exception {
        assertThat(player.getStatus(), is(STATUS.TURN_START));
        player.roll();
        assertThat(player.getStatus(), is(STATUS.WAIT_FOR_BUY_COMMAND));
    }

    @Test
    public void turn_end_after_say_no() throws Exception {
        player.roll();
        player.sayNo();
        assertThat(player.getStatus(), is(STATUS.TURN_END));
    }

    @Test
    public void can_buy_land_when_have_enough_money() throws Exception {
        EmptyLand emptyLand = new EmptyLand(TestHelper.LOWPRICE);
        when(map.getPlace(anyInt())).thenReturn(emptyLand);

        player.roll();
        int money = player.getMoney();
        player.sayYes();

        assertThat(player.getStatus(), is(STATUS.TURN_END));
        assertThat(player.getMoney(), is(money - emptyLand.getPrice()));
        assertThat(player.getPlaces().contains(emptyLand), is(true));
        assertThat(emptyLand.getOwner(), is(player));
    }

    @Test
    public void cannot_buy_land_when_no_enough_money() throws Exception {
        EmptyLand emptyLand = new EmptyLand(TestHelper.HIGHPRICE);
        when(map.getPlace(anyInt())).thenReturn(emptyLand);

        player.roll();
        int money = player.getMoney();
        player.sayYes();

        assertThat(player.getStatus(), is(STATUS.TURN_END));
        assertThat(player.getMoney(), is(money));
        assertThat(player.getPlaces().contains(emptyLand), is(false));
        assertNotEquals(emptyLand.getOwner(), player);
    }
}






















