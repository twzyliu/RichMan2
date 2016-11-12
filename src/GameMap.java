/**
 * Created by zyongliu on 11/11/16.
 */
public interface GameMap {
    int move(int position, int roll);

    Place getPlace(int position);
}
