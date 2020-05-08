package Controlleres;

import Messages.Controllers.ShowMessage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BalanceController {

    static float currentBalance, carryoverBalance, totalBalance;
    static String year = Integer.toString(HijriCalendar.getSimpleYear());

    public static float getCurrentBalance() {
        try {
            ResultSet rs = DatabaseAccess.select("balance", "FISCALYEAR='" + year + "'");
            if (rs.next()) {
                currentBalance = rs.getFloat("CURRENTBALANCE");
            }
            rs.close();
        } catch (IOException | SQLException ex) {
            Logger.getLogger(BalanceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return currentBalance;
    }

    public static void setCurrentBalance(float currentBalance) {
        BalanceController.currentBalance = currentBalance;
    }

    public static float getCarryoverBalance() {
        try {
            ResultSet rs = DatabaseAccess.select("balance", "FISCALYEAR='" + year + "'");
            if (rs.next()) {
                carryoverBalance = rs.getFloat("CARRYOVERBALANCE");
            }
            rs.close();
        } catch (IOException | SQLException ex) {
            Logger.getLogger(BalanceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return carryoverBalance;
    }

    public static void setCarryoverBalance(float carryoverBalance) {
        BalanceController.carryoverBalance = carryoverBalance;
    }

    public static float getTotalBalance() {
        try {
            ResultSet rs = DatabaseAccess.select("balance", "FISCALYEAR='" + year + "'");
            if (rs.next()) {
                totalBalance = rs.getFloat("TOTALBALANCE");
            }
            rs.close();
        } catch (IOException | SQLException ex) {
            Logger.getLogger(BalanceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return totalBalance;
    }

    public static void setTotalBalance(float totalBalance) {
        BalanceController.totalBalance = totalBalance;
    }

    public static boolean isBalanceExisting(String validationmassage) {
        boolean status = true;
        try {
            year = Integer.toString(HijriCalendar.getSimpleYear());
            ResultSet rs = DatabaseAccess.select("balance", "FISCALYEAR='" + year + "'");
            if (rs.next()) {

                status = false;
                ShowMessage showMessage = new ShowMessage();
                showMessage.error(validationmassage);
            }
            rs.close();
        } catch (IOException | SQLException ex) {
            Logger.getLogger(BalanceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }

    public static void incrementBalance(float amount) {
        setCurrentBalance(getCurrentBalance());
        setCarryoverBalance(getCarryoverBalance());
        currentBalance = currentBalance + amount;
        setTotalBalance(currentBalance + carryoverBalance);
        float[] data = {currentBalance, totalBalance};
        String tableNmae = "balance";
        String fildeNmae = "`CURRENTBALANCE`=?,`TOTALBALANCE`=?";
        try {
            DatabaseAccess.updat(tableNmae, fildeNmae, data, "FISCALYEAR='" + year + "'");
        } catch (IOException ex) {
            Logger.getLogger(BalanceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void decrementBalance(float amount) {
        setCurrentBalance(getCurrentBalance());
        setCarryoverBalance(getCarryoverBalance());
        currentBalance = currentBalance - amount;
        setTotalBalance(currentBalance+carryoverBalance);
        float[] data = {currentBalance, totalBalance};
        String tableNmae = "balance";
        String fildeNmae = "`CURRENTBALANCE`=?,`TOTALBALANCE`=?";
        try {
            DatabaseAccess.updat(tableNmae, fildeNmae, data, "FISCALYEAR='" + year + "'");
        } catch (IOException ex) {
            Logger.getLogger(BalanceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
