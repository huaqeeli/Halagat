package Controlleres;

import Messages.Controllers.ShowMessage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class AdmissionController implements Initializable {

    LodPages loding = new LodPages();
    ShowMessage massage = new ShowMessage();
    @FXML
    private BorderPane content;
    @FXML
    private Label titel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void NewHalaghLodPage(MouseEvent event) {
        loding.lodPage("/Views/Admission/AddNewHalagh", content);
        titel.setText("الصفحة الرئيسية /القبول والتسجيل /إضافة حلقة ");
    }

    @FXML
    private void StudentRegistrationLodPage(MouseEvent event) {
        loding.lodPage("/Views/Admission/StudentRegistration", content);
        titel.setText("الصفحة الرئيسية /القبول والتسجيل /تسجيل الطلاب ");
    }

    @FXML
    private void StudentIdentityLodPage(MouseEvent event) {
        loding.lodPage("/Views/Admission/StudentIdentity", content);
        titel.setText("الصفحة الرئيسية /القبول والتسجيل /هوية طالب ");
    }

    @FXML
    private void PayFeesLodPage(MouseEvent event) throws IOException {
        loding.lodNewFXML("/Views/Admission/PayFees");
    }

}
