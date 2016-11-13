/**
 * Created by zyongliu on 12/11/16.
 */
public class Hospital extends Place {

    private static final String HOSIPITAL_SYMBOL = "H";

    @Override
    public String getStatusSymbol() {
        return HOSIPITAL_SYMBOL;
    }
}
