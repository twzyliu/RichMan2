import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created by zyongliu on 12/11/16.
 */
public class CommandBlockTest {
    private Dice dice;
    private EmptyLand emptyLand;
    private GameMap gameMap;
    private Player player;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        emptyLand = new EmptyLand(TestHelper.PRICE);
        gameMap = new GameMap(new StartingPoint(), emptyLand);
        player = new Player(TestHelper.PLAYER_1, dice, gameMap);
    }

    @Test
    public void cannot_use_barricade_when_no_enough_barricade() throws Exception {
        int itemsNum = player.getToolsNum();

        assertThat(player.block(TestHelper.STEP), is(false));
        assertThat(player.getToolsNum(), is(itemsNum));
        assertThat(player.getStatus(), is(STATUS.WAIT_FOR_COMMAND));
    }

    @Test
    public void cannot_use_barricade_when_position_is_too_far() throws Exception {
        player.getBarricade().gainItem();
        int itemsNum = player.getToolsNum();

        assertThat(player.block(TestHelper.FAR_STEP), is(false));
        assertThat(player.getToolsNum(), is(itemsNum));
        assertThat(player.getStatus(), is(STATUS.WAIT_FOR_COMMAND));
    }

    @Test
    public void cannot_use_barricade_when_item_on_target() throws Exception {
        player.getBarricade().gainItem();
        player.getBarricade().gainItem();
        player.block(TestHelper.STEP);
        int itemsNum = player.getToolsNum();

        assertThat(player.block(TestHelper.STEP), is(false));
        assertThat(player.getToolsNum(), is(itemsNum));
        assertThat(player.getStatus(), is(STATUS.WAIT_FOR_COMMAND));
    }

    @Test
    public void cannot_use_barricade_when_player_on_target() throws Exception {
        player.getBarricade().gainItem();

        assertThat(player.block(TestHelper.SELF_POSITION), is(false));
        assertThat(player.getToolsNum(), is(player.getToolsNum()));
        assertThat(player.getStatus(), is(STATUS.WAIT_FOR_COMMAND));
    }

    @Test
    public void should_change_map_when_use_barricade() throws Exception {
        player.getBarricade().gainItem();
        int itemsNum = player.getToolsNum();

        assertThat(player.block(TestHelper.STEP), is(true));
        assertThat(player.getToolsNum(), is(itemsNum - 1));
        assertThat(gameMap.getPlace(player.getPosition() + TestHelper.STEP).getStatus(), is(Place.BARRICADE));
        assertThat(player.getStatus(), is(STATUS.WAIT_FOR_COMMAND));
    }
}










