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
public class PlayerOnPrisonTest {
    private Dice dice;
    private GameMap map;
    private Prison prison;
    private Player player;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        map = mock(GameMap.class);
        prison = new Prison();
        player = new Player(TestHelper.PLAYER_1, dice, map);
        when(map.getPlace(anyInt())).thenReturn(prison);
    }

    @Test
    public void should_stay_two_days_when_on_prison() throws Exception {
        player.roll();
        assertThat(player.getPrisonDays(), is(Player.PRISON_DAY));
        assertThat(player.getStatus(), is(STATUS.TURN_END));
    }
}
