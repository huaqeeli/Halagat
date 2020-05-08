/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package halagat;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author ابو ريان
 */
public class FXMLDocumentController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        VBox vbox = new VBox();
        HBox hbox = new HBox();
        Stage stage = null;
        stage.setTitle("VBox");
        Label label = new Label("this is VBox example");
        vbox.getChildren().add(label);

        for (int i = 0; i < 10; i++) {
            vbox.getChildren().add(new Button("Button " + (int) (i + 1)));
        }
        Scene scene = new Scene(vbox, 300, 300);
        stage.setScene(scene);
        stage.show();
    }

}
