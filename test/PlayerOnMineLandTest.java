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
    private Dice dice;
    private GameMap map;
    private Player player;
    private MineLand mineLand;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        map = mock(GameMap.class);
        mineLand = new MineLand(TestHelper.POINT);
        player = new Player(TestHelper.PLAYER_1, dice, map);
        when(map.getPlace(anyInt())).thenReturn(mineLand);
    }

    @Test
    public void should_get_point_when_on_mineland() throws Exception {
        int point = player.getPoint();
        player.roll();
        assertThat(player.getPoint(), is(point + mineLand.getPoint()));
        assertThat(player.getStatus(), is(STATUS.TURN_END));
    }
}
