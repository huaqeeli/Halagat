
package Controlleres;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;


public class SaveAndReviewController implements Initializable {
    @FXML
    private TableColumn<?, ?> name_col1;
    @FXML
    private TableColumn<?, ?> name_col;
    @FXML
    private ComboBox<?> halagahName1;
    @FXML
    private TextField issuePlace;
    @FXML
    private TextField mobileNumber;
    @FXML
    private TextField level;
    @FXML
    private ComboBox<?> halagahName;
    @FXML
    private ComboBox<?> halagahName11;
    @FXML
    private TableColumn<?, ?> name_col11;
    @FXML
    private TableColumn<?, ?> age_col1;
    @FXML
    private TableColumn<?, ?> birthDate_col1;
    @FXML
    private TableColumn<?, ?> nationality_col1;
    @FXML
    private TableColumn<?, ?> nationalID_col1;
    @FXML
    private TableColumn<?, ?> nationalID_col11;

 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    @FXML
    private void saveData(ActionEvent event) {
    }

    @FXML
    private void editData(ActionEvent event) {
    }

    @FXML
    private void deleteData(ActionEvent event) {
    }
    
}
