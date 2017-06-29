package gui.fragment_controllers;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import data.model.information.BatteryEvent;
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

@ViewController(value = "/fxml/battery_event.fxml")
public class BatteryController {
    @FXML
    private StackPane root;
    private static final String PREFIX = "( ";
    private static final String POSTFIX = " )";
    private ObservableList<BatteryEvent> observableList = FXCollections.observableArrayList();
    @FXMLViewFlowContext
    ViewFlowContext context;

    // readonly table view
    @FXML
    private JFXTreeTableView<BatteryController.BatteryProperty> treeTableView;
    @FXML
    private JFXTreeTableColumn<BatteryController.BatteryProperty, String> dateColumn;
    @FXML
    private JFXTreeTableColumn<BatteryController.BatteryProperty, String> statusColumn;
    @FXML
    private JFXTreeTableColumn<BatteryController.BatteryProperty, String> levelColumn;
    @FXML
    private JFXTreeTableColumn<BatteryController.BatteryProperty, String> batteryStatusColumn;
    @FXML
    private JFXTreeTableColumn<BatteryController.BatteryProperty, String> typeChargingColumn;
    @FXML
    private JFXTextField searchField;
    @FXML
    private Label treeTableViewCount;
    private List<BatteryEvent> batteryEvents;

    @PostConstruct
    public void init() {
        Objects.requireNonNull(context, "battery_list");
        this.batteryEvents = (List<BatteryEvent>) context.getRegisteredObject("battery_list");
        setupTableView();
    }

    public ObservableList<BatteryEvent> getObservableList() {
        return observableList;
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<BatteryController.BatteryProperty, T> column, Function<BatteryController.BatteryProperty, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<BatteryController.BatteryProperty, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }

    //new Image(getClass().getResourceAsStream("/drawables/logo-vs-rf.png"))
    private void setupTableView() {
        setupCellValueFactory(dateColumn, BatteryController.BatteryProperty::dateProperty);
        setupCellValueFactory(statusColumn, BatteryController.BatteryProperty::statusProperty);
        setupCellValueFactory(batteryStatusColumn, BatteryController.BatteryProperty::batteryStatusProperty);
        setupCellValueFactory(levelColumn, BatteryController.BatteryProperty::levelProperty);
        setupCellValueFactory(typeChargingColumn, BatteryController.BatteryProperty::connectionStatusProperty);
        ObservableList<BatteryController.BatteryProperty> callData = FXCollections.observableArrayList();
        for (BatteryEvent batteryEvent : batteryEvents) {
            BatteryController.BatteryProperty batteryProperty = new BatteryController.BatteryProperty(batteryEvent.getFormatDate(),
                    batteryEvent.getStatus(), batteryEvent.getLevel(), batteryEvent.getBatteryStatus(), batteryEvent.getTypeCharging());
            callData.add(batteryProperty);
        }
        treeTableView.setRoot(new RecursiveTreeItem<>(callData, RecursiveTreeObject::getChildren));
        treeTableView.setShowRoot(false);

        treeTableViewCount.textProperty()
                .bind(Bindings.createStringBinding(() -> PREFIX + treeTableView.getCurrentItemsCount() + POSTFIX,
                        treeTableView.currentItemsCountProperty()));

        searchField.textProperty().addListener(setupSearchField(treeTableView));
    }

    private ChangeListener<String> setupSearchField(final JFXTreeTableView<BatteryController.BatteryProperty> tableView) {
        return (o, oldVal, newVal) ->
                tableView.setPredicate(propertyTreeItem -> {
                    final BatteryController.BatteryProperty batteryProperty = propertyTreeItem.getValue();
                    return batteryProperty.date.get().contains(newVal)
                            || batteryProperty.batteryStatus.get().contains(newVal)
                            || batteryProperty.status.get().contains(newVal)
                            || batteryProperty.connectionStatus.get().contains(newVal)
                            || batteryProperty.level.get().contains(newVal);
                });
    }

    static final class BatteryProperty extends RecursiveTreeObject<BatteryController.BatteryProperty> {
        final StringProperty date;
        final StringProperty status;
        final StringProperty level;
        final StringProperty batteryStatus;
        final StringProperty connectionStatus;


        BatteryProperty(String date, String status, String level, String batteryStatus, String connectionStatus) {
            this.status = new SimpleStringProperty(status);
            this.level = new SimpleStringProperty(level);
            this.batteryStatus = new SimpleStringProperty(batteryStatus);
            this.connectionStatus = new SimpleStringProperty(connectionStatus);
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

        public String getLevel() {
            return level.get();
        }

        public StringProperty levelProperty() {
            return level;
        }

        public void setLevel(String level) {
            this.level.set(level);
        }

        public String getBatteryStatus() {
            return batteryStatus.get();
        }

        public StringProperty batteryStatusProperty() {
            return batteryStatus;
        }

        public void setBatteryStatus(String type) {
            this.batteryStatus.set(type);
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

        public String getConnectionStatus() {
            return connectionStatus.get();
        }

        public StringProperty connectionStatusProperty() {
            return connectionStatus;
        }

        public void setConnectionStatus(String connectionStatus) {
            this.connectionStatus.set(connectionStatus);
        }
    }
}
