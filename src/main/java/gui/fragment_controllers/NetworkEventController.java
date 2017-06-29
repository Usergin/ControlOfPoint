package gui.fragment_controllers;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import data.model.information.NetworkEvent;
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

@ViewController(value = "/fxml/network_event.fxml")
public class NetworkEventController {
    @FXML
    private StackPane root;
    private static final String PREFIX = "( ";
    private static final String POSTFIX = " )";
    private ObservableList<NetworkEvent> observableList = FXCollections.observableArrayList();
    @FXMLViewFlowContext
    ViewFlowContext context;

    // readonly table view
    @FXML
    private JFXTreeTableView<NetworkEventController.NetworkEventProperty> treeTableView;
    @FXML
    private JFXTreeTableColumn<NetworkEventController.NetworkEventProperty, String> dateColumn;
    @FXML
    private JFXTreeTableColumn<NetworkEventController.NetworkEventProperty, String> stateColumn;
    @FXML
    private JFXTreeTableColumn<NetworkEventController.NetworkEventProperty, String> ipColumn;
    @FXML
    private JFXTextField searchField;
    @FXML
    private Label treeTableViewCount;
    private List<NetworkEvent> networkEvents;

    @PostConstruct
    public void init() {
        Objects.requireNonNull(context, "network_events_list");
        this.networkEvents = (List<NetworkEvent>) context.getRegisteredObject("network_events_list");
        setupTableView();
    }

    public ObservableList<NetworkEvent> getObservableList() {
        return observableList;
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<NetworkEventController.NetworkEventProperty, T> column, Function<NetworkEventController.NetworkEventProperty, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<NetworkEventController.NetworkEventProperty, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }

    //new Image(getClass().getResourceAsStream("/drawables/logo-vs-rf.png"))
    private void setupTableView() {
        setupCellValueFactory(dateColumn, NetworkEventController.NetworkEventProperty::dateProperty);
        setupCellValueFactory(ipColumn, NetworkEventController.NetworkEventProperty::ipProperty);
        setupCellValueFactory(stateColumn, NetworkEventController.NetworkEventProperty::stateProperty);
        ObservableList<NetworkEventController.NetworkEventProperty> networkEventProperties = FXCollections.observableArrayList();
        for (NetworkEvent networkEvent : networkEvents) {
            NetworkEventController.NetworkEventProperty deviceStatusPropertyatteryProperty = new NetworkEventController.NetworkEventProperty(networkEvent.getFormatDate(),
                    networkEvent.getState(), networkEvent.getIp());
            networkEventProperties.add(deviceStatusPropertyatteryProperty);
        }
        treeTableView.setRoot(new RecursiveTreeItem<>(networkEventProperties, RecursiveTreeObject::getChildren));
        treeTableView.setShowRoot(false);

        treeTableViewCount.textProperty()
                .bind(Bindings.createStringBinding(() -> PREFIX + treeTableView.getCurrentItemsCount() + POSTFIX,
                        treeTableView.currentItemsCountProperty()));

        searchField.textProperty().addListener(setupSearchField(treeTableView));
    }

    private ChangeListener<String> setupSearchField(final JFXTreeTableView<NetworkEventController.NetworkEventProperty> tableView) {
        return (o, oldVal, newVal) ->
                tableView.setPredicate(propertyTreeItem -> {
                    final NetworkEventController.NetworkEventProperty batteryProperty = propertyTreeItem.getValue();
                    return batteryProperty.date.get().contains(newVal)
                            || batteryProperty.ip.get().contains(newVal)
                            || batteryProperty.state.get().contains(newVal);
                });
    }

    private static final class NetworkEventProperty extends RecursiveTreeObject<NetworkEventController.NetworkEventProperty> {
        final StringProperty date;
        final StringProperty state;
        final StringProperty ip;

        NetworkEventProperty(String date, String state, String ip) {
            this.state = new SimpleStringProperty(state);
            this.date = new SimpleStringProperty(date);
            this.ip = new SimpleStringProperty(ip);
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

        public String getState() {
            return state.get();
        }

        public StringProperty stateProperty() {
            return state;
        }

        public void setState(String state) {
            this.state.set(state);
        }

        public String getIp() {
            return ip.get();
        }

        public StringProperty ipProperty() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip.set(ip);
        }
    }
}