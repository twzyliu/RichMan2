import static java.lang.System.out;

/**
 * Created by zyongliu on 14/11/16.
 */
public class Robot extends Items {
    private static final int point = 30;
    private static final int robotStep = 10;

    public Robot() {
        super();
    }

    @Override
    public boolean use(GameMap gameMap, int position, int step) {
        if (getNum() > 0) {
            gameMap.robotsTool(position, robotStep);
            loseItem();
            out.print("机器娃娃已经出动!\n");
            return true;
        } else {
            out.print("对不起,你穷的连机器娃娃都没有...\n");
            return false;
        }
    }

    public static int getRobotStep() {
        return robotStep;
    }
}
