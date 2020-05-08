
package Controlleres;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public class HomeController implements Initializable {

    LodPages loding = new LodPages();
    @FXML
    private BorderPane content;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void admissionPageOpen(ActionEvent event) {
        loding.lodPage("/Views/AdmissionPage", content);
    }

    @FXML
    private void usersPageOpen(ActionEvent event) {
        loding.lodPage("/Views/UsersPage", content);
    }

    @FXML
    private void teachersLoginPageOpen(ActionEvent event) {
        loding.lodPage("/Views/EducationAffairs", content);
    }

    @FXML
    private void FinancialPageOpen(ActionEvent event) {
        loding.lodPage("/Views/Financial", content);
    }
}
