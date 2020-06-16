package Controlleres;

import Messages.Controllers.ShowMessage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BalanceController {

    static float currentBalance, carryoverBalance, totalBalance, totalExpenses, totalOfloans, totalOfPaymentAmount, totalOfremainingAmount, remainingAmount, totalRevenues;
    static String opened = "opened";
    static String close = "close";
    static String newbalance = "new";

    public static float getCurrentBalance() {
        try {
            ResultSet rs = DatabaseAccess.select("balance", "STATUSE ='" + opened + "'");
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
            ResultSet rs = DatabaseAccess.select("balance", "STATUSE ='" + opened + "'");
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
            ResultSet rs = DatabaseAccess.select("balance", "STATUSE ='" + opened + "'");
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

    public static float getTotalExpenses() {
        try {
            ResultSet rs = DatabaseAccess.select("expenses", "AMOUNT", "TOTALEXPENSES","BALANCEID = '"+getBalanceId()+"'");
            if (rs.next()) {
                totalExpenses = rs.getFloat("TOTALEXPENSES");
            }
            rs.close();
        } catch (IOException | SQLException ex) {
            Logger.getLogger(BalanceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return totalExpenses;
    }

    public static void setTotalExpenses(float totalExpenses) {
        BalanceController.totalExpenses = totalExpenses;
    }
    
    public static float getTotalOfloans() {
         try {
            ResultSet rs = DatabaseAccess.select("loans", "LOANSAMOUNT","TOTALLOANS","BALANCEID = '"+getBalanceId()+"'");
            if (rs.next()) {
                totalOfloans = rs.getFloat("TOTALLOANS");
            }
            rs.close();
        } catch (IOException | SQLException ex) {
            Logger.getLogger(BalanceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return totalOfloans;
    }

    public static void setTotalOfloans(float totalOfloans) {
        BalanceController.totalOfloans = totalOfloans;
    }

    public static float getTotalOfPaymentAmount() {
         try {
            ResultSet rs = DatabaseAccess.select("loans", "PAYMENTAMOUNT","TOTALPAYMENTAMOUNT","BALANCEID = '"+getBalanceId()+"'");
            if (rs.next()) {
                totalOfPaymentAmount = rs.getFloat("TOTALPAYMENTAMOUNT");
            }
            rs.close();
        } catch (IOException | SQLException ex) {
            Logger.getLogger(BalanceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return totalOfPaymentAmount;
    }

    public static void setTotalOfPaymentAmount(float totalOfPaymentAmount) {
        BalanceController.totalOfPaymentAmount = totalOfPaymentAmount;
    }

    public static float getTotalOfremainingAmount() {
        try {
            ResultSet rs = DatabaseAccess.select("loans", "REMAININGAMOUNT","TOTALREMAININGAMOUNT","BALANCEID = '"+getBalanceId()+"'");
            if (rs.next()) {
                totalOfremainingAmount = rs.getFloat("TOTALREMAININGAMOUNT");
            }
            rs.close();
        } catch (IOException | SQLException ex) {
            Logger.getLogger(BalanceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return totalOfremainingAmount;
    }

    public static void setTotalOfremainingAmount(float totalOfremainingAmount) {
        BalanceController.totalOfremainingAmount = totalOfremainingAmount;
    }

    public static float getRemainingAmount(String loansid) {
        try {
            ResultSet rs = DatabaseAccess.select("loans","LOANSID = '"+loansid+"'");
            if (rs.next()) {
                remainingAmount = rs.getFloat("LOANSAMOUNT")- rs.getFloat("PAYMENTAMOUNT");
            }
            rs.close();
        } catch (IOException | SQLException ex) {
            Logger.getLogger(BalanceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return remainingAmount;
    }

    public static void setRemainingAmount(float remainingAmount) {
        BalanceController.remainingAmount = remainingAmount;
    }

    public static float getTotalRevenues() {
         try {
            ResultSet rs = DatabaseAccess.select("revenues", "AMOUNT","TOTALREVENUESAMOUNT","BALANCEID = '"+getBalanceId()+"'");
            if (rs.next()) {
                totalRevenues = rs.getFloat("TOTALREVENUESAMOUNT");
            }
            rs.close();
        } catch (IOException | SQLException ex) {
            Logger.getLogger(BalanceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return totalRevenues;
    }

    public static void setTotalRevenues(float totalRevenues) {
        BalanceController.totalRevenues = totalRevenues;
    }

    public static boolean isBalanceExisting(String validationmassage) {
        boolean status = true;
        try {

            ResultSet rs = DatabaseAccess.select("balance", "STATUSE = '" + newbalance + "'");
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

    public static boolean isNewBalanceExisting(String validationmassage) {
        boolean status = true;
        try {

            ResultSet rs = DatabaseAccess.select("balance", "STATUSE = '" + newbalance + "'");
            if (!rs.next()) {
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
            DatabaseAccess.updat(tableNmae, fildeNmae, data, "STATUSE ='" + opened + "'");
        } catch (IOException ex) {
            Logger.getLogger(BalanceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void decrementBalance(float amount) {
        setCurrentBalance(getCurrentBalance());
        setCarryoverBalance(getCarryoverBalance());
        currentBalance = currentBalance - amount;
        setTotalBalance(currentBalance + carryoverBalance);
        float[] data = {currentBalance, totalBalance};
        String tableNmae = "balance";
        String fildeNmae = "`CURRENTBALANCE`=?,`TOTALBALANCE`=?";
        try {
            DatabaseAccess.updat(tableNmae, fildeNmae, data, "STATUSE ='" + opened + "'");
        } catch (IOException ex) {
            Logger.getLogger(BalanceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void closeBalance() {
        setTotalBalance(getTotalBalance());
        setCarryoverBalance(totalBalance);
        LocalDate localDate = LocalDate.now();
        String endDate = localDate.toString();
        float[] data = {carryoverBalance, totalBalance};
        String[] data1 = {"close", endDate};
        String tableNmae = "balance";
        String fildeNmae = "`CARRYOVERBALANCE`=?,`TOTALBALANCE`=?,`STATUSE`='" + opened + "'";
        String fildeNmae1 = "`STATUSE`=?,`FISCALYEARENDDATE`=?";
        try {
            DatabaseAccess.updat(tableNmae, fildeNmae1, data1, "STATUSE ='" + opened + "'");
            DatabaseAccess.updat(tableNmae, fildeNmae, data, " STATUSE = '" + newbalance + "'");
        } catch (IOException ex) {
            Logger.getLogger(BalanceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getBalanceId() {
        String balanceId = null;
        try {
            ResultSet rs = DatabaseAccess.select("balance", "STATUSE ='" + opened + "'");
            if (rs.next()) {
                balanceId = rs.getString("ID");
            }
        } catch (IOException | SQLException ex) {
            Logger.getLogger(BalanceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return balanceId;
    }

}
