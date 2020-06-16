package Controlleres;

import Messages.Controllers.ShowMessage;
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
    String userid = null;
    boolean logOut = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            HBox hbox = FXMLLoader.load(getClass().getResource("/Views/ToolBar.fxml"));
            drawer.setSidePane(hbox);
            for (Node node : hbox.getChildren()) {
                if (node.getAccessibleText() != null) {
                    node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        switch (node.getAccessibleText()) {
                            case "homPage":
                                loding.lodHomePage(mainContent, userid);
                                break;
                            case "admissionPage":
                                try {
                                    if (UserPowersController.getPower(userid, "101") || UserPowersController.getPower(userid, "105")) {
                                        loding.lodPage("/Views/AdmissionPage", mainContent);
                                    } else {
                                        ShowMessage showMessage = new ShowMessage();
                                        showMessage.error("ليس لديك صلاحية الدخول الى صفحة القبول والتسجيل");
                                    }
                                } catch (IOException ex) {
                                    Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                break;
                            case "financialPage":
                                try {
                                    if (UserPowersController.getPower(userid, "102") || UserPowersController.getPower(userid, "105")) {
                                        loding.lodFinancialPage(mainContent, userid);
                                    } else {
                                        ShowMessage showMessage = new ShowMessage();
                                        showMessage.error("ليس لديك صلاحية الدخول الى صفحة الشؤون المالية والادارية");
                                    }
                                } catch (IOException ex) {
                                    Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                break;
                            case "teachersLoginPage":
                                try {
                                    if (UserPowersController.getPower(userid, "103") || UserPowersController.getPower(userid, "105")) {
                                        loding.lodPage("/Views/EducationAffairs", mainContent);
                                    } else {
                                        ShowMessage showMessage = new ShowMessage();
                                        showMessage.error("ليس لديك صلاحية الدخول الى صفحة الشؤون التعلمية");
                                    }
                                } catch (IOException ex) {
                                    Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                break;
                            case "usersPage":
                                try {
                                    if (UserPowersController.getPower(userid, "104") || UserPowersController.getPower(userid, "105")) {
                                        loding.lodPage("/Views/UsersPage", mainContent);
                                    } else {
                                        ShowMessage showMessage = new ShowMessage();
                                        showMessage.error("ليس لديك صلاحية الدخول الى صفحة ادارة المستخدمين ");
                                    }
                                } catch (IOException ex) {
                                    Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
                                }
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
        loding.lodHomePage(mainContent, userid);
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
        try {
            close();
            LoginPageController login = new LoginPageController();
            login.lodLogingPage();
            logOut = true;
        } catch (IOException ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setUserName(String username, String userid) {
        this.userNameLabel.setText(username);
        this.userid = userid;
        loding.lodHomePage(mainContent, userid);
    }
}
