/**
 * Created by zyongliu on 11/11/16.
 */
public class Place {

    public static int PLAYER = 1;
    public static int BARRICADE = 2;
    public static int BOMB = 3;
    private int status = 0;

    public int getStatus(){
        return status;
    }

    public void setStatus(int status){
        this.status = status;
    }

    public boolean isEmpty() {
        return status == 0;
    }

}
