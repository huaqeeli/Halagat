
package Models;

import Controlleres.BalanceController;
import Controlleres.DatabaseAccess;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FurnitureModel {
   String furnitureid,detales,unetnumber,unetamount,totalamount,consumptionyear,notes;
   static float totalefurniture ,alltotalamount;

    public FurnitureModel(String furnitureid, String detales, String unetnumber, String unetamount, String totalamount, String consumptionyear, String notes) {
        this.furnitureid = furnitureid;
        this.detales = detales;
        this.unetnumber = unetnumber;
        this.unetamount = unetamount;
        this.totalamount = totalamount;
        this.consumptionyear = consumptionyear;
        this.notes = notes;
    }
    
    public String getFurnitureid() {
        return furnitureid;
    }

    public void setFurnitureid(String furnitureid) {
        this.furnitureid = furnitureid;
    }

    public String getDetales() {
        return detales;
    }

    public void setDetales(String detales) {
        this.detales = detales;
    }

    public String getUnetnumber() {
        return unetnumber;
    }

    public void setUnetnumber(String unetnumber) {
        this.unetnumber = unetnumber;
    }

    public String getUnetamount() {
        return unetamount;
    }

    public void setUnetamount(String unetamount) {
        this.unetamount = unetamount;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public String getConsumptionyear() {
        return consumptionyear;
    }

    public void setConsumptionyear(String consumptionyear) {
        this.consumptionyear = consumptionyear;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public static float getTotalefurniture() {
        try {
            ResultSet rs = DatabaseAccess.select("furniture", "UNETNUMBER", "TOTALUNETNUMBER");
            if (rs.next()) {
                totalefurniture = rs.getFloat("TOTALUNETNUMBER");
            }
            rs.close();
        } catch (IOException | SQLException ex) {
            Logger.getLogger(BalanceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return totalefurniture;
    }

    public static void setTotalefurniture(float totalefurniture) {
        FurnitureModel.totalefurniture = totalefurniture;
    }

    public static float getAlltotalamount() {
         try {
            ResultSet rs = DatabaseAccess.select("furniture", "TOTALAMOUNT", "AllTOTALAMOUNT");
            if (rs.next()) {
                alltotalamount = rs.getFloat("AllTOTALAMOUNT");
            }
            rs.close();
        } catch (IOException | SQLException ex) {
            Logger.getLogger(BalanceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return alltotalamount;
    }

    public static void setAlltotalamount(float alltotalamount) {
        FurnitureModel.alltotalamount = alltotalamount;
    }
   
   
}
