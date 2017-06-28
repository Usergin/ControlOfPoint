package gui.fragment_controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import data.model.information.Call;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
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
 * Created by oldman on 27.06.17.
 */

@ViewController(value = "/fxml/call.fxml")
public class CallController {
    @FXML
    private StackPane root;
    private static final String PREFIX = "( ";
    private static final String POSTFIX = " )";
    private ObservableList<Call> callObservableList = FXCollections.observableArrayList();
    @FXMLViewFlowContext
    ViewFlowContext context;

    // readonly table view
    @FXML
    private JFXTreeTableView<CallProperty> treeTableView;
    @FXML
    private JFXTreeTableColumn<CallProperty, String> dateColumn;
    @FXML
    private JFXTreeTableColumn<CallProperty, String> numberColumn;
    @FXML
    private JFXTreeTableColumn<CallProperty, Integer> typeColumn;
    @FXML
    private JFXTreeTableColumn<CallProperty, Integer> durationColumn;
    @FXML
    private JFXTextField searchField;
    @FXML
    private Label treeTableViewCount;

    private List<Call> callList;

    public CallController() {
    }

    @PostConstruct
    public void init() {

        Objects.requireNonNull(context, "call_list");
        this.callList = (List<Call>) context.getRegisteredObject("call_list");
        setupReadOnlyTableView();

    }

    public ObservableList<Call> getCallObservableList() {
        return callObservableList;
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<CallProperty, T> column, Function<CallProperty, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<CallProperty, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }

    private void setupReadOnlyTableView() {
        setupCellValueFactory(dateColumn, CallProperty::dateProperty);
        setupCellValueFactory(numberColumn, CallProperty::numberProperty);
        setupCellValueFactory(typeColumn, p -> p.type.asObject());
        setupCellValueFactory(durationColumn, p -> p.duration.asObject());

        ObservableList<CallProperty> callData = FXCollections.observableArrayList();

        for (Call call : callList) {
            CallProperty callProperty = new CallProperty(call.getNumber(),
                    call.getDuration(), call.getType(), String.valueOf(call.getDate()));
            callData.add(callProperty);

        }

        treeTableView.setRoot(new RecursiveTreeItem<>(callData, RecursiveTreeObject::getChildren));

        treeTableView.setShowRoot(false);
        treeTableViewCount.textProperty()
                .bind(Bindings.createStringBinding(() -> PREFIX + treeTableView.getCurrentItemsCount() + POSTFIX,
                        treeTableView.currentItemsCountProperty()));
//        treeTableViewAdd.disableProperty()
//                .bind(Bindings.notEqual(-1, treeTableView.getSelectionModel().selectedIndexProperty()));
//        treeTableViewRemove.disableProperty()
//                .bind(Bindings.equal(-1, treeTableView.getSelectionModel().selectedIndexProperty()));
//        treeTableViewAdd.setOnMouseClicked((e) -> {
//            dummyData.add(createNewRandomPerson());
//            final IntegerProperty currCountProp = treeTableView.currentItemsCountProperty();
//            currCountProp.set(currCountProp.get() + 1);
//        });
//        treeTableViewRemove.setOnMouseClicked((e) -> {
//            dummyData.remove(treeTableView.getSelectionModel().selectedItemProperty().get().getValue());
//            final IntegerProperty currCountProp = treeTableView.currentItemsCountProperty();
//            currCountProp.set(currCountProp.get() - 1);
//        });
        searchField.textProperty().addListener(setupSearchField(treeTableView));
    }
    private ChangeListener<String> setupSearchField(final JFXTreeTableView<CallController.CallProperty> tableView) {
        return (o, oldVal, newVal) ->
                tableView.setPredicate(personProp -> {
                    final CallProperty callProperty = personProp.getValue();
                    return callProperty.date.get().contains(newVal)
                            || callProperty.number.get().contains(newVal)
                            || Integer.toString(callProperty.duration.get()).contains(newVal)
                            || Integer.toString(callProperty.type.get()).contains(newVal);
                });
    }
    static final class CallProperty extends RecursiveTreeObject<CallProperty> {
        final StringProperty number;
        final SimpleIntegerProperty duration;
        final SimpleIntegerProperty type;
        final StringProperty date;

        CallProperty(String number, int duration, int type, String date) {
            this.number = new SimpleStringProperty(number);
            this.duration = new SimpleIntegerProperty(duration);
            this.type = new SimpleIntegerProperty(type);
            this.date = new SimpleStringProperty(date);
        }

        public String getNumber() {
            return number.get();
        }

        public StringProperty numberProperty() {
            return number;
        }

        public void setNumber(String number) {
            this.number.set(number);
        }

        public int getDuration() {
            return duration.get();
        }

        public SimpleIntegerProperty durationProperty() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration.set(duration);
        }

        public int getType() {
            return type.get();
        }

        public SimpleIntegerProperty typeProperty() {
            return type;
        }

        public void setType(int type) {
            this.type.set(type);
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
