package gui.fragment_controllers;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import data.model.information.Message;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.beans.binding.Bindings;
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
 * Created by OldMan on 29.06.2017.
 */
@ViewController(value = "/fxml/message_event.fxml")
public class MessageController {
    @FXML
    private StackPane root;
    private static final String PREFIX = "( ";
    private static final String POSTFIX = " )";
    private ObservableList<Message> smsObservableList = FXCollections.observableArrayList();
    @FXMLViewFlowContext
    ViewFlowContext context;

    // readonly table view
    @FXML
    private JFXTreeTableView<MessageProperty> treeTableView;
    @FXML
    private JFXTreeTableColumn<MessageProperty, String> dateColumn;
    @FXML
    private JFXTreeTableColumn<MessageProperty, String> numberColumn;
    @FXML
    private JFXTreeTableColumn<MessageProperty, Text> typeColumn;
    @FXML
    private JFXTreeTableColumn<MessageProperty, String> dataColumn;
    @FXML
    private JFXTextField searchField;
    @FXML
    private Label treeTableViewCount;

    private List<Message> smsList;

    public MessageController() {
    }

    @PostConstruct
    public void init() {
        Objects.requireNonNull(context, "sms_list");
        this.smsList = (List<Message>) context.getRegisteredObject("sms_list");
        setupReadOnlyTableView();

    }

    public ObservableList<Message> getSmsObservableList() {
        return smsObservableList;
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<MessageController.MessageProperty, T> column, Function<MessageController.MessageProperty, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<MessageController.MessageProperty, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }
    //new Image(getClass().getResourceAsStream("/drawables/logo-vs-rf.png"))
    private void setupReadOnlyTableView() {
        setupCellValueFactory(dateColumn, MessageProperty::dateProperty);
        setupCellValueFactory(numberColumn, MessageProperty::numberProperty);
        setupCellValueFactory(typeColumn, MessageProperty::typeProperty);
        setupCellValueFactory(dataColumn, MessageProperty::dataProperty);
        ObservableList<MessageController.MessageProperty> messageProperties = FXCollections.observableArrayList();
        ImageView icon = null;
        for (Message message : smsList) {
            switch (message.getType()){
                case Constants.INCOMING_SMS:
                    icon = new ImageView(new Image(getClass().getResourceAsStream("/drawables/incoming-sms.png")));
                    break;
                case Constants.OUTGOING_SMS:
                    icon =  new ImageView(new Image(getClass().getResourceAsStream("/drawables/outgoing-sms.png")));
                    break;
                    default:
                        icon =  new ImageView(new Image(getClass().getResourceAsStream("/drawables/sms.png")));
                        break;
            }
            MessageController.MessageProperty messageProperty = new MessageController.MessageProperty(message.getFormatDate(),
                    message.getNumber(),message.getData(), icon);
            messageProperties.add(messageProperty);

        }
        treeTableView.setRoot(new RecursiveTreeItem<>(messageProperties, RecursiveTreeObject::getChildren));
        treeTableView.setShowRoot(false);

        treeTableViewCount.textProperty()
                .bind(Bindings.createStringBinding(() -> PREFIX + treeTableView.getCurrentItemsCount() + POSTFIX,
                        treeTableView.currentItemsCountProperty()));

        searchField.textProperty().addListener(setupSearchField(treeTableView));
    }
    private ChangeListener<String> setupSearchField(final JFXTreeTableView<MessageProperty> tableView) {
        return (o, oldVal, newVal) ->
                tableView.setPredicate(itemPredicate -> {
                    final MessageProperty messageProperty = itemPredicate.getValue();
                    return messageProperty.date.get().contains(newVal)
                            || messageProperty.number.get().contains(newVal)
                            || messageProperty.dataProperty().get().contains(newVal);
                });
    }
   private static final class MessageProperty extends RecursiveTreeObject<MessageController.MessageProperty> {
        final StringProperty number;
        final StringProperty data;
        final SimpleObjectProperty<Node> type;
        final StringProperty date;

        MessageProperty(String date, String number, String data, Node type) {
            this.number = new SimpleStringProperty(number);
            this.data = new SimpleStringProperty(data);
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

        public String getData() {
            return data.get();
        }

        public StringProperty dataProperty() {
            return data;
        }

        public void setData(String duration) {
            this.data.set(duration);
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
