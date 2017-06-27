package gui.fragment_controllers;

import com.jfoenix.controls.*;
import data.remote.model.information.Call;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

import javax.annotation.PostConstruct;

/**
 * Created by oldman on 27.06.17.
 */

@ViewController(value = "/fxml/call.fxml")
public class CallController {
    @FXML
    private StackPane root;
    @FXML
    private JFXDatePicker dateOverlay;
    private static final String PREFIX = "( ";
    private static final String POSTFIX = " )";

    // readonly table view
//    @FXML
//    private JFXTreeTableView<Call> treeTableView;
//    @FXML
//    private JFXTreeTableColumn<Person, String> firstNameColumn;
//    @FXML
//    private JFXTreeTableColumn<Person, String> lastNameColumn;
//    @FXML
//    private JFXTreeTableColumn<Person, Integer> ageColumn;
    @FXML
    private JFXTextField searchField;
    @PostConstruct
    public void init() {
        dateOverlay.setDialogParent(root);
    }

}
