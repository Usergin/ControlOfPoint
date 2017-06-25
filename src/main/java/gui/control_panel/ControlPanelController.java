package gui.control_panel;

import com.jfoenix.controls.*;
import dagger.Injector;
import dagger.application.AppModule;
import dagger.control_panel.ControlPanelModule;
import data.model.Device;
import gui.fragment_controllers.device_info.DeviceInfoController;
import gui.fragment_controllers.SpinnerController;
import gui.map.MapController;
import gui.menu.MainMenuController;
import gui.ui_components.DeviceItemViewCell;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.container.AnimatedFlowContainer;
import io.datafx.controller.flow.container.ContainerAnimations;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.apache.log4j.Logger;
import utils.RxBus;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Objects;

/**
 * Created by OldMan on 04.06.2017.
 */

@ViewController(value = "/fxml/control_panel.fxml")
public class ControlPanelController implements ControlPanelView, EventHandler<MouseEvent> {
    private static final Logger LOG = Logger.getLogger(ControlPanelController.class);
    @Inject
    ControlPanelPresenter controlPanelPresenter;
    @FXMLViewFlowContext
    @Inject
    ViewFlowContext context;
    @ViewNode
    private StackPane rootPane;
    @ViewNode
    private StackPane centerPane;
    @ViewNode
    private StackPane spinnerPane;
    @ViewNode
    private JFXHamburger device_menu;
    @ViewNode
    private StackPane optionsBurger;
    @FXML
    private JFXRippler back;
    @ViewNode
    private JFXRippler ripple_device_menu;
    @ViewNode
    private StackPane update;
    private JFXListView<Device> list;
    private JFXPopup toolbarPopup, devicePopup;
    private FlowHandler flowHandler;
    private ObservableList<Device> devicesData = FXCollections.observableArrayList();

    /**
     * init fxml when loaded.
     */
    private FadeTransition fadeInBack, fadeInDeviceMenu;

    @PostConstruct
    public void init() throws Exception {
        Injector.inject(this, Arrays.asList(new ControlPanelModule(), new AppModule()));
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
        list.setOnMouseClicked(this);

        fadeInBack = getFadeTransition(back);
        fadeInDeviceMenu = getFadeTransition(ripple_device_menu);
        // create the inner flow and content
        context = new ViewFlowContext();
        controlPanelPresenter.setControlPanelView(this);
        controlPanelPresenter.onNewDeviceList();
    }

    private FadeTransition getFadeTransition(JFXRippler jfxRippler) {
        FadeTransition fadeIn = new FadeTransition(
                Duration.millis(500)
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
        LOG.info("handleSuccess " + devices.size());
        list.setItems(devicesData);
        list.setCellFactory(param -> new DeviceItemViewCell());
        devicePopup = new JFXPopup(list);
        devicePopup.setAutoFix(true);
        devicePopup.setHideOnEscape(true);
        ripple_device_menu.setOnMouseClicked(e -> devicePopup.show(device_menu, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT));
    }

    public ObservableList<Device> getDevicesData() {
        LOG.info("handleSuccess " + devicesData.size());
        return devicesData;
    }

    @FXML
    private void onUpdate() {
        if (controlPanelPresenter != null)
            controlPanelPresenter.onNewDeviceList();
    }

    @FXML
    public void onBack() throws VetoException, FlowException {
        flowHandler.navigateTo(MapController.class);
        if (flowHandler.getCurrentViewControllerClass().equals(DeviceInfoController.class)) {
            back.setVisible(true);
        } else
            back.setVisible(false);
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
    public void showSpinner(boolean enable) {
        spinnerPane.setVisible(enable);
    }

    @Override
    public void showMapFlow() {
        Flow innerFlow = new Flow(MapController.class);
        flowHandler = innerFlow.createHandler(context);
        context.register("ContentFlow", innerFlow);
        showFlow(flowHandler);
        RxBus.instanceOf().getDeviceInfo().subscribe(device_id -> {
            LOG.info("device_id " + device_id);
            controlPanelPresenter.onDeviceById(Integer.parseInt(device_id));
        });
    }

    @Override
    public void showDeviceFlow(Device device) {
        if (devicePopup.isShowing())
            devicePopup.hide();
        Flow innerFlow = new Flow(DeviceInfoController.class).withLink(DeviceInfoController.class, "back", MapController.class);
        flowHandler = innerFlow.createHandler(context);
        context.register("device", device);
        context.register("ContentFlow", innerFlow);
        showFlow(flowHandler);
    }

    @Override
    public void showErrorFlow() {
        Flow innerFlow =new Flow(MapController.class);
        flowHandler = innerFlow.createHandler(context);
        context.register("ContentFlow", innerFlow);
        showFlow(flowHandler);
    }

    private void showFlow(FlowHandler flowHandler) {
        context.register("ContentFlowHandler", flowHandler);
        final Duration containerAnimationDuration = Duration.millis(320);
        try {
            centerPane.getChildren().add(flowHandler.start(new AnimatedFlowContainer(Duration.millis(320), ContainerAnimations.ZOOM_OUT)));
        } catch (FlowException e) {
            e.printStackTrace();
        }

        if (flowHandler.getCurrentViewControllerClass().equals(MapController.class)) {
            back.setVisible(false);
            fadeInBack.playFromStart();
        } else if (flowHandler.getCurrentViewControllerClass().equals(DeviceInfoController.class)) {
            back.setVisible(true);
            fadeInBack.playFromStart();
        }
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getClickCount() == 2) {
            //Use ListView's getSelected Item
            LOG.info("getClickCount = 2 " + list.getSelectionModel()
                    .getSelectedItem());
            controlPanelPresenter.onDeviceById(list.getSelectionModel()
                    .getSelectedItem().getId());
        } else {
            RxBus.instanceOf().setShortDeviceInfo(list.getSelectionModel().getSelectedItem());
        }
    }
}
