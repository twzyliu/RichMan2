import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by zyongliu on 11/11/16.
 */
public class PlayerOnOwnLandTest {

    private static final String PLAYER_A = "A";
    private static final int LOWPRICE = 200;
    private static final int HIGHPRICE = 200000;
    private Dice dice;
    private GameMap map;
    private EmptyLand emptyLand;
    private Player player;


    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        map = mock(GameMap.class);
        emptyLand = mock(EmptyLand.class);
        player = new Player(PLAYER_A, dice, map);
    }

    @Test
    public void should_wait_for_input_when_player_walk_to_my_own_land() throws Exception {
        when(map.getPlace(anyInt())).thenReturn(emptyLand);
        when(emptyLand.getOwner()).thenReturn(player);

        assertThat(player.getStatus(), is(Player.STATUS.TURN_START));
        player.roll();
        assertThat(player.getStatus(), is(Player.STATUS.WAIT_FOR_UPGRADE_COMMAND));
    }

    @Test
    public void turn_end_after_say_no() throws Exception {
        when(map.getPlace(anyInt())).thenReturn(emptyLand);
        when(emptyLand.getOwner()).thenReturn(player);

        player.roll();
        player.sayNo();

        assertThat(player.getStatus(), is(Player.STATUS.TURN_END));
    }

    @Test
    public void can_upgrade_land_when_have_enough_money() throws Exception {
        EmptyLand emptyLand = new EmptyLand(LOWPRICE);
        when(map.getPlace(anyInt())).thenReturn(emptyLand);

        player.roll();
        player.sayYes();
        player.roll();
        int money = player.getMoney();
        int level = emptyLand.getLevel();
        player.sayYes();

        assertThat(player.getStatus(), is(Player.STATUS.TURN_END));
        assertThat(player.getMoney(), is(money - emptyLand.getPrice()));
        assertThat(emptyLand.getLevel(), is(level + 1));
    }

    @Test
    public void cannot_upgrade_land_when_no_enough_money() throws Exception {
        EmptyLand emptyLand = new EmptyLand(HIGHPRICE);
        when(map.getPlace(anyInt())).thenReturn(emptyLand);

        player.roll();
        int money = player.getMoney();
        int level = emptyLand.getLevel();
        player.sayYes();

        assertThat(player.getStatus(), is(Player.STATUS.TURN_END));
        assertThat(player.getMoney(), is(money));
        assertThat(emptyLand.getLevel(), is(level));
    }

    @Test
    public void cannot_upgrade_land_when_level_is_max() throws Exception {
        EmptyLand emptyLand = new EmptyLand(LOWPRICE);
        when(map.getPlace(anyInt())).thenReturn(emptyLand);

        player.roll();
        player.sayYes();
        player.roll();
        player.sayYes();
        player.roll();
        player.sayYes();
        player.roll();
        player.sayYes();
        player.roll();
        int money = player.getMoney();
        player.sayYes();

        assertThat(player.getStatus(), is(Player.STATUS.TURN_END));
        assertThat(emptyLand.getLevel(), is(EmptyLand.MAXLEVEL));
        assertThat(player.getMoney(), is(money));
    }

}
