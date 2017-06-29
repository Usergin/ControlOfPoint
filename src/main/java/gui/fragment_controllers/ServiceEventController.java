package gui.fragment_controllers;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import data.model.information.NetworkEvent;
import data.model.information.ServiceEvent;
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
@ViewController(value = "/fxml/service_event.fxml")
public class ServiceEventController {
    @FXML
    private StackPane root;
    private static final String PREFIX = "( ";
    private static final String POSTFIX = " )";
    private ObservableList<ServiceEvent> observableList = FXCollections.observableArrayList();
    @FXMLViewFlowContext
    ViewFlowContext context;

    // readonly table view
    @FXML
    private JFXTreeTableView<ServiceEventProperty> treeTableView;
    @FXML
    private JFXTreeTableColumn<ServiceEventProperty, String> dateColumn;
    @FXML
    private JFXTreeTableColumn<ServiceEventProperty, String> stateColumn;
    @FXML
    private JFXTreeTableColumn<ServiceEventProperty, String> ipColumn;
    @FXML
    private JFXTextField searchField;
    @FXML
    private Label treeTableViewCount;
    private List<ServiceEvent> networkEvents;

    @PostConstruct
    public void init() {
        Objects.requireNonNull(context, "service_events_list");
        this.networkEvents = (List<ServiceEvent>) context.getRegisteredObject("service_events_list");
        setupTableView();
    }

    public ObservableList<ServiceEvent> getObservableList() {
        return observableList;
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<ServiceEventProperty, T> column, Function<ServiceEventProperty, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<ServiceEventProperty, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }

    //new Image(getClass().getResourceAsStream("/drawables/logo-vs-rf.png"))
    private void setupTableView() {
        setupCellValueFactory(dateColumn, ServiceEventProperty::dateProperty);
        setupCellValueFactory(ipColumn, ServiceEventProperty::areaProperty);
        setupCellValueFactory(stateColumn, ServiceEventProperty::eventProperty);
        ObservableList<ServiceEventProperty> serviceEventProperties = FXCollections.observableArrayList();
        for (ServiceEvent serviceEvent : networkEvents) {
            ServiceEventProperty deviceStatusPropertyatteryProperty = new ServiceEventProperty(serviceEvent.getFormatDate(),
                    serviceEvent.getArea(), serviceEvent.getEvent());
            serviceEventProperties.add(deviceStatusPropertyatteryProperty);
        }
        treeTableView.setRoot(new RecursiveTreeItem<>(serviceEventProperties, RecursiveTreeObject::getChildren));
        treeTableView.setShowRoot(false);

        treeTableViewCount.textProperty()
                .bind(Bindings.createStringBinding(() -> PREFIX + treeTableView.getCurrentItemsCount() + POSTFIX,
                        treeTableView.currentItemsCountProperty()));

        searchField.textProperty().addListener(setupSearchField(treeTableView));
    }

    private ChangeListener<String> setupSearchField(final JFXTreeTableView<ServiceEventController.ServiceEventProperty> tableView) {
        return (o, oldVal, newVal) ->
                tableView.setPredicate(propertyTreeItem -> {
                    final ServiceEventController.ServiceEventProperty batteryProperty = propertyTreeItem.getValue();
                    return batteryProperty.date.get().contains(newVal)
                            || batteryProperty.area.get().contains(newVal)
                            || batteryProperty.event.get().contains(newVal);
                });
    }

    private static final class ServiceEventProperty extends RecursiveTreeObject<ServiceEventProperty> {
        final StringProperty date;
        final StringProperty area;
        final StringProperty event;

        ServiceEventProperty(String date, String area, String event) {
            this.area = new SimpleStringProperty(area);
            this.date = new SimpleStringProperty(date);
            this.event = new SimpleStringProperty(event);
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

        public String getArea() {
            return area.get();
        }

        public StringProperty areaProperty() {
            return area;
        }

        public void setArea(String area) {
            this.area.set(area);
        }

        public String getEvent() {
            return event.get();
        }

        public StringProperty eventProperty() {
            return event;
        }

        public void setEvent(String event) {
            this.event.set(event);
        }
    }
}
