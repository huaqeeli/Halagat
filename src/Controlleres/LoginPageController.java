/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlleres;

import Messages.Controllers.ShowMessage;
import halagat.Halagat;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginPageController implements Initializable {

    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private AnchorPane content;
    LodPages loding = new LodPages();
    MainPageController controller = new MainPageController();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void login(ActionEvent event) {
        boolean userNameStatus = Validation.loginTextFieldNotEmpty(userName, "ادخل اسم المستخدم");
        boolean passwordStatus = Validation.loginTextFieldNotEmpty(password, "ادخل كلمة المرور");
        if (userNameStatus && passwordStatus) {
            try {
                ResultSet rs = DatabaseAccess.select("users", "USERNAME ='" + userName.getText() + "' AND PASSWORD = '" + password.getText() + "'");
                if (rs.next()) {
                    lodMainPage(rs.getString("NAME"));
                    close();
                } else {
                    ShowMessage showMessage = new ShowMessage();
                    showMessage.error("اسم المستخدم او كلمة المرور خاطئة الرجاء التاكد من الادخال الصحيح");
                }
            } catch (IOException | SQLException ex) {
                Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void lodMainPage(String userName) throws IOException {
        Stage stage = new Stage();
        Pane myPane = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainPage.fxml"));
        myPane = loader.load();

        controller = (MainPageController) loader.getController();
        controller.setUserName(userName);
        Scene scene = new Scene(myPane);

        Duration delay = Duration.seconds(600);
        PauseTransition transition = new PauseTransition(delay);
        transition.setOnFinished(evt -> {
            try {
                controller.close();
                lodLogingPage();
                transition.stop();
            } catch (IOException ex) {
                Logger.getLogger(Halagat.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        scene.addEventFilter(InputEvent.ANY, evt -> transition.playFromStart());
//        stage.setFullScreen(true);
        stage.setScene(scene);
//        stage.getIcons().add(new Image("/source/osslogo.png"));
//        stage.setTitle("الصفحة الرئيسية");
        stage.show();
    }

    public void lodLogingPage() throws IOException {
        Stage stage = new Stage();
        Pane myPane = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/LoginPage.fxml"));
        myPane = loader.load();
        Scene scene = new Scene(myPane);
//        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
    }

    private void close() {
        Stage stage = (Stage) content.getScene().getWindow();
        stage.close();
    }
}
