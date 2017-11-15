package nightshop.debuck.info.nightshop.AppClass;

import java.io.Serializable;

/**
 * Created by steeves on 05/09/2017.
 */

public class AppVar implements Serializable {

    private int ScanOK;
    private int ScanKO;

    /**
     * PUBLIC CONSTRUCTORS
     */
    public AppVar() {}

    public AppVar(int scanok, int scanko){
        this.ScanOK = scanok;
        this.ScanKO = scanko;

    }

    /**
     * GETTERS AND SETTERS
     */

    public int getScanOK() {
        return ScanOK;
    }

    public void setScanOK(int scanOK) {
        ScanOK = scanOK;
    }

    public int getScanKO() {
        return ScanKO;
    }

    public void setScanKO(int scanKO) {
        ScanKO = scanKO;
    }

    public String toString(){
        return ("[AppVar: ScanOK=" + getScanOK() + ", ScanKO=" +
                getScanKO() +"]");
    }

}
