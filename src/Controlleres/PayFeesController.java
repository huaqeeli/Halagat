
package Controlleres;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class PayFeesController implements Initializable {
    @FXML
    private TextField IDNumber;
    @FXML
    private TextField amount;
    @FXML
    private AnchorPane stage;
    @FXML
    private ImageView closButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void save(ActionEvent event) {
    }

    @FXML
    private void colos(MouseEvent event) {
        Stage stage = (Stage) closButton.getScene().getWindow();
        stage.close();
    }
    
}
