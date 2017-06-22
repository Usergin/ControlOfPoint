package gui.ui_components;

import data.model.Device;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.IOException;

/**
 * Created by OldMan on 17.06.2017.
 */
public class DeviceItemViewCell extends ListCell<Device> {

    @FXML
    private Label label_model;
    @FXML
    private Label label_version_os;
    @FXML
    private Label label_imei;
    @FXML
    private HBox root;
    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(Device item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/fxml/ui/device_item.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            label_model.setText(String.valueOf(item.getModel()));
            label_version_os.setText(String.valueOf(item.getVersion_os()));
            label_imei.setText(String.valueOf(item.getImei()));

//            setText(null);
            setGraphic(root);
        }

    }
}