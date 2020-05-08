
package Messages.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;


public class ConfirmationController implements Initializable {
    @FXML
    private VBox content;
    @FXML
    private Label messageText;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    @FXML
    private void close(MouseEvent event) {
    }
    
    @FXML
    private void confirmAction(MouseEvent event) {
    }
    
}
