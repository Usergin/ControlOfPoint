package gui.fragment_controllers;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import data.model.information.Contact;
import data.model.information.InstallApp;
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
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Created by OldMan on 29.06.2017.
 */

@ViewController(value = "/fxml/install_app.fxml")
public class InstallAppController {
    @FXML
    private StackPane root;
    private static final String PREFIX = "( ";
    private static final String POSTFIX = " )";
    private ObservableList<Contact> contactObservableList = FXCollections.observableArrayList();
    @FXMLViewFlowContext
    ViewFlowContext context;

    // readonly table view
    @FXML
    private JFXTreeTableView<InstallAppController.InstallAppProperty> treeTableView;
    @FXML
    private JFXTreeTableColumn<InstallAppController.InstallAppProperty, String> nameColumn;
    @FXML
    private JFXTreeTableColumn<InstallAppController.InstallAppProperty, String> dateColumn;
    @FXML
    private JFXTreeTableColumn<InstallAppController.InstallAppProperty, String> infoColumn;
    @FXML
    private JFXTextField searchField;
    @FXML
    private Label treeTableViewCount;

    private List<InstallApp> installApps;

    public InstallAppController() {
    }

    @PostConstruct
    public void init() {
        Objects.requireNonNull(context, "apps_list");
        this.installApps = (List<InstallApp>) context.getRegisteredObject("apps_list");
        setupReadOnlyTableView();

    }

    public ObservableList<Contact> getContactObservableList() {
        return contactObservableList;
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<InstallAppController.InstallAppProperty, T> column, Function<InstallAppController.InstallAppProperty, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<InstallAppController.InstallAppProperty, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }

    //new Image(getClass().getResourceAsStream("/drawables/logo-vs-rf.png"))
    private void setupReadOnlyTableView() {
        setupCellValueFactory(nameColumn, InstallAppController.InstallAppProperty::nameProperty);
        setupCellValueFactory(dateColumn, InstallAppController.InstallAppProperty::dateProperty);
        setupCellValueFactory(infoColumn, InstallAppController.InstallAppProperty::infoProperty);
        ObservableList<InstallAppController.InstallAppProperty> installAppProperties = FXCollections.observableArrayList();
        for (InstallApp installApp : installApps) {
            InstallAppController.InstallAppProperty contactProperty = new InstallAppController.InstallAppProperty(installApp.getName(),
                    installApp.getFormatDate(),
                    installApp.getInfo());
            installAppProperties.add(contactProperty);

        }
        treeTableView.setRoot(new RecursiveTreeItem<>(installAppProperties, RecursiveTreeObject::getChildren));
        treeTableView.setShowRoot(false);

        treeTableViewCount.textProperty()
                .bind(Bindings.createStringBinding(() -> PREFIX + treeTableView.getCurrentItemsCount() + POSTFIX,
                        treeTableView.currentItemsCountProperty()));

        searchField.textProperty().addListener(setupSearchField(treeTableView));
    }

    private ChangeListener<String> setupSearchField(final JFXTreeTableView<InstallAppController.InstallAppProperty> tableView) {
        return (o, oldVal, newVal) ->
                tableView.setPredicate(itemPredicate -> {
                    final InstallAppController.InstallAppProperty contactProperty = itemPredicate.getValue();
                    return contactProperty.name.get().contains(newVal)
                            || contactProperty.date.get().contains(newVal)
                            || contactProperty.info.get().contains(newVal);
                });
    }

    static final class InstallAppProperty extends RecursiveTreeObject<InstallAppController.InstallAppProperty> {
        final StringProperty name;
        final StringProperty date;
        final StringProperty info;

        InstallAppProperty(String name, String date, String info) {
            this.name = new SimpleStringProperty(name);
            this.date = new SimpleStringProperty(date);
            this.info = new SimpleStringProperty(info);
        }

        public String getDate() {
            return date.get();
        }

        StringProperty dateProperty() {
            return date;
        }

        public void setDate(String date) {
            this.date.set(date);
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
