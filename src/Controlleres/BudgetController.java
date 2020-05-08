package Controlleres;

import Models.BalanceModel;
import Models.StudentModel;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class BudgetController implements Initializable {

    @FXML
    private TextField fiscalyearname;
    @FXML
    private TableView<BalanceModel> balance;
    @FXML
    private TableColumn<?, ?> fiscalyearname_col;
    @FXML
    private TableColumn<?, ?> year_col;
    @FXML
    private TableColumn<?, ?> currentbalance_col;
    @FXML
    private TableColumn<?, ?> carryoverbalance_col;
    @FXML
    private TableColumn<?, ?> totalbalane_col;
    ObservableList<BalanceModel> balancelist = FXCollections.observableArrayList();
    String year = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshStudentTable();
        getRowData(balance);
    }
    /*`FISCALYEARNAME`,`FISCALYEAR`,`CURRENTBALANCE`,`CARRYOVERBALANCE`,`TOTALBALANCE`*/

    @FXML
    private void creatAccount(ActionEvent event) {
        String tableName = "balance";
        String fiscalYear = Integer.toString(HijriCalendar.getSimpleYear());
        String fieldName = "`FISCALYEARNAME`,`FISCALYEAR`";
        String[] data = {fiscalyearname.getText(), fiscalYear};
        String valuenumbers = "?,?";
        boolean existing = BalanceController.isBalanceExisting("تم انشاء حساب للسنة المالية الحالية");
        boolean fiscalyearnameStatus = Validation.textFieldNotEmpty(fiscalyearname, "ادخل مسمى السنة المالية");
        if (existing && fiscalyearnameStatus) {
            try {
                DatabaseAccess.insert(tableName, fieldName, valuenumbers, data);
                refreshStudentTable();
            } catch (IOException ex) {
                Logger.getLogger(BudgetController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void closeYearBudgetAccount(ActionEvent event) {
    }

    @FXML
    private void editData(ActionEvent event) {
        String tableName = "balance";
        String fieldName = "`FISCALYEARNAME`=?";
        String[] data = {fiscalyearname.getText()};
        boolean fiscalyearnameStatus = Validation.textFieldNotEmpty(fiscalyearname, "ادخل مسمى السنة المالية");
       
        if (fiscalyearnameStatus) {
            try {
                DatabaseAccess.updat(tableName, fieldName,data,"FISCALYEAR = '"+year+"'");
                refreshStudentTable();
            } catch (IOException ex) {
                Logger.getLogger(BudgetController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void balanceTableView() {
        try {
            ResultSet rs = DatabaseAccess.select("balance");

            while (rs.next()) {
                balancelist.add(new BalanceModel(
                        rs.getString("FISCALYEARNAME"),
                        rs.getString("FISCALYEAR"),
                        rs.getInt("CURRENTBALANCE"),
                        rs.getInt("CARRYOVERBALANCE"),
                        rs.getInt("TOTALBALANCE")
                ));
            }

            rs.close();
        } catch (SQLException | IOException ex) {
            Validation.showAlert("خطاء", (SQLException) ex);
        }
        fiscalyearname_col.setCellValueFactory(new PropertyValueFactory<>("fiscalyearname"));
        year_col.setCellValueFactory(new PropertyValueFactory<>("year"));
        currentbalance_col.setCellValueFactory(new PropertyValueFactory<>("currentbalance"));
        carryoverbalance_col.setCellValueFactory(new PropertyValueFactory<>("carryoverbalance"));
        totalbalane_col.setCellValueFactory(new PropertyValueFactory<>("totalbalane"));

        balance.setItems(balancelist);
    }

    private void refreshStudentTable() {
        balancelist.clear();
        balanceTableView();
    }

    private void getRowData(TableView table) {
        table.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                ObservableList<BalanceModel> list = FXCollections.observableArrayList();
                list = table.getSelectionModel().getSelectedItems();
                if (list.isEmpty()) {
                    Validation.showAlert("", "لاتوجد بيانات");
                } else {
                    year = list.get(0).getYear();
                    fiscalyearname.setText(list.get(0).getFiscalyearname());
                }
            }
        });
    }

    private void clearData() {
//        amount.setText("");
//        from.setText("");
//        revenuesdata.setValue(LocalDate.now());
//        clause.setValue("");
//        accounttype.setValue("");
    }

}
