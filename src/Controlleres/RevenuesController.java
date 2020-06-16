package Controlleres;

import Messages.Controllers.ShowMessage;
import Models.RevenuesModel;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class RevenuesController implements Initializable {

    @FXML
    private DatePicker revenuesdata;
    @FXML
    private ComboBox<String> clause;
    @FXML
    private TextField amount;
    @FXML
    private TextField from;
    @FXML
    private TextField amountText;
    @FXML
    private TextField cashOrCheque;
    @FXML
    private TextField bankName;
    @FXML
    private Label carryoverbalance;
    @FXML
    private Label totalbalance;
    @FXML
    private Label totalRevenues;
    @FXML
    private TableView<RevenuesModel> revenuesTable;
    @FXML
    private TableColumn<?, ?> revenuesdata_col;
    @FXML
    private TableColumn<?, ?> clause_col;
    @FXML
    private TableColumn<?, ?> amount_col;
    @FXML
    private TableColumn<?, ?> from_col;
    @FXML
    private TableColumn<?, ?> revenuesid_col;

    ObservableList<RevenuesModel> revenueslist = FXCollections.observableArrayList();
    String[] clauseitem = {"فاعل خير", "الداعم الرئيسي", "رسوم الطلاب", "ايرادات مسجد", "بنك البلاد", "تسديد عهدة"};
    String[] accounttypeitem = {"حساب الحلقات", "حساب المسجد"};
    Image img = new Image(getClass().getResourceAsStream("/Images/print.png"));
    float tableAmount = 0;
    String revenuedId = null;
    String balanceId = null;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshRevenuesTable();
        revenuesdata.setValue(LocalDate.now());
        ComboBoxFill.fillComboBox(clause, clauseitem);
        getRowData(revenuesTable);
    }
    /*`REVENUESID`,`REVENUESDATE`,`CLAUSE`,`INVOICENUMBER`,`AMOUNT`,`FROM`,`ACCOUNTTYPE*/

    @FXML
    private void saveDate(MouseEvent event) {
        String tableName = "revenues";
        balanceId = BalanceController.getBalanceId();
        String bankNameText = null;
        if (bankName.getText() == null) {
            bankNameText = "ـــــ";
        } else {
            bankNameText = bankName.getText();
        }

        String fieldName = "`REVENUESDATE`,`CLAUSE`,`AMOUNT`,`FROM`,`BALANCEID`,`AMOUNTTEXT`,`CASHORCHEQUENO`,`BANKNAME`";
        String[] data = {revenuesdata.getValue().toString(), clause.getValue(), amount.getText(), from.getText(),  balanceId, amountText.getText(), cashOrCheque.getText(), bankNameText};
        String valuenumbers = "?,?,?,?,?,?,?,?";

        boolean revenuesdataStatus = Validation.datePickerNotEmpty(revenuesdata, "ادخل التاريخ");
        boolean clauseStatus = Validation.comboBoxNotEmpty(clause, "ادخل البند");
        boolean amountStatus = Validation.textFieldNotEmpty(amount, "ادخل المبلغ");
        boolean amountNumberOnle = Validation.textFieldNotEmpty(amount, "ادخل ارقام فقط");
        boolean fromStatus = Validation.textFieldNotEmpty(from, "ادخل الايراد من ");
        boolean amountTextStatus = Validation.textFieldNotEmpty(amountText, "ادخل الايراد من ");
        boolean cashOrChequeStatus = Validation.textFieldNotEmpty(cashOrCheque, "ادخل نقدا ار رقم الشيك ");

        if (revenuesdataStatus && clauseStatus && amountStatus && amountNumberOnle && fromStatus  && amountTextStatus && cashOrChequeStatus) {
            try {
                DatabaseAccess.insert(tableName, fieldName, valuenumbers, data);
                BalanceController.incrementBalance(Float.parseFloat(amount.getText()));
                refreshRevenuesTable();
                clearData();
            } catch (IOException ex) {
                Logger.getLogger(RevenuesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void editData(MouseEvent event) {
        String tableName = "revenues";
        String bankNameText = null;
        if (bankName.getText() == null) {
            bankNameText = "ـــــ";
        } else {
            bankNameText = bankName.getText();
        }
        String fieldName = "`REVENUESDATE`=?,`CLAUSE`=?,`AMOUNT`=?,`FROM`=?,`AMOUNTTEXT`=?,`CASHORCHEQUENO`=?,`BANKNAME`=?";
        String[] data = {revenuesdata.getValue().toString(), clause.getValue(), amount.getText(), from.getText(), amountText.getText(), cashOrCheque.getText(), bankNameText};

        boolean revenuesdataStatus = Validation.datePickerNotEmpty(revenuesdata, "ادخل التاريخ");
        boolean clauseStatus = Validation.comboBoxNotEmpty(clause, "ادخل البند");
        boolean amountStatus = Validation.textFieldNotEmpty(amount, "ادخل المبلغ");
        boolean amountNumberOnle = Validation.textFieldNotEmpty(amount, "ادخل ارقام فقط");
        boolean fromStatus = Validation.textFieldNotEmpty(from, "ادخل الايراد من ");
        boolean amountTextStatus = Validation.textFieldNotEmpty(amountText, "ادخل الايراد من ");
        boolean cashOrChequeStatus = Validation.textFieldNotEmpty(cashOrCheque, "ادخل نقدا ار رقم الشيك ");

        if (revenuesdataStatus && clauseStatus && amountStatus && amountNumberOnle && fromStatus  && amountTextStatus && cashOrChequeStatus) {
            try {
                DatabaseAccess.updat(tableName, fieldName, data, "REVENUESID='" + revenuedId + "'");
                BalanceController.decrementBalance(tableAmount);
                BalanceController.incrementBalance(Float.parseFloat(amount.getText()));
                refreshRevenuesTable();
                clearData();
            } catch (IOException ex) {
                Logger.getLogger(RevenuesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void deleteData(MouseEvent event) {
        try {
            String tableName = "revenues";
            DatabaseAccess.delete(tableName, "REVENUESID='" + revenuedId + "'");
            BalanceController.decrementBalance(tableAmount);
            refreshRevenuesTable();
            clearData();
        } catch (IOException ex) {
            Logger.getLogger(RevenuesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void revenuesTableView() {
        try {
            balanceId = BalanceController.getBalanceId();
            ResultSet rs = DatabaseAccess.select("revenues", "BALANCEID = '" + balanceId + "'");
            while (rs.next()) {
                revenueslist.add(new RevenuesModel(
                        rs.getString("REVENUESID"),
                        rs.getString("REVENUESDATE"),
                        rs.getString("CLAUSE"),
                        rs.getString("AMOUNT"),
                        rs.getString("FROM")
                ));
            }
            rs.close();
        } catch (SQLException | IOException ex) {
            Validation.showAlert("خطاء", (SQLException) ex);
        }
        revenuesid_col.setCellValueFactory(new PropertyValueFactory<>("revenuesid"));
        revenuesdata_col.setCellValueFactory(new PropertyValueFactory<>("revenuesdata"));
        clause_col.setCellValueFactory(new PropertyValueFactory<>("clause"));
        amount_col.setCellValueFactory(new PropertyValueFactory<>("amount"));
        from_col.setCellValueFactory(new PropertyValueFactory<>("from"));
        
        totalbalance.setText(Float.toString(BalanceController.getTotalBalance()));
        carryoverbalance.setText(Float.toString(BalanceController.getCarryoverBalance()));
        totalRevenues.setText(Float.toString(BalanceController.getTotalRevenues()));
        
        revenuesTable.setItems(revenueslist);
    }

    private void refreshRevenuesTable() {
        revenueslist.clear();
        revenuesTableView();
    }

    private void clearData() {
        amount.setText("");
        from.setText("");
        revenuesdata.setValue(LocalDate.now());
        clause.setValue("");
        amountText.setText("");
        cashOrCheque.setText("");
        bankName.setText("");
    }

    private void getRowData(TableView table ) {
        table.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                ObservableList<RevenuesModel> list = FXCollections.observableArrayList();
                list = table.getSelectionModel().getSelectedItems();
                if (list.isEmpty()) {
                    Validation.showAlert("", "لاتوجد بيانات");
                } else {
                    try {
                        ResultSet rs = DatabaseAccess.select("revenues", "REVENUESID='" + list.get(0).getRevenuesid() + "'");
                        revenuesdata.setValue(LocalDate.parse(list.get(0).getRevenuesdata()));
                        clause.setValue(list.get(0).getClause());
                        amount.setText(list.get(0).getAmount());
                        from.setText(list.get(0).getFrom());
                        tableAmount = Float.parseFloat(list.get(0).getAmount());
                        revenuedId = list.get(0).getRevenuesid();
                        if (rs.next()) {
                            amountText.setText(rs.getString("AMOUNTTEXT"));
                            cashOrCheque.setText(rs.getString("CASHORCHEQUENO"));
                            bankName.setText(rs.getString("BANKNAME"));
                        }
                    } catch (IOException | SQLException ex) {
                        Logger.getLogger(RevenuesController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    @FXML
    private void printCatchReceipt(ActionEvent event) {
        try {
            String reportSrcFile = "C:\\Users\\ابو ريان\\Documents\\Halagat\\src\\Reports\\CatchReceiptReport.jrxml";
            Connection con = DatabaseConnector.dbConnector();

            JasperDesign jasperReport = JRXmlLoader.load(reportSrcFile);
            Map parameters = new HashMap();
            if (revenuedId == null) {
                ShowMessage showMessage = new ShowMessage();
                showMessage.error("اختر السجل من الجدول");
            } else {
                parameters.put("revenuedId", revenuedId);
                JasperReport jrr = JasperCompileManager.compileReport(jasperReport);
                JasperPrint print = JasperFillManager.fillReport(jrr, parameters, con);
                JasperViewer.viewReport(print, false);
            }

        } catch (JRException | IOException ex) {
            Logger.getLogger(StudentIdentityController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
