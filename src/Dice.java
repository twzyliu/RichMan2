import static java.lang.Math.random;

/**
 * Created by zyongliu on 11/11/16.
 */
public class Dice {

    int roll() {
        return (int) ((random() * 6) + 1);
    }
}
