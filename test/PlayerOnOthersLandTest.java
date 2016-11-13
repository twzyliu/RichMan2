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
public class PlayerOnOthersLandTest {
    private Dice dice;
    private GameMap map;
    private EmptyLand emptyLand;
    private Player player;
    private Player other;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        map = mock(GameMap.class);
        emptyLand = mock(EmptyLand.class);
        player = new Player(TestHelper.PLAYER_1, dice, map);
        player.gainMoney(Game.DEFAULT_MONEY);
        other = new Player(TestHelper.PLAYER_2, dice, map);
        other.gainMoney(Game.DEFAULT_MONEY);
        when(map.getPlace(anyInt())).thenReturn(emptyLand);
        when(emptyLand.getOwner()).thenReturn(other);
    }

    @Test
    public void should_pay_when_player_walk_to_others_land_and_have_enough_money() throws Exception {
        when(emptyLand.getBill()).thenReturn(TestHelper.LOWPRICE);

        int money = player.getMoney();
        assertThat(money > emptyLand.getBill(), is(true));
        player.roll();
        assertThat(player.getStatus(), is(STATUS.TURN_END));
        assertThat(player.getMoney(), is(money - emptyLand.getBill()));
    }

    @Test
    public void should_game_over_when_player_walk_to_others_land_and_no_enough_money() throws Exception {
        when(emptyLand.getBill()).thenReturn(TestHelper.HIGHPRICE);

        int money = player.getMoney();
        assertThat(money < emptyLand.getBill(), is(true));
        player.roll();
        assertThat(player.getStatus(), is(STATUS.GAME_OVER));
    }

    @Test
    public void should_not_pay_when_player_walk_to_others_land_and_hasgod() throws Exception {
        when(emptyLand.getBill()).thenReturn(TestHelper.LOWPRICE);

        player.gainGod();
        int money = player.getMoney();
        player.roll();
        assertThat(player.getMoney(), is(money));
        assertThat(player.getStatus(), is(STATUS.TURN_END));
    }

    @Test
    public void should_not_pay_when_player_walk_to_others_land_and_inprison() throws Exception {
        when(emptyLand.getBill()).thenReturn(TestHelper.LOWPRICE);

        other.gotoPrison();
        int money = player.getMoney();
        player.roll();
        assertThat(player.getMoney(), is(money));
        assertThat(player.getStatus(), is(STATUS.TURN_END));
    }

    @Test
    public void should_not_pay_when_player_walk_to_others_land_and_inhospital() throws Exception {
        when(emptyLand.getBill()).thenReturn(TestHelper.LOWPRICE);

        other.gotoHosipital();
        int money = player.getMoney();
        player.roll();
        assertThat(player.getMoney(), is(money));
        assertThat(player.getStatus(), is(STATUS.TURN_END));
    }
}















