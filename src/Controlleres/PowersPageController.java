package Controlleres;

import Messages.Controllers.ShowMessage;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class PowersPageController implements Initializable {

    @FXML
    private Label userId;
    @FXML
    private CheckBox admissionPower;
    @FXML
    private CheckBox finacialPower;
    @FXML
    private CheckBox educationPower;
    @FXML
    private CheckBox userMangPower;
    @FXML
    private CheckBox adminPower;
    String selectedUserid = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setUserId(String userid) {
        userId.setText(userid);
        admissionPower.setSelected(getPower(userid, "101"));
        finacialPower.setSelected(getPower(userid, "102"));
        educationPower.setSelected(getPower(userid, "103"));
        userMangPower.setSelected(getPower(userid, "104"));
        adminPower.setSelected(getPower(userid, "105"));
    }

    @FXML
    private void savePowr(ActionEvent event) {
        try {
            addPower(admissionPower.isSelected(), "101", userId.getText());
            addPower(finacialPower.isSelected(), "102", userId.getText());
            addPower(educationPower.isSelected(), "103", userId.getText());
            addPower(userMangPower.isSelected(), "104", userId.getText());
            addPower(adminPower.isSelected(), "105", userId.getText());
            ShowMessage showMessage = new ShowMessage();
            showMessage.success("تم اضافة الصلاحيات");
        } catch (IOException ex) {
            Logger.getLogger(PowersPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void editPower(ActionEvent event) {
        try {
            ubdatePower(admissionPower.isSelected(), "101", userId.getText());
            ubdatePower(finacialPower.isSelected(), "102", userId.getText());
            ubdatePower(educationPower.isSelected(), "103", userId.getText());
            ubdatePower(userMangPower.isSelected(), "104", userId.getText());
            ubdatePower(adminPower.isSelected(), "105", userId.getText());
            ShowMessage showMessage = new ShowMessage();
            showMessage.success("تم تحديث الصلاحيات");
        } catch (IOException ex) {
            Logger.getLogger(PowersPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addPower(boolean selected, String powercod, String userid) {
        String tabelNme = "powers";
        String fieldName = "`USERID`,`POWERCODE`";
        String valuenumbers = "?,?";
        try {
            boolean powerStatus = Validation.ifexisting(tabelNme, fieldName, "USERID='" + userid + "' AND POWERCODE = '" + powercod + "' ");
            if (selected && powerStatus) {
                String[] data = {userid, powercod};
                DatabaseAccess.insert(tabelNme, fieldName, valuenumbers, data);
            }
        } catch (IOException ex) {
            Logger.getLogger(PowersPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void ubdatePower(boolean selected, String powercod, String userid) {
        String tabelNme = "powers";
        String fieldName = "`USERID`,`POWERCODE`";
        String valuenumbers = "?,?";
        try {
            boolean powerStatus = Validation.ifexisting(tabelNme, fieldName, "USERID='" + userid + "' AND POWERCODE = '" + powercod + "' ");
            if (selected == false && powerStatus == false) {
                DatabaseAccess.deletePower(tabelNme, "USERID='" + userid + "' AND POWERCODE = '" + powercod + "' ");
            } else if (selected && powerStatus) {
                String[] data = {userid, powercod};
                DatabaseAccess.insert(tabelNme, fieldName, valuenumbers, data);
            }
        } catch (IOException ex) {
            Logger.getLogger(PowersPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean getPower(String userid, String powercod) {
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
