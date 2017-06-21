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
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * Created by OldMan on 04.06.2017.
 */

@ViewController(value = "/fxml/control_panel.fxml")
public class ControlPanelController {

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
    private static final Logger LOG = Logger.getLogger(ControlPanelController.class);
    private ControlPanelInteractor controlPanelInteractor;
    private FlowHandler flowHandler;
    /**
     * init fxml when loaded.
     */
    private FadeTransition fadeIn = new FadeTransition(
            Duration.millis(2000)
    );

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
        fadeIn.setNode(back);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.setCycleCount(1);
        fadeIn.setAutoReverse(false);
        list = new JFXListView<>();

        // create the inner flow and content
        context = new ViewFlowContext();
        // set the default controller
        showFlow(new Flow(SpinnerController.class));
        controlPanelInteractor = new ControlPanelInteractorImpl(this);
        onNewDeviceList();
//      showFlow(new Flow(MapController.class).withLink(MapController.class, "back", SpinnerController.class));
    }


    private void handleError(Throwable throwable) {
        JFXSnackbar jfxSnackbar = new JFXSnackbar(rootPane);
        jfxSnackbar.show("Ошибка: " + throwable.getMessage(), 5000);

    }

    private void showFlow(Flow innerFlow) {
        flowHandler = innerFlow.createHandler(context);
        context.register("ContentFlowHandler", flowHandler);
        context.register("ContentFlow", innerFlow);
        final Duration containerAnimationDuration = Duration.millis(320);
        try {
            centerPane.getChildren().add(flowHandler.start(new AnimatedFlowContainer(Duration.millis(320), ContainerAnimations.ZOOM_OUT)));
        } catch (FlowException e) {
            e.printStackTrace();
        }
        if (flowHandler.getCurrentViewControllerClass().equals(MapController.class)
                || flowHandler.getCurrentViewControllerClass().equals(SpinnerController.class)) {
            back.setVisible(false);
            fadeIn.playFromStart();
        } else {
            back.setVisible(true);
            fadeIn.playFromStart();
        }
    }

    private void handleSuccess(ObservableList<Device> devices) {
        LOG.info("handleSuccess " + devices.size());

//        ObservableList<Device> deviceObservableList = FXCollections.observableArrayList();;
//        Device device = new Device();
//        device.setImei("asdasdas");
//        device.setModel("asdsa");
//        device.setVersion_os("android");
//        deviceObservableList.add(device);

        //// ...
        Platform.runLater(() -> {
            list.setItems(devices);
            list.setCellFactory(param -> new DeviceItemViewCell());
            JFXPopup popup = new JFXPopup(list);
            popup.setAutoFix( true );
            popup.setHideOnEscape( true );

            ripple_device_menu.setOnMouseClicked(e -> popup.show(device_menu, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT));

        });

    }

    @ActionMethod("update")
    private void update() {
        onNewDeviceList();
    }

    @ActionMethod("back")
    public void onBack() throws VetoException, FlowException {
        flowHandler.navigateTo(MapController.class);
        if (flowHandler.getCurrentViewControllerClass().equals(MapController.class)) {
            back.setVisible(false);
        }
    }

    private void onNewDeviceList(){
        LOG.info("onNewDeviceList");
        controlPanelInteractor.getNewDeviceList()
                .map(FXCollections::observableArrayList)
//                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
//                .observeOn(Schedulers.newThread())
                .subscribe(this::handleSuccess, this::handleError);
    }
}
