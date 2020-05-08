/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlleres;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author ابو ريان
 */
public class FinancialController implements Initializable {

    @FXML
    private BorderPane content;
    LodPages loding = new LodPages();
    @FXML
    private Label titel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void lodRevenues(MouseEvent event) {
        loding.lodPage("/Views/Finacial/Revenues", content);
        titel.setText("الصفحة الرئيسية /الشؤون الإدارية والمالية /الايرادات ");
    }

    @FXML
    private void lodExpenses(MouseEvent event) {
         loding.lodPage("/Views/Finacial/Expenses", content);
         titel.setText("الصفحة الرئيسية /الشؤون الإدارية والمالية /المصروفات ");
    }

    @FXML
    private void lodLoan(MouseEvent event) {
         loding.lodPage("/Views/Finacial/Loan", content);
         titel.setText("الصفحة الرئيسية /الشؤون الإدارية والمالية /العهد ");
    }

    @FXML
    private void lodFurniture(MouseEvent event) {
         loding.lodPage("/Views/Finacial/Furniture", content);
         titel.setText("الصفحة الرئيسية /الشؤون الإدارية والمالية /الاصول الثابتة ");
    }

    @FXML
    private void lodBankAccount(MouseEvent event) {
         loding.lodPage("/Views/Finacial/BankAccount", content);
         titel.setText("الصفحة الرئيسية /الشؤون الإدارية والمالية /حساب البنك ");
    }

    @FXML
    private void lodReports(MouseEvent event) {
         loding.lodPage("/Views/Finacial/Reports", content);
         titel.setText("الصفحة الرئيسية /الشؤون الإدارية والمالية /التقارير ");
    }

    @FXML
    private void lodBudget(MouseEvent event) {
         loding.lodPage("/Views/Finacial/Budget", content);
         titel.setText("الصفحة الرئيسية /الشؤون الإدارية والمالية /الميزانية ");
    }

}
