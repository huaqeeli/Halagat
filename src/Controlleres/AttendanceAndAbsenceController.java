package Controlleres;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class AttendanceAndAbsenceController implements Initializable {

    LodPages loding = new LodPages();
    @FXML
    private Label label;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void lodPrepareStudentsPage(ActionEvent event) throws IOException {
         loding.lodNewFXML("/Views/TeachersLogin/PrepareStudents");
    }

}
