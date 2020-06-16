
package Controlleres;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;


public class EducationAffairsController implements Initializable {
   LodPages loding = new LodPages();
    @FXML
    private BorderPane content;
    @FXML
    private ComboBox<?> halagahName;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    @FXML
    private void lodAttendanceAndAbsence(ActionEvent event) {
        loding.lodPage("/Views/EducationAffairs/AttendanceAndAbsence", content);
    }

    @FXML
    private void lodExams(MouseEvent event) {
         loding.lodPage("/Views/EducationAffairs/SecondLevelTest", content);
    }

    @FXML
    private void lodSaveAndReview(MouseEvent event) {
        loding.lodPage("/Views/EducationAffairs/SaveAndReview", content);
    }
    
}
