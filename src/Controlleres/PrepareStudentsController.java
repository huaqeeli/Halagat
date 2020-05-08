
package Controlleres;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class PrepareStudentsController implements Initializable {
    @FXML
    private VBox content;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage) content.getScene().getWindow();
        stage.close();
    }
    
}
