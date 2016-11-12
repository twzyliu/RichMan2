/**
 * Created by zyongliu on 11/11/16.
 */
public class Place {
    public static final int EMPTY = 0;
    public static final int PLAYER = 1;
    public static final int BARRICADE = 2;
    public static final int BOMB = 3;
    private int status = 0;

    public int getStatus(){
        return status;
    }

    public void setStatus(int status){
        this.status = status;
    }

    public boolean isEmpty() {
        return status == EMPTY;
    }

    public void clearToolStatus() {
        if (status > 1) {
            status = 0;
        }
    }
}
