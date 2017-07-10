package gui.fragment_controllers.device_info;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXSnackbar;
import com.lynden.gmapsfx.javascript.event.GMapMouseEvent;
import com.lynden.gmapsfx.javascript.event.MouseEventHandler;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.LatLong;
import dagger.Injector;
import dagger.application.AppModule;
import dagger.device_info.DeviceInfoModule;
import data.model.Device;
import data.model.information.*;
import data.remote.model.request.SettingsRequest;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import gui.fragment_controllers.*;
import gui.fragment_controllers.map.ContactController;
import gui.fragment_controllers.map.DeviceMapController;
import gui.fragment_controllers.map.MiniMapController;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.FlowView;
import io.datafx.controller.flow.container.AnimatedFlowContainer;
import io.datafx.controller.flow.container.ContainerAnimations;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by OldMan on 04.06.2017.
 */

@ViewController(value = "/fxml/device_info.fxml")
public class DeviceInfoController implements DeviceInfoView, EventHandler<MouseEvent>, MouseEventHandler {
    private static final Logger LOG = Logger.getLogger(DeviceInfoController.class);
    @Inject
    DeviceInfoPresenter deviceInfoPresenter;
    @FXMLViewFlowContext
    ViewFlowContext context;
    @ViewNode
    private StackPane rootPane;
    @ViewNode
    private StackPane centerPane;
    @ViewNode
    private StackPane mapRoot;
    @ViewNode
    private StackPane spinnerPane;
    @ViewNode
    private Label model;
    @ViewNode
    private Label os;
    @ViewNode
    private Label imei;
    @ViewNode
    private JFXListView<?> menuList;
    @ViewNode
    private FontAwesomeIconView btnCogs;
    @ViewNode
    private JFXButton btnMoreDetail;
    private GoogleMap map;
    private Device device;
    private InfoWindow infoWindow;
    private FlowHandler flowHandler;
    private JFXListView<String> menuServiceEvent;
    private int lastSelectedMenu = -1;

    @PostConstruct
    public void init() {
        Injector.inject(this, Arrays.asList(new DeviceInfoModule(), new AppModule()));
        Objects.requireNonNull(context, "device");
        this.device = (Device) context.getRegisteredObject("device");
        menuServiceEvent = new JFXListView<>();
        menuServiceEvent.getItems().add("Батарея");
        menuServiceEvent.getItems().add("Статус устройства");
        menuServiceEvent.getItems().add("Сеть ");
        menuServiceEvent.getItems().add("Остальное");
        menuServiceEvent.setOnMouseClicked(this);
        model.setText(device.getModel());
        imei.setText(device.getImei());
        os.setText(device.getVersion_os());
        spinnerPane.setVisible(false);
        deviceInfoPresenter.setDeviceInfoView(this);
        if (device.getLongitude() != null && device.getLatitude() != null)
            showLastLocationMapFlow(new LatLong(device.getLongitude(), device.getLatitude()));
    }

    @Override
    public void handle(GMapMouseEvent mouseEvent) {
        LOG.info("latLong " + mouseEvent);
    }

    @FXML
    public void onClickMoreDetail() {
        LOG.info("onClickMoreDetail " + deviceInfoPresenter);
        if (deviceInfoPresenter != null)
            deviceInfoPresenter.onDeviceInfo(device.getId());
    }

    @Override
    public void showSnackBar(String message) {
        JFXSnackbar jfxSnackbar = new JFXSnackbar(rootPane);
        jfxSnackbar.show(message, 5000);

    }

    @Override
    public void showDeviceInfoPopup(DeviceInfo deviceInfo) {
        LOG.info("deviceinfo" + deviceInfo.getBrand());
        JFXListView<String> list = new JFXListView<>();
        for (Field field : deviceInfo.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            StringBuilder result = new StringBuilder();
            try {
                result.append(field.getName()).append(": ").append(field.get(deviceInfo));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            list.getItems().add(result.toString());
        }
        JFXPopup devicePopup = new JFXPopup(list);
        devicePopup.setAutoFix(true);
        devicePopup.setHideOnEscape(true);
        devicePopup.show(btnMoreDetail, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
    }

    @Override
    public void showSettingsView(Settings settings) {
        Flow innerFlow = new Flow(SettingsController.class);
        flowHandler = innerFlow.createHandler(context);

        try {
            rootPane.getChildren().add(flowHandler.start(new AnimatedFlowContainer(Duration.millis(320), ContainerAnimations.ZOOM_OUT)));
        } catch (FlowException e) {
            e.printStackTrace();
        }
        FlowView view = flowHandler.getCurrentView();
        SettingsController settingsController = (SettingsController) view.getViewContext().getController();
        settingsController.init(this, settings);
    }

    @Override
    public void closeCurrentView() {
        if (menuList != null)
            menuList.getSelectionModel().select(lastSelectedMenu);
        int size = rootPane.getChildren().size();
        if (size != 0 && rootPane.getChildren().get(size - 1).isVisible())
            rootPane.getChildren().remove(size - 1);
    }


    @Override
    public void onSaveNewSettings(Settings settings) {
        if (deviceInfoPresenter != null) {
            deviceInfoPresenter.setDeviceSettings(new SettingsRequest(device.getImei(), device.getId(), settings));
        }
    }

    @Override
    public void showMessageFlow(List<Message> messages) {
        context.register("sms_list", messages);
        showFlow(new Flow(MessageController.class));
    }

    @Override
    public void showContactFlow(List<Contact> contacts) {
        context.register("contact_list", contacts);
        showFlow(new Flow(ContactController.class));
    }

    @Override
    public void showInstallAppFlow(List<InstallApp> installApps) {
        context.register("apps_list", installApps);
        showFlow(new Flow(InstallAppController.class));

    }

    @Override
    public void showBatteryEventFlow(List<BatteryEvent> batteryEvents) {
        context.register("battery_list", batteryEvents);
        showFlow(new Flow(BatteryController.class));
    }

    @Override
    public void showDeviceStatusFlow(List<DeviceEvent> deviceEvents) {
        context.register("device_events_list", deviceEvents);
        showFlow(new Flow(DeviceStatusController.class));
    }

    @Override
    public void showNetworkEventFlow(List<NetworkEvent> networkEvents) {
        context.register("network_events_list", networkEvents);
        showFlow(new Flow(NetworkEventController.class));
    }

    @Override
    public void showServiceEventFlow(List<ServiceEvent> serviceEvents) {
        context.register("service_events_list", serviceEvents);
        showFlow(new Flow(ServiceEventController.class));

    }

    @Override
    public void showSpinner(boolean enable) {
        spinnerPane.setVisible(enable);
    }

    @Override
    public void showMapFlow(List<Location> list) {
        context.register("point_list", list);
        showFlow(new Flow(DeviceMapController.class));
    }

    //    @Override
    public void showLastLocationMapFlow(LatLong point) {
        Flow innerFlow = new Flow(MiniMapController.class);
        flowHandler = innerFlow.createHandler(context);
        context.register("ContentInnerFlow", innerFlow);
        context.register("ContentInnerFlowHandler", flowHandler);
        context.register("latitude", point.getLatitude());
        context.register("longitude", point.getLongitude());
        try {
            mapRoot.getChildren().add(flowHandler.start(new AnimatedFlowContainer(Duration.millis(320), ContainerAnimations.ZOOM_OUT)));
        } catch (FlowException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showCallFlow(List<Call> list) {
        context.register("call_list", list);
        showFlow(new Flow(CallController.class));
    }


    private void showFlow(Flow innerFlow) {
        flowHandler = innerFlow.createHandler(context);
        context.register("ContentInnerFlow", innerFlow);
        context.register("ContentInnerFlowHandler", flowHandler);
        try {
            centerPane.getChildren().clear();
            centerPane.getChildren().add(flowHandler.start(new AnimatedFlowContainer(Duration.millis(320), ContainerAnimations.ZOOM_OUT)));
        } catch (FlowException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void handleMenuList(MouseEvent event) {
        switch (menuList.getSelectionModel().getSelectedIndex()) {
            case 0:
                lastSelectedMenu = 0;
                if (deviceInfoPresenter != null)
                    deviceInfoPresenter.onDeviceCalls(device.getId());
                break;
            case 1:
                lastSelectedMenu = 1;
                if (deviceInfoPresenter != null)
                    deviceInfoPresenter.onDeviceMessages(device.getId());
                break;
            case 2:
                lastSelectedMenu = 2;
                if (deviceInfoPresenter != null)
                    deviceInfoPresenter.onDeviceContacts(device.getId());
                break;
            case 3:
                lastSelectedMenu = 3;
                if (deviceInfoPresenter != null)
                    deviceInfoPresenter.onDeviceInstallApps(device.getId());
                break;
            case 4:
                lastSelectedMenu = 4;
                if (deviceInfoPresenter != null)
                    deviceInfoPresenter.onDeviceLocations(device.getId());
                break;
            case 5:
                lastSelectedMenu = 5;
                if (deviceInfoPresenter != null) {
                    JFXPopup devicePopup = new JFXPopup(menuServiceEvent);
                    devicePopup.setAutoFix(true);
                    devicePopup.setHideOnEscape(true);
                    devicePopup.show(menuList, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT,
                            (menuList.getWidth() / 1.3),
                            (menuList.getHeight() - menuList.getPrefHeight()) / 2);
                }
                break;
            case 6:
                if (deviceInfoPresenter != null)
                    deviceInfoPresenter.onDeviceSetting(device.getId());
                break;
        }

    }

    @Override
    public void handle(MouseEvent event) {
        LOG.info("onClickMoreDetail " + menuServiceEvent.getSelectionModel().getSelectedIndex());
        switch (menuServiceEvent.getSelectionModel().getSelectedIndex()) {
            case 0:
                if (deviceInfoPresenter != null)
                    deviceInfoPresenter.onDeviceBatteryEvents(device.getId());
                break;
            case 1:
                if (deviceInfoPresenter != null)
                    deviceInfoPresenter.onDeviceEvents(device.getId());
                break;
            case 2:
                if (deviceInfoPresenter != null)
                    deviceInfoPresenter.onDeviceNetworkEvents(device.getId());
                break;
            case 3:
                if (deviceInfoPresenter != null)
                    deviceInfoPresenter.onDeviceServiceEvents(device.getId());
                break;
        }
    }
}
