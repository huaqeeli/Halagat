package Controlleres;

import Messages.Controllers.ShowMessage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class HomeController implements Initializable {

    LodPages loding = new LodPages();
    @FXML
    private BorderPane content;
    String userid = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void admissionPageOpen(MouseEvent event) {
        try {
            if (UserPowersController.getPower(userid, "101")||UserPowersController.getPower(userid, "105")) {
                loding.lodPage("/Views/AdmissionPage", content);
            } else {
                ShowMessage showMessage = new ShowMessage();
                showMessage.error("ليس لديك صلاحية الدخول الى صفحة القبول والتسجيل");
            }
        } catch (IOException ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void usersPageOpen(MouseEvent event) {
        try {
            if (UserPowersController.getPower(userid, "104")||UserPowersController.getPower(userid, "105")) {
                loding.lodPage("/Views/UsersPage", content);
            } else {
                ShowMessage showMessage = new ShowMessage();
                showMessage.error("ليس لديك صلاحية الدخول الى صفحة ادارة المستخدمين ");
            }
        } catch (IOException ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void teachersLoginPageOpen(MouseEvent event) {
        try {
            if (UserPowersController.getPower(userid, "103")||UserPowersController.getPower(userid, "105")) {
                loding.lodPage("/Views/EducationAffairs", content);
            } else {
                ShowMessage showMessage = new ShowMessage();
                showMessage.error("ليس لديك صلاحية الدخول الى صفحة الشؤون التعلمية");
            }
        } catch (IOException ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void FinancialPageOpen(MouseEvent event) {
        try {
            if (UserPowersController.getPower(userid, "102")||UserPowersController.getPower(userid, "105")) {
                loding.lodFinancialPage( content,userid);
            } else {
                ShowMessage showMessage = new ShowMessage();
                showMessage.error("ليس لديك صلاحية الدخول الى صفحة الشؤون المالية والادارية");
            }
        } catch (IOException ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setUserId(String userid) {
        this.userid = userid;
    }
}
