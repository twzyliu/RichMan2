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
public class PlayerOnMineLandTest {
    private static final String PLAYER_A = "A";
    public static final int POINT = 20;
    private Dice dice;
    private GameMap map;
    private Player player;
    private MineLand mineLand;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        map = mock(GameMap.class);
        mineLand = mock(MineLand.class);
        player = new Player(PLAYER_A, dice, map);
        when(map.getPlace(anyInt())).thenReturn(mineLand);
    }

    @Test
    public void should_get_point_when_on_mineland() throws Exception {
        when(mineLand.getPoint()).thenReturn(POINT);

        int point = player.getPoint();
        player.roll();
        assertThat(player.getPoint(), is(point + mineLand.getPoint()));
        assertThat(player.getStatus(), is(Player.STATUS.TURN_END));

    }
}
