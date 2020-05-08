package Controlleres;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainPageController implements Initializable {

    LodPages loding = new LodPages();
    @FXML
    private BorderPane mainContent;
    @FXML
    private JFXHamburger myHamburger;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private Label userNameLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loding.lodPage("/Views/HomePage", mainContent);
            HBox hbox = FXMLLoader.load(getClass().getResource("/Views/ToolBar.fxml"));
            drawer.setSidePane(hbox);

            for (Node node : hbox.getChildren()) {
                if (node.getAccessibleText() != null) {
                    node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        switch (node.getAccessibleText()) {
                            case "homPage":
                                loding.lodPage("/Views/HomePage", mainContent);
                                break;
                            case "admissionPage":
                                loding.lodPage("/Views/AdmissionPage", mainContent);
                                break;
                            case "financialPage":
                                loding.lodPage("/Views/Financial", mainContent);
                                break;
                            case "teachersLoginPage":
                                loding.lodPage("/Views/EducationAffairs", mainContent);
                                break;
                            case "usersPage":
                                loding.lodPage("/Views/UsersPage", mainContent);
                                break;
                        }
                    });
                }
            }

            HamburgerSlideCloseTransition transition = new HamburgerSlideCloseTransition(myHamburger);
            transition.setRate(-1);
            myHamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
                transition.setRate(transition.getRate() * -1);
                transition.play();
                if (drawer.isClosed()) {
                    drawer.open();
                } else {
                    drawer.close();
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void close() {
        Stage stage = (Stage) mainContent.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void homePageOpen(MouseEvent event) {
        loding.lodPage("/Views/HomePage", mainContent);
    }

    @FXML
    private void admissionPageOpen(ActionEvent event) {
        loding.lodPage("/Views/AdmissionPage", mainContent);
    }

    @FXML
    private void usersPageOpen(ActionEvent event) {
        loding.lodPage("/Views/UsersPage", mainContent);
    }

    @FXML
    private void teachersLoginPageOpen(ActionEvent event) {
        loding.lodPage("/Views/TeachersLogin", mainContent);
    }

    @FXML
    private void FinancialPageOpen(ActionEvent event) {
        loding.lodPage("/Views/Financial", mainContent);
    }

    private void fullScreen(MouseEvent event) {
        Stage stage = (Stage) mainContent.getScene().getWindow();
        stage.setFullScreen(true);
    }

    private void minimiz(MouseEvent event) {
        Stage stage = (Stage) mainContent.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void logout(ActionEvent event) {
    }

    public void setUserName(String username) {
        this.userNameLabel.setText(username);
    }
}
