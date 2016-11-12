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
    private static final String PLAYER_A = "A";
    private Dice dice;
    private GameMap map;
    private PrisonLand prisonLand;
    private Player player;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        map = mock(GameMap.class);
        prisonLand = mock(PrisonLand.class);
        player = new Player(PLAYER_A, dice, map);
        when(map.getPlace(anyInt())).thenReturn(prisonLand);
    }

    @Test
    public void should_stay_two_days_when_on_prisonland() throws Exception {
        player.roll();
        assertThat(player.getPrisonDays(), is(Player.PRISON_DAY));
        assertThat(player.getStatus(), is(Player.STATUS.TURN_END));
    }
}
