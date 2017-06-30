package gui.fragment_controllers;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import data.model.information.DeviceEvent;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.StackPane;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Created by OldMan on 29.06.2017.
 */

@ViewController(value = "/fxml/device_status.fxml")
public class DeviceStatusController {
    @FXML
    private StackPane root;
    private static final String PREFIX = "( ";
    private static final String POSTFIX = " )";
    private ObservableList<DeviceEvent> observableList = FXCollections.observableArrayList();
    @FXMLViewFlowContext
    ViewFlowContext context;

    // readonly table view
    @FXML
    private JFXTreeTableView<DeviceStatusController.DeviceStatusProperty> treeTableView;
    @FXML
    private JFXTreeTableColumn<DeviceStatusController.DeviceStatusProperty, String> dateColumn;
    @FXML
    private JFXTreeTableColumn<DeviceStatusController.DeviceStatusProperty, String> statusColumn;
    @FXML
    private JFXTextField searchField;
    @FXML
    private Label treeTableViewCount;
    private List<DeviceEvent> deviceEvents;

    @PostConstruct
    public void init() {
        Objects.requireNonNull(context, "device_events_list");
        this.deviceEvents = (List<DeviceEvent>) context.getRegisteredObject("device_events_list");
        setupTableView();
    }

    public ObservableList<DeviceEvent> getObservableList() {
        return observableList;
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<DeviceStatusController.DeviceStatusProperty, T> column, Function<DeviceStatusController.DeviceStatusProperty, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<DeviceStatusController.DeviceStatusProperty, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }

    private void setupTableView() {
        setupCellValueFactory(dateColumn, DeviceStatusController.DeviceStatusProperty::dateProperty);
        setupCellValueFactory(statusColumn, DeviceStatusController.DeviceStatusProperty::statusProperty);
        ObservableList<DeviceStatusController.DeviceStatusProperty> deviceStatusProperties = FXCollections.observableArrayList();
        for (DeviceEvent deviceEvent : deviceEvents) {

            DeviceStatusController.DeviceStatusProperty deviceStatusPropertyatteryProperty = new DeviceStatusController.DeviceStatusProperty(deviceEvent.getFormatDate(),
                    deviceEvent.getStatus());
            deviceStatusProperties.add(deviceStatusPropertyatteryProperty);

        }
        treeTableView.setRoot(new RecursiveTreeItem<>(deviceStatusProperties, RecursiveTreeObject::getChildren));
        treeTableView.setShowRoot(false);

        treeTableViewCount.textProperty()
                .bind(Bindings.createStringBinding(() -> PREFIX + treeTableView.getCurrentItemsCount() + POSTFIX,
                        treeTableView.currentItemsCountProperty()));

        searchField.textProperty().addListener(setupSearchField(treeTableView));
    }

    private ChangeListener<String> setupSearchField(final JFXTreeTableView<DeviceStatusController.DeviceStatusProperty> tableView) {
        return (o, oldVal, newVal) ->
                tableView.setPredicate(propertyTreeItem -> {
                    final DeviceStatusController.DeviceStatusProperty batteryProperty = propertyTreeItem.getValue();
                    return batteryProperty.date.get().contains(newVal)
                            || batteryProperty.status.get().contains(newVal);
                });
    }

    private static final class DeviceStatusProperty extends RecursiveTreeObject<DeviceStatusController.DeviceStatusProperty> {
        final StringProperty date;
        final StringProperty status;

        DeviceStatusProperty(String date, String status) {
            this.status = new SimpleStringProperty(status);
            this.date = new SimpleStringProperty(date);
        }

        public String getStatus() {
            return status.get();
        }

        public StringProperty statusProperty() {
            return status;
        }

        public void setStatus(String status) {
            this.status.set(status);
        }

        public String getDate() {
            return date.get();
        }

        public StringProperty dateProperty() {
            return date;
        }

        public void setDate(String date) {
            this.date.set(date);
        }
    }
}
