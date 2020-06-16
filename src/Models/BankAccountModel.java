package Models;

import Controlleres.BalanceController;
import Controlleres.DatabaseAccess;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BankAccountModel {

    static String balanceId = BalanceController.getBalanceId();
    String oprationid, oprationdate, oprationtype, amount, depositorname, notes;
    static float totalcost, totalpaymanet, totalbalance, Initialbalance;

    public BankAccountModel(String oprationid, String oprationdate, String oprationtype, String amount, String depositorname, String notes) {
        this.oprationid = oprationid;
        this.oprationdate = oprationdate;
        this.oprationtype = oprationtype;
        this.amount = amount;
        this.depositorname = depositorname;
        this.notes = notes;
    }

    public String getOprationid() {
        return oprationid;
    }

    public void setOprationid(String oprationid) {
        this.oprationid = oprationid;
    }

    public String getOprationdate() {
        return oprationdate;
    }

    public void setOprationdate(String oprationdate) {
        this.oprationdate = oprationdate;
    }

    public String getOprationtype() {
        return oprationtype;
    }

    public void setOprationtype(String oprationtype) {
        this.oprationtype = oprationtype;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDepositorname() {
        return depositorname;
    }

    public void setDepositorname(String depositorname) {
        this.depositorname = depositorname;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public static float getTotalcost() {
        try {
            ResultSet rs = DatabaseAccess.select("bankopration", "AMOUNT", "TOTALCOST", "OPRATIONTYPE='سحب' AND BALANCEID='" + balanceId + "'");
            if (rs.next()) {
                totalcost = rs.getFloat("TOTALCOST");
            }
            rs.close();
        } catch (IOException | SQLException ex) {
            Logger.getLogger(BankAccountModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return totalcost;
    }

    public void setTotalcost(float totalcost) {
        this.totalcost = totalcost;
    }

    public static float getTotalpaymanet() {
        try {
            ResultSet rs = DatabaseAccess.select("bankopration", "AMOUNT", "TOTALPAYMANET", "OPRATIONTYPE='ايداع' AND BALANCEID='" + balanceId + "'");
            if (rs.next()) {
                totalpaymanet = rs.getFloat("TOTALPAYMANET");
            }
            rs.close();
        } catch (IOException | SQLException ex) {
            Logger.getLogger(BankAccountModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return totalpaymanet;
    }

    public void setTotalpaymanet(float totalpaymanet) {
        this.totalpaymanet = totalpaymanet;
    }

    public static float getTotalbalance() {
        try {
            ResultSet rs = DatabaseAccess.select("bankaccount", "BALANCEID='" + balanceId + "'");

            if (rs.next()) {
                totalbalance = rs.getFloat("TOTALBALANCE");
            }
            rs.close();
        } catch (IOException | SQLException ex) {
            Logger.getLogger(BankAccountModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return totalbalance;
    }

    public static void setTotalbalance(float totalbalance) {
        BankAccountModel.totalbalance = totalbalance;
    }

    public static float getInitialbalance() {
        try {
            ResultSet rs = DatabaseAccess.select("bankaccount","BALANCEID='" + balanceId + "'");
            if (rs.next()) {
                Initialbalance = rs.getFloat("INITIALBALANCE");
            }
            rs.close();
        } catch (IOException | SQLException ex) {
            Logger.getLogger(BankAccountModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Initialbalance;
    }

    public static void setInitialbalance(float Initialbalance) {
        BankAccountModel.Initialbalance = Initialbalance;
    }

    public static void incrementBalance(float amount) {
        setTotalbalance(getTotalbalance());
        totalbalance = totalbalance + amount;
        float[] data = {totalbalance};
        String tableNmae = "bankaccount";
        String fildeNmae = "`TOTALBALANCE`=?";
        try {
            DatabaseAccess.updat(tableNmae, fildeNmae, data, "BALANCEID='" + balanceId + "'");
        } catch (IOException ex) {
            Logger.getLogger(BankAccountModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void decrementBalance(float amount) {
        setTotalbalance(getTotalbalance());
        totalbalance = totalbalance - amount;
        float[] data = {totalbalance};
        String tableNmae = "bankaccount";
        String fildeNmae = "`TOTALBALANCE`=?";
        try {
            DatabaseAccess.updat(tableNmae, fildeNmae, data, "BALANCEID='" + balanceId + "'");
        } catch (IOException ex) {
            Logger.getLogger(BankAccountModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
