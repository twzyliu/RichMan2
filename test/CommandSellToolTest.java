import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created by zyongliu on 12/11/16.
 */
public class CommandSellToolTest {
    private Dice dice;
    private GameMap gameMap;
    private Player player;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        gameMap = mock(GameMap.class);
        player = new Player(TestHelper.PLAYER_1, dice, gameMap);
    }

    @Test
    public void cannot_sell_tool_when_not_have_tools() throws Exception {
        assertThat(player.sellTool(Items.Barricade), is(false));
        assertThat(player.getStatus(), is(STATUS.WAIT_FOR_COMMAND));
    }

    @Test
    public void should_change_point_and_toolsnum_when_selltool() throws Exception {
        player.gainBarricade();
        int point = player.getPoint();
        int toolsNum = player.getToolsNum();

        assertThat(player.sellTool(Items.Barricade), is(true));
        assertThat(player.getPoint(), is(point+ Items.Barricade.getPoint()));
        assertThat(player.getToolsNum(), is(toolsNum - 1));
        assertThat(player.getStatus(), is(STATUS.WAIT_FOR_COMMAND));
    }
}
