package gui.control_panel;

import business.control_panel.ControlPanelInteractor;
import business.control_panel.ControlPanelInteractorImpl;
import com.jfoenix.controls.*;
import data.model.Device;
import gui.fragment_controllers.MapController;
import gui.fragment_controllers.SpinnerController;
import gui.menu.MainMenuController;
import gui.ui_components.DeviceItemViewCell;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.container.AnimatedFlowContainer;
import io.datafx.controller.flow.container.ContainerAnimations;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by OldMan on 04.06.2017.
 */

@ViewController(value = "/fxml/control_panel.fxml")
public class ControlPanelController implements ControlPanelView, EventHandler<MouseEvent> {
    private static final Logger LOG = Logger.getLogger(ControlPanelController.class);

    @FXMLViewFlowContext
    private ViewFlowContext context;
    @ViewNode
    private StackPane rootPane;
    @ViewNode
    private StackPane centerPane;
    @ViewNode
    private JFXHamburger device_menu;
    @ViewNode
    private StackPane optionsBurger;
    @FXML
    @ActionTrigger("back")
    private JFXRippler back;
    @ViewNode
    private JFXRippler ripple_device_menu;
    @ViewNode
    @ActionTrigger("update")
    private StackPane update;
    private JFXListView<Device> list;
    private JFXPopup toolbarPopup;
    private JFXPopup popup;
    private ControlPanelInteractor controlPanelInteractor;
    private FlowHandler flowHandler;
    private ObservableList<Device> devicesData = FXCollections.observableArrayList();


    /**
     * init fxml when loaded.
     */
    private FadeTransition fadeInBack, fadeInDeviceMenu;

    @PostConstruct
    public void init() throws Exception {
        Objects.requireNonNull(context, "context");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ui/main_menu.fxml"));
        loader.setController(new MainMenuController());
        toolbarPopup = new JFXPopup(loader.load());
        optionsBurger.setOnMouseClicked(e -> toolbarPopup.show(optionsBurger,
                JFXPopup.PopupVPosition.TOP,
                JFXPopup.PopupHPosition.RIGHT,
                -12,
                15));

        list = new JFXListView<>();
        fadeInBack = getFadeTransition(back);
        fadeInDeviceMenu = getFadeTransition(ripple_device_menu);
        // create the inner flow and content
        context = new ViewFlowContext();
        controlPanelInteractor = new ControlPanelInteractorImpl(this);
//      showFlow(new Flow(MapController.class).withLink(MapController.class, "back", SpinnerController.class));
    }

    private FadeTransition getFadeTransition(JFXRippler jfxRippler) {
        FadeTransition fadeIn = new FadeTransition(
                Duration.millis(3000)
        );
        fadeIn.setNode(jfxRippler);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.setCycleCount(1);
        fadeIn.setAutoReverse(false);
        return fadeIn;
    }

    @Override
    public void setDeviceList(ObservableList<Device> devices) {
        this.devicesData = devices;
//        devicesData.addListener(new ListChangeListener() {
//            @Override
//            public void onChanged(ListChangeListener.Change change) {
//                System.out.println("change!");
//            }
//        });
        LOG.info("handleSuccess " + devices.size());
        list.setItems(devicesData);
        list.setCellFactory(param -> new DeviceItemViewCell());
        list.setOnMouseClicked(this);
        JFXPopup popup = new JFXPopup(list);
        popup.setAutoFix(true);
        popup.setHideOnEscape(true);
        ripple_device_menu.setOnMouseClicked(e -> popup.show(device_menu, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT));
    }

    public ObservableList<Device> getDevicesData() {
        LOG.info("handleSuccess " + devicesData.size());
        return devicesData;
    }

    @ActionMethod("update")
    private void update() {
        if (controlPanelInteractor != null)
            controlPanelInteractor.getNewDeviceList();
    }

    @ActionMethod("back")
    public void onBack() throws VetoException, FlowException {
        flowHandler.navigateTo(MapController.class);
        if (flowHandler.getCurrentViewControllerClass().equals(MapController.class)) {
            back.setVisible(false);
        }
    }

    public void showSnackBar(String message) {
        JFXSnackbar jfxSnackbar = new JFXSnackbar(rootPane);
        jfxSnackbar.show(message, 5000);
    }

    @Override
    public void showDeviceMenu(boolean val) {
        if (ripple_device_menu != null) {
            ripple_device_menu.setEnabled(val);
            ripple_device_menu.setVisible(val);
            fadeInDeviceMenu.playFromStart();
        }
    }

    @Override
    public void showSpinnerFlow() {
        showFlow(new Flow(SpinnerController.class));
    }

    @Override
    public void showMapFlow() {
        showFlow(new Flow(MapController.class));
    }

    @Override
    public void showDeviceFlow() {
        showFlow(new Flow(MapController.class).withLink(MapController.class, "back", SpinnerController.class));
    }

    @Override
    public void showErrorFlow() {
        showFlow(new Flow(MapController.class).withLink(MapController.class, "back", SpinnerController.class));
    }

    private void showFlow(Flow innerFlow) {
        flowHandler = innerFlow.createHandler(context);
        context.register("ContentFlowHandler", flowHandler);
        context.register("ContentFlow", innerFlow);
        context.register("devices", devicesData);
        final Duration containerAnimationDuration = Duration.millis(320);
        try {
            centerPane.getChildren().add(flowHandler.start(new AnimatedFlowContainer(Duration.millis(320), ContainerAnimations.ZOOM_OUT)));
        } catch (FlowException e) {
            e.printStackTrace();
        }
        if (flowHandler.getCurrentViewControllerClass().equals(MapController.class)
                || flowHandler.getCurrentViewControllerClass().equals(SpinnerController.class)) {
            back.setVisible(false);
            fadeInBack.playFromStart();
        } else {
            back.setVisible(true);
            fadeInBack.playFromStart();
        }
    }

    @Override
    public void handle(MouseEvent event) {
        LOG.info("handleSuccess " +list.getSelectionModel().getSelectedItem().getModel());
    }
}
