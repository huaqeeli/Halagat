package Controlleres;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LodPages {

    double x = 0, y = 0;

    public void lodPage(String page, BorderPane mainContent) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(page + ".fxml"));
        } catch (IOException ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainContent.setCenter(root);
    }
    
    public void lodHomePage(BorderPane mainContent, String userid) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/HomePage.fxml"));
            root = loader.load();
            HomeController controller = new HomeController();
            controller = (HomeController)loader.getController();
            controller.setUserId(userid);
            
        } catch (IOException ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainContent.setCenter(root);
    }
    public void lodBudgetPage(BorderPane mainContent, String userid) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Finacial/Budget.fxml"));
            root = loader.load();
            BudgetController controller = new BudgetController();
            controller = (BudgetController)loader.getController();
            controller.setUserId(userid);
            
        } catch (IOException ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainContent.setCenter(root);
    }
    public void lodFinancialPage(BorderPane mainContent, String userid) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Financial.fxml"));
            root = loader.load();
            FinancialController controller = new FinancialController();
            controller = (FinancialController)loader.getController();
            controller.setUserId(userid);
            
        } catch (IOException ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainContent.setCenter(root);
    }

    public void lodNewFXML(String page) throws IOException {
        Stage stage = new Stage();
        Pane myPane = null;
        myPane = FXMLLoader.load(getClass().getResource(page + ".fxml"));
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
