
package Controlleres;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;


public class ComboBoxFill {
    
     List<String> list1 = new ArrayList<String>();
     
    public static void fillComboBox(ComboBox com, String[] item  ){
        List<String> value = new ArrayList<String>();
        for (int i = 0; i < item.length; i++) {
            value.add(item[i]);
        }
        ObservableList<String> list = FXCollections.observableArrayList(value);
        com.setItems(list);
    }
}
