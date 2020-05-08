package Messages.Controllers;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ShowMessage {

    double  x, y;

    public  void success(String message) throws IOException {
        Stage stage = new Stage();
        Pane myPane = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Messages/Views/Success.fxml"));
        myPane = loader.load();
        SuccessController con = new SuccessController();
        con = (SuccessController) loader.getController();
        con.sendMeassage(message);
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(myPane);
        scene.setFill(Color.TRANSPARENT);
        myPane.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        myPane.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);

        stage.showAndWait();
    }

    public void error(String message) throws IOException {
        Stage stage = new Stage();
        Pane myPane = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Messages/Views/Error.fxml"));
        myPane = loader.load();
        ErrorController con = new ErrorController();
        con = (ErrorController) loader.getController();
        con.sendMeassage(message);
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(myPane);
        scene.setFill(Color.TRANSPARENT);
        myPane.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        myPane.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.showAndWait();
    }
}
