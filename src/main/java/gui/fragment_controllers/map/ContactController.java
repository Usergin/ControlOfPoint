package gui.fragment_controllers.map;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import data.model.information.Contact;
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
@ViewController(value = "/fxml/contact.fxml")
public class ContactController {
    @FXML
    private StackPane root;
    private static final String PREFIX = "( ";
    private static final String POSTFIX = " )";
    private ObservableList<Contact> contactObservableList = FXCollections.observableArrayList();
    @FXMLViewFlowContext
    ViewFlowContext context;

    // readonly table view
    @FXML
    private JFXTreeTableView<ContactController.ContactProperty> treeTableView;
    @FXML
    private JFXTreeTableColumn<ContactController.ContactProperty, String> nameColumn;
    @FXML
    private JFXTreeTableColumn<ContactController.ContactProperty, String> numberColumn;
    @FXML
    private JFXTreeTableColumn<ContactController.ContactProperty, String> eMailColumn;
    @FXML
    private JFXTreeTableColumn<ContactController.ContactProperty, String> infoColumn;
    @FXML
    private JFXTextField searchField;
    @FXML
    private Label treeTableViewCount;

    private List<Contact> contactList;

    public ContactController() {
    }

    @PostConstruct
    public void init() {
        Objects.requireNonNull(context, "contact_list");
        this.contactList = (List<Contact>) context.getRegisteredObject("contact_list");
        setupReadOnlyTableView();

    }

    public ObservableList<Contact> getContactObservableList() {
        return contactObservableList;
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<ContactController.ContactProperty, T> column, Function<ContactController.ContactProperty, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<ContactController.ContactProperty, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }

    //new Image(getClass().getResourceAsStream("/drawables/logo-vs-rf.png"))
    private void setupReadOnlyTableView() {
        setupCellValueFactory(nameColumn, ContactProperty::nameProperty);
        setupCellValueFactory(numberColumn, ContactProperty::numberProperty);
        setupCellValueFactory(eMailColumn, ContactProperty::eMailProperty);
        setupCellValueFactory(infoColumn, ContactProperty::infoProperty);
        ObservableList<ContactController.ContactProperty> contactData = FXCollections.observableArrayList();
        for (Contact contact : contactList) {
            StringBuffer number = new StringBuffer();
            if (contact.getMainNumber() != null)
                number.append("Основной: ").append(contact.getMainNumber()).append(System.getProperty("line.separator"));
            if (contact.getHomeNumber() != null)
                number.append("Домашний: ").append(contact.getHomeNumber()).append(System.getProperty("line.separator"));
            if (contact.getWorkNumber() != null)
                number.append("Рабочий: ").append(contact.getWorkNumber()).append(System.getProperty("line.separator"));
            if (contact.getNumber() != null)
                number.append("Дополнительный: ").append(contact.getNumber()).append(System.getProperty("line.separator"));

            ContactController.ContactProperty contactProperty = new ContactController.ContactProperty(contact.getName(),
                    number.toString(),
                    contact.getEMail(), contact.getInfo());
            contactData.add(contactProperty);

        }
        treeTableView.setRoot(new RecursiveTreeItem<>(contactData, RecursiveTreeObject::getChildren));
        treeTableView.setShowRoot(false);

        treeTableViewCount.textProperty()
                .bind(Bindings.createStringBinding(() -> PREFIX + treeTableView.getCurrentItemsCount() + POSTFIX,
                        treeTableView.currentItemsCountProperty()));

        searchField.textProperty().addListener(setupSearchField(treeTableView));
    }

    private ChangeListener<String> setupSearchField(final JFXTreeTableView<ContactController.ContactProperty> tableView) {
        return (o, oldVal, newVal) ->
                tableView.setPredicate(personProp -> {
                    final ContactController.ContactProperty contactProperty = personProp.getValue();
                    return contactProperty.name.get().contains(newVal)
                            || contactProperty.number.get().contains(newVal)
                            || contactProperty.eMail.get().contains(newVal)
                            || contactProperty.info.get().contains(newVal);
                });
    }

    static final class ContactProperty extends RecursiveTreeObject<ContactController.ContactProperty> {
        final StringProperty name;
        final StringProperty number;
        final StringProperty eMail;
        final StringProperty info;

        ContactProperty(String name, String number, String eMail, String info) {
            this.name = new SimpleStringProperty(name);
            this.number = new SimpleStringProperty(number);
            this.eMail = new SimpleStringProperty(eMail);
            this.info = new SimpleStringProperty(info);
        }

        public String getNumber() {
            return number.get();
        }

        StringProperty numberProperty() {
            return number;
        }

        public void setNumber(String number) {
            this.number.set(number);
        }

        public String getName() {
            return name.get();
        }

        StringProperty nameProperty() {
            return name;
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public String getEMail() {
            return eMail.get();
        }

        StringProperty eMailProperty() {
            return eMail;
        }

        public void setEMail(String eMail) {
            this.eMail.set(eMail);
        }

        public String getInfo() {
            return info.get();
        }

        StringProperty infoProperty() {
            return info;
        }

        public void setInfo(String info) {
            this.info.set(info);
        }
    }
}
