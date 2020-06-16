package Controlleres;

import Models.FurnitureModel;
import Models.LoansModel;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

public class FurnitureController implements Initializable {

    @FXML
    private TextField detales;
    @FXML
    private TextField unetnumber;
    @FXML
    private TextField unetamount;
    @FXML
    private TextField totalamount;
    @FXML
    private TextField consumptionyear;
    @FXML
    private TextField notes;
    @FXML
    private Label totalefurniture;
    @FXML
    private Label alltotalamount;
    @FXML
    private TableView<FurnitureModel> furnituretable;
    @FXML
    private TableColumn<?, ?> furnitureid_col;
    @FXML
    private TableColumn<?, ?> detales_col;
    @FXML
    private TableColumn<?, ?> unetnumber_col;
    @FXML
    private TableColumn<?, ?> unetamount_col;
    @FXML
    private TableColumn<?, ?> totalamount_col;
    @FXML
    private TableColumn<?, ?> consumptionyear_col;
    @FXML
    private TableColumn<?, ?> notes_col;

    String balanceId = null;
    String furnitureId = null;
    ObservableList<FurnitureModel> furniturelist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshfurnitureTable();
        getRowData(furnituretable);
    }

    @FXML
    private void saveData(ActionEvent event) {
        String tableName = "furniture";
        balanceId = BalanceController.getBalanceId();
        /*`FURNITUREID`,`DETALES`,`UNETNUMBER`,`UNETAMOUNT`,`TOTALAMOUNT`,`CONSUMPTIONYEAR`,`NOTES`*/
        String fieldName = "`DETALES`,`UNETNUMBER`,`UNETAMOUNT`,`TOTALAMOUNT`,`CONSUMPTIONYEAR`,`NOTES`,`BALANCEID`";
        String[] data = {detales.getText(), unetnumber.getText(), unetamount.getText(), totalamount.getText(), consumptionyear.getText(), notes.getText(), balanceId};
        String valuenumbers = "?,?,?,?,?,?,?";

        boolean detalesStatus = Validation.textFieldNotEmpty(detales, "ادخل البيان");
        boolean consumptionyearStatus = Validation.textFieldNotEmpty(consumptionyear, "ادخل سنوات الاستهلاك");
        boolean unetnumberNumberOnle = Validation.textFieldNumberOnly(unetnumber, "لا يقبل الا ارقام فقط");
        boolean unetamountNumberOnle = Validation.textFieldNumberOnly(unetamount, "لا يقبل الا ارقام فقط");

        if (detalesStatus && consumptionyearStatus && unetnumberNumberOnle && unetnumberNumberOnle && unetamountNumberOnle) {
            try {
                DatabaseAccess.insert(tableName, fieldName, valuenumbers, data);
                refreshfurnitureTable();
                clearData();
            } catch (IOException ex) {
                Logger.getLogger(RevenuesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void editData(ActionEvent event) {
        String tableName = "furniture";
        balanceId = BalanceController.getBalanceId();
        /*`FURNITUREID`,`DETALES`,`UNETNUMBER`,`UNETAMOUNT`,`TOTALAMOUNT`,`CONSUMPTIONYEAR`,`NOTES`*/
        String fieldName = "`DETALES`=?,`UNETNUMBER`=?,`UNETAMOUNT`=?,`TOTALAMOUNT`=?,`CONSUMPTIONYEAR`=?,`NOTES`=?,`BALANCEID`=?";
        String[] data = {detales.getText(), unetnumber.getText(), unetamount.getText(), totalamount.getText(), consumptionyear.getText(), notes.getText(), balanceId};

        boolean detalesStatus = Validation.textFieldNotEmpty(detales, "ادخل البيان");
        boolean consumptionyearStatus = Validation.textFieldNotEmpty(consumptionyear, "ادخل سنوات الاستهلاك");
        boolean unetnumberNumberOnle = Validation.textFieldNumberOnly(unetnumber, "لا يقبل الا ارقام فقط");
        boolean unetamountNumberOnle = Validation.textFieldNumberOnly(unetamount, "لا يقبل الا ارقام فقط");

        if (detalesStatus && consumptionyearStatus && unetnumberNumberOnle && unetnumberNumberOnle && unetamountNumberOnle) {
            try {
                DatabaseAccess.updat(tableName, fieldName, data, "FURNITUREID = '" + furnitureId + "'");
                refreshfurnitureTable();
                clearData();
            } catch (IOException ex) {
                Logger.getLogger(RevenuesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void deleteData(ActionEvent event) {
        try {
            DatabaseAccess.delete("furniture", "FURNITUREID = '" + furnitureId + "'");
            refreshfurnitureTable();
            clearData();
        } catch (IOException ex) {
            Logger.getLogger(FurnitureController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void colectTotaleAmount(KeyEvent event) {
        boolean unetnumbertStatus = Validation.textFieldNotEmpty(unetnumber, "ادخل عدد الوحدات");
        boolean unetamountStatus = Validation.textFieldNotEmpty(unetamount, "ادخل سعر الوحدة");
        if (unetnumbertStatus && unetamountStatus) {
            float total = Float.parseFloat(unetnumber.getText()) * Float.parseFloat(unetamount.getText());
            totalamount.setText(Float.toString(total));
        }

    }

    private void furnituretableView() {
        try {
            //balanceId = BalanceController.getBalanceId();
            ResultSet rs = DatabaseAccess.select("furniture");
            while (rs.next()) {
                furniturelist.add(new FurnitureModel(
                        rs.getString("FURNITUREID"),
                        rs.getString("DETALES"),
                        rs.getString("UNETNUMBER"),
                        rs.getString("UNETAMOUNT"),
                        rs.getString("TOTALAMOUNT"),
                        rs.getString("CONSUMPTIONYEAR"),
                        rs.getString("NOTES")
                ));
            }
            rs.close();
        } catch (SQLException | IOException ex) {
            Validation.showAlert("خطاء", (SQLException) ex);
        }
        furnitureid_col.setCellValueFactory(new PropertyValueFactory<>("furnitureid"));
        detales_col.setCellValueFactory(new PropertyValueFactory<>("detales"));
        unetnumber_col.setCellValueFactory(new PropertyValueFactory<>("unetnumber"));
        unetamount_col.setCellValueFactory(new PropertyValueFactory<>("unetamount"));
        totalamount_col.setCellValueFactory(new PropertyValueFactory<>("totalamount"));
        consumptionyear_col.setCellValueFactory(new PropertyValueFactory<>("consumptionyear"));
        notes_col.setCellValueFactory(new PropertyValueFactory<>("notes"));

        totalefurniture.setText(Float.toString(FurnitureModel.getTotalefurniture()));
        alltotalamount.setText(Float.toString(FurnitureModel.getAlltotalamount()));

        furnituretable.setItems(furniturelist);
    }

    private void refreshfurnitureTable() {
        furniturelist.clear();
        furnituretableView();
    }

    private void clearData() {
        detales.setText("");
        unetnumber.setText("");
        unetamount.setText("");
        totalamount.setText("");
        consumptionyear.setText("");
        notes.setText("");
    }

    private void getRowData(TableView table) {
        table.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                ObservableList<FurnitureModel> list = FXCollections.observableArrayList();
                list = table.getSelectionModel().getSelectedItems();
                if (list.isEmpty()) {
                    Validation.showAlert("", "لاتوجد بيانات");
                } else {

                    detales.setText(list.get(0).getDetales());
                    unetnumber.setText(list.get(0).getUnetnumber());
                    unetamount.setText(list.get(0).getUnetamount());
                    totalamount.setText(list.get(0).getTotalamount());
                    totalamount.setText(list.get(0).getTotalamount());
                    consumptionyear.setText(list.get(0).getConsumptionyear());
                    notes.setText(list.get(0).getNotes());
                    furnitureId = list.get(0).getFurnitureid();
                }
            }
        });
    }

}
