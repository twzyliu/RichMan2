import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by zyongliu on 12/11/16.
 */
public class GameMapTest {

    private GameMap gameMap;
    private Hospital hospital;

    @Before
    public void setUp() throws Exception {
        EmptyLand emptyLand = new EmptyLand(TestHelper.PRICE);
        hospital = new Hospital();
        gameMap = new GameMap(new StartingPoint(), emptyLand, hospital);
    }

    @Test
    public void should_return_random_number_between_1_and_6_when_dice_roll() throws Exception {
        Dice dice = new Dice();
        assertThat(dice.roll() >= 1, is(true));
        assertThat(dice.roll() <= 6, is(true));
    }

    @Test
    public void should_stop_when_player_encounter_barricade() throws Exception {
        Dice dice = mock(Dice.class);
        when(dice.roll()).thenReturn(TestHelper.STEP + 1);
        Player player = new Player(TestHelper.PLAYER_1, dice, gameMap);
        int position = player.getPosition();
        player.gainBarricade();
        player.block(TestHelper.STEP);
        player.roll();

        assertThat(player.getPosition(), is(position + TestHelper.STEP));
    }

    @Test
    public void should_goto_hosipital_when_player_encounter_bomb() throws Exception {
        Dice dice = mock(Dice.class);
        when(dice.roll()).thenReturn(TestHelper.STEP);
        Player player = new Player(TestHelper.PLAYER_1, dice, gameMap);
        player.gainBomb();
        player.bomb(TestHelper.STEP);
        player.roll();

        assertThat(player.getPosition(), is(gameMap.getHosipitalPosition()));
        assertThat(player.getHosipitalDays(), is(Player.HOSIPITAL_DAY));
    }
}
