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
public class PlayerOnHospitalTest {
    private Dice dice;
    private GameMap map;
    private Player player;
    private Hospital hospatil;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        map = mock(GameMap.class);
        hospatil = mock(Hospital.class);
        player = new Player(TestHelper.PLAYER_1, dice, map);
        when(map.getPlace(anyInt())).thenReturn(hospatil);
    }

    @Test
    public void should_end_turn_when_on_hospatil() throws Exception {
        player.roll();
        assertThat(player.getStatus(), is(STATUS.TURN_END));
    }
}
