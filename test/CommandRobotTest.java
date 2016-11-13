import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created by zyongliu on 12/11/16.
 */
public class CommandRobotTest {
    private static final int PRICE = 200;
    private static final int STEP = 1;
    private Dice dice;
    private EmptyLand emptyLand;
    private GameMap gameMap;
    private Player player;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        emptyLand = new EmptyLand(PRICE);
        gameMap = new GameMap(new StartingPoint(), emptyLand);
        player = new Player(TestHelper.PLAYER_1, dice, gameMap);
    }

    @Test
    public void cannot_use_robot_when_no_enough_robots() throws Exception {
        int itemsNum = player.getToolsNum();

        assertThat(player.robot(), is(false));
        assertThat(player.getToolsNum(), is(itemsNum));
        assertThat(player.getStatus(), is(STATUS.WAIT_FOR_COMMAND));
    }

    @Test
    public void should_change_map_and_toolsnum_when_use_robot() throws Exception {
        player.gainBarricade();
        player.block(STEP);
        player.gainRobot();
        int itemsNum = player.getToolsNum();

        assertThat(player.robot(), is(true));
        assertThat(player.getToolsNum(), is(itemsNum - 1));
        assertThat(gameMap.getPlace(player.getPosition() + STEP).isEmpty(), is(true));
        assertThat(player.getStatus(), is(STATUS.WAIT_FOR_COMMAND));
    }
}
