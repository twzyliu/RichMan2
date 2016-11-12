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
public class PlayerOnStartingLand {
    private static final String PLAYER_A = "A";
    private Dice dice;
    private GameMap map;
    private Player player;
    private StartingPoint startingPoint;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        map = mock(GameMap.class);
        startingPoint = mock(StartingPoint.class);
        player = new Player(PLAYER_A, dice, map);
        when(map.getPlace(anyInt())).thenReturn(startingPoint);
    }

    @Test
    public void should_end_turn_when_on_startingland() throws Exception {
        player.roll();
        assertThat(player.getStatus(), is(Player.STATUS.TURN_END));
    }
}
