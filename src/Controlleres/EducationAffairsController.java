/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlleres;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author ابو ريان
 */
public class EducationAffairsController implements Initializable {
   LodPages loding = new LodPages();
    @FXML
    private BorderPane content;
    @FXML
    private ComboBox<?> halagahName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void lodAttendanceAndAbsence(ActionEvent event) {
        loding.lodPage("/Views/TeachersLogin/AttendanceAndAbsence", content);
    }

    @FXML
    private void lodExams(MouseEvent event) {
         loding.lodPage("/Views/TeachersLogin/SecondLevelTest", content);
    }

    @FXML
    private void lodSaveAndReview(MouseEvent event) {
        loding.lodPage("/Views/TeachersLogin/SaveAndReview", content);
    }
    
}
