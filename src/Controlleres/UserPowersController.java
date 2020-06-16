
package Controlleres;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UserPowersController {
    
    public static boolean getPower(String userid, String powercod) {
        boolean existing = false;
        String tabelNme = "powers";
        try {
            ResultSet rs = DatabaseAccess.select(tabelNme, "USERID='" + userid + "' AND POWERCODE = '" + powercod + "' ");
            if (rs.next()) {
                existing = true;
            }
        } catch (IOException | SQLException ex) {
            Logger.getLogger(PowersPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existing;
    }
    
}
