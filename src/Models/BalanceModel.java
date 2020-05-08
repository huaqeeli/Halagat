
package Models;


public class BalanceModel {
    String fiscalyearname,year;
    int currentbalance,carryoverbalance,totalbalane;

    public BalanceModel(String fiscalyearname, String year, int currentbalance, int carryoverbalance, int totalbalane) {
        this.fiscalyearname = fiscalyearname;
        this.year = year;
        this.currentbalance = currentbalance;
        this.carryoverbalance = carryoverbalance;
        this.totalbalane = totalbalane;
    }

    public String getFiscalyearname() {
        return fiscalyearname;
    }

    public void setFiscalyearname(String fiscalyearname) {
        this.fiscalyearname = fiscalyearname;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getCurrentbalance() {
        return currentbalance;
    }

    public void setCurrentbalance(int currentbalance) {
        this.currentbalance = currentbalance;
    }

    public int getCarryoverbalance() {
        return carryoverbalance;
    }

    public void setCarryoverbalance(int carryoverbalance) {
        this.carryoverbalance = carryoverbalance;
    }

    public int getTotalbalane() {
        return totalbalane;
    }

    public void setTotalbalane(int totalbalane) {
        this.totalbalane = totalbalane;
    }
    
    
}
