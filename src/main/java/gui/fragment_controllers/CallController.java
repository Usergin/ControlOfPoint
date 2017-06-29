package gui.fragment_controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import data.model.information.Call;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import utils.Constants;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Created by oldman on 27.06.17.
 */

@ViewController(value = "/fxml/call_event.fxml")
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
    private JFXTreeTableColumn<CallProperty, Text> typeColumn;
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
//new Image(getClass().getResourceAsStream("/drawables/logo-vs-rf.png"))
    private void setupReadOnlyTableView() {
        setupCellValueFactory(dateColumn, CallProperty::dateProperty);
        setupCellValueFactory(numberColumn, CallProperty::numberProperty);
        setupCellValueFactory(typeColumn, CallProperty::typeProperty);
//            return p.type.asObject();});
        setupCellValueFactory(durationColumn, p -> p.duration.asObject());
        ObservableList<CallProperty> callData = FXCollections.observableArrayList();
        ImageView icon = null;
        for (Call call : callList) {
            switch (call.getType()){
                case Constants.INCOMING_CALL:
                    icon = new ImageView(new Image(getClass().getResourceAsStream("/drawables/incoming-call.png")));
                    break;
                case Constants.OUTGOING_CALL:
                    icon =  new ImageView(new Image(getClass().getResourceAsStream("/drawables/outgoing-call.png")));
                    break;
                case Constants.MISSED_CALL:
                    icon =  new ImageView(new Image(getClass().getResourceAsStream("/drawables/missed_call.png")));
                    break;
                    default:
                        icon =  new ImageView(new Image(getClass().getResourceAsStream("/drawables/phone.png")));
            }
            CallProperty callProperty = new CallProperty(call.getNumber(),
                    call.getDuration(), icon, call.getFormatDate());
            callData.add(callProperty);

        }
        treeTableView.setRoot(new RecursiveTreeItem<>(callData, RecursiveTreeObject::getChildren));
        treeTableView.setShowRoot(false);

         treeTableViewCount.textProperty()
                .bind(Bindings.createStringBinding(() -> PREFIX + treeTableView.getCurrentItemsCount() + POSTFIX,
                        treeTableView.currentItemsCountProperty()));

        searchField.textProperty().addListener(setupSearchField(treeTableView));
    }
    private ChangeListener<String> setupSearchField(final JFXTreeTableView<CallController.CallProperty> tableView) {
        return (o, oldVal, newVal) ->
                tableView.setPredicate(personProp -> {
                    final CallProperty callProperty = personProp.getValue();
                    return callProperty.date.get().contains(newVal)
                            || callProperty.number.get().contains(newVal)
                            || Integer.toString(callProperty.duration.get()).contains(newVal);
                });
    }
    static final class CallProperty extends RecursiveTreeObject<CallProperty> {
        final StringProperty number;
        final SimpleIntegerProperty duration;
        final SimpleObjectProperty<Node> type;
        final StringProperty date;

        CallProperty(String number, int duration, Node type, String date) {
            this.number = new SimpleStringProperty(number);
            this.duration = new SimpleIntegerProperty(duration);
            this.type = new SimpleObjectProperty(type);
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

        public Node getType() {
            return type.get();
        }

        public SimpleObjectProperty typeProperty() {
            return type;
        }

        public void setType(Node type) {
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
