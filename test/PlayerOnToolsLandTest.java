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
public class PlayerOnToolsLandTest {
    private static final String PLAYER_A = "A";
    public static final int POINT = 1000;
    private Dice dice;
    private GameMap map;
    private Player player;
    private ToolsLand toolsLand;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        map = mock(GameMap.class);
        toolsLand = mock(ToolsLand.class);
        player = new Player(PLAYER_A, dice, map);
        when(map.getPlace(anyInt())).thenReturn(toolsLand);
    }

    @Test
    public void should_wait_for_input_when_player_walk_to_tools_land() throws Exception {
        assertThat(player.getStatus(), is(Player.STATUS.TURN_START));
        player.roll();
        assertThat(player.getStatus(), is(Player.STATUS.WAIT_FOR_TOOLS_COMMAND));
    }

    @Test
    public void turn_end_after_chose_command_F() throws Exception {
        assertThat(player.getStatus(), is(Player.STATUS.TURN_START));
        player.roll();
        player.command(Player.EXIT_BUY_TOOLS);
        assertThat(player.getStatus(), is(Player.STATUS.TURN_END));
    }

    @Test
    public void can_buy_tools_when_have_enough_point_and_space() throws Exception {
        player.gainPoint(POINT);
        player.roll();
        int point = player.getPoint();
        int itemNum = player.getItemsNum();
        player.command(Player.BARRICADE);

        assertThat(player.getPoint(), is(point - Barricade.POINT));
        assertThat(player.getItemsNum(), is(itemNum + 1));
        assertThat(player.getStatus(), is(Player.STATUS.WAIT_FOR_TOOLS_COMMAND));
    }

    @Test
    public void cannot_buy_tools_when_no_enough_point() throws Exception {
        player.roll();
        int point = player.getPoint();
        int itemsNum = player.getItemsNum();
        player.command(Player.BARRICADE);

        assertThat(player.getPoint(), is(point));
        assertThat(player.getItemsNum(), is(itemsNum));
        assertThat(player.getStatus(), is(Player.STATUS.WAIT_FOR_TOOLS_COMMAND));
    }

    @Test
    public void cannot_buy_tools_when_no_enough_space() throws Exception {
        player.gainPoint(POINT);
        player.roll();
        for (int inxex = 0; inxex < Player.MAX_TOOLS_NUM; inxex++) {
            player.command(Player.BARRICADE);
        }

        int point = player.getPoint();
        int itemsNum = player.getItemsNum();
        player.command(Player.BARRICADE);
        assertThat(player.getPoint(), is(point));
        assertThat(player.getItemsNum(), is(itemsNum));
        assertThat(player.getStatus(), is(Player.STATUS.WAIT_FOR_TOOLS_COMMAND));
    }
}
