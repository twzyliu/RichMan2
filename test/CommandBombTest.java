import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created by zyongliu on 12/11/16.
 */
public class CommandBombTest {
    public static final int STEP = 1;
    private static final int FAR_STEP = 15;
    public static final int PRICE = 200;
    public static final int SELF_POSITION = 0;
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
    public void cannot_use_bomb_when_no_enough_bombs() throws Exception {
        int itemsNum = player.getToolsNum();

        assertThat(player.bomb(STEP), is(false));
        assertThat(player.getToolsNum(), is(itemsNum));
        assertThat(player.getStatus(), is(Player.STATUS.WAIT_FOR_COMMAND));
    }

    @Test
    public void cannot_use_bomb_when_position_is_too_far() throws Exception {
        player.gainBomb();
        int itemsNum = player.getToolsNum();

        assertThat(player.bomb(FAR_STEP), is(false));
        assertThat(player.getToolsNum(), is(itemsNum));
        assertThat(player.getStatus(), is(Player.STATUS.WAIT_FOR_COMMAND));
    }

    @Test
    public void cannot_use_bomb_when_item_on_target() throws Exception {
        player.gainBomb();
        player.gainBomb();
        player.bomb(STEP);
        int itemsNum = player.getToolsNum();

        assertThat(player.bomb(STEP), is(false));
        assertThat(player.getToolsNum(), is(itemsNum));
        assertThat(player.getStatus(), is(Player.STATUS.WAIT_FOR_COMMAND));
    }

    @Test
    public void cannot_use_bomb_when_player_on_target() throws Exception {
        player.gainBomb();

        assertThat(player.bomb(SELF_POSITION), is(false));
        assertThat(player.getToolsNum(), is(player.getToolsNum()));
        assertThat(player.getStatus(), is(Player.STATUS.WAIT_FOR_COMMAND));
    }

    @Test
    public void should_change_map_when_use_bomb() throws Exception {
        player.gainBomb();
        int itemsNum = player.getToolsNum();

        assertThat(player.bomb(STEP), is(true));
        assertThat(player.getToolsNum(), is(itemsNum - 1));
        assertThat(gameMap.getPlace(player.getPosition() + STEP).getStatus(), is(Place.BOMB));
        assertThat(player.getStatus(), is(Player.STATUS.WAIT_FOR_COMMAND));
    }
}
