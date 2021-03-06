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
    private Dice dice;
    private GameMap map;
    private Player player;
    private ToolsLand toolsLand;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        map = mock(GameMap.class);
        toolsLand = new ToolsLand();
        player = new Player(TestHelper.PLAYER_1, dice, map);
        when(map.getPlace(anyInt())).thenReturn(toolsLand);
    }

    @Test
    public void should_wait_for_input_when_player_walk_to_tools_land() throws Exception {
        assertThat(player.getStatus(), is(STATUS.TURN_START));
        player.gainPoint(TestHelper.MOREPOINT);
        player.roll();
        assertThat(player.getStatus(), is(STATUS.WAIT_FOR_TOOLS_COMMAND));
    }

    @Test
    public void turn_end_after_chose_command_F() throws Exception {
        assertThat(player.getStatus(), is(STATUS.TURN_START));
        player.gainPoint(TestHelper.MOREPOINT);
        player.roll();
        player.command(Input.TOOLS_EXIT);
        assertThat(player.getStatus(), is(STATUS.TURN_END));
    }

    @Test
    public void can_buy_tools_when_have_enough_point_and_space() throws Exception {
        player.gainPoint(TestHelper.MOREPOINT);
        player.roll();
        int point = player.getPoint();
        int itemNum = player.getToolsNum();
        player.command(Input.TOOLS_BARRICADE);

        assertThat(player.getPoint(), is(point - player.getBarricade().getPoint()));
        assertThat(player.getToolsNum(), is(itemNum + 1));
        assertThat(player.getStatus(), is(STATUS.WAIT_FOR_TOOLS_COMMAND));
    }

    @Test
    public void cannot_buy_tools_when_no_enough_point() throws Exception {
        player.gainPoint(40);
        player.roll();
        int point = player.getPoint();
        int itemsNum = player.getToolsNum();
        player.command(Input.TOOLS_BARRICADE);

        assertThat(player.getPoint(), is(point));
        assertThat(player.getToolsNum(), is(itemsNum));
        assertThat(player.getStatus(), is(STATUS.WAIT_FOR_TOOLS_COMMAND));
    }

    @Test
    public void cannot_buy_tools_when_no_enough_space() throws Exception {
        player.gainPoint(TestHelper.MOREPOINT);
        player.roll();
        for (int inxex = 0; inxex < Player.MAX_TOOLS_NUM; inxex++) {
            player.command(Input.TOOLS_BARRICADE);
        }

        int point = player.getPoint();
        int itemsNum = player.getToolsNum();
        player.command(Input.TOOLS_BARRICADE);
        assertThat(player.getPoint(), is(point));
        assertThat(player.getToolsNum(), is(itemsNum));
        assertThat(player.getStatus(), is(STATUS.WAIT_FOR_TOOLS_COMMAND));
    }

    @Test
    public void auto_end_turn_when_no_enough_point_to_buy_anyone() throws Exception {
        player.roll();
        assertThat(player.getPoint() < Items.CHEAPEST, is(true));
        assertThat(player.getStatus(), is(STATUS.TURN_END));
    }
}
