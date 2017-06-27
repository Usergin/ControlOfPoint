package gui.fragment_controllers.device_info;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXSnackbar;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.GMapMouseEvent;
import com.lynden.gmapsfx.javascript.event.MouseEventHandler;
import com.lynden.gmapsfx.javascript.object.*;
import dagger.Injector;
import dagger.application.AppModule;
import dagger.device_info.DeviceInfoModule;
import data.model.Device;
import data.remote.model.information.Call;
import data.remote.model.information.DeviceInfo;
import data.remote.model.information.Location;
import data.remote.model.information.Settings;
import data.remote.model.request.SettingsRequest;
import gui.fragment_controllers.CallController;
import gui.fragment_controllers.SettingsController;
import gui.fragment_controllers.map.BaseMapController;
import gui.fragment_controllers.map.DeviceMapController;
import gui.fragment_controllers.map.MainMapController;
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
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by OldMan on 04.06.2017.
 */

@ViewController(value = "/fxml/device_info.fxml")
public class DeviceInfoController implements DeviceInfoView, MouseEventHandler {
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
    private JFXButton btnMoreDetail;
    private GoogleMap map;
    private Device device;
    private InfoWindow infoWindow;
    private FlowHandler flowHandler;

    @PostConstruct
    public void init() {
//        LOG.info("device info " + deviceInfoPresenter);
        Injector.inject(this, Arrays.asList(new DeviceInfoModule(), new AppModule()));
//        mapView.addMapInializedListener(this);
        Objects.requireNonNull(context, "device");
        this.device = (Device) context.getRegisteredObject("device");
        LOG.info("showSettings" + device);
        model.setText(device.getModel());
        imei.setText(device.getImei());
        os.setText(device.getVersion_os());
        spinnerPane.setVisible(false);
        deviceInfoPresenter.setDeviceInfoView(this);
        showLastLocationMapFlow(new LatLong(device.getLongitude(), device.getLatitude()));
    }

//    @Override
//    public void mapInitialized() {
//        MapOptions mapOptions = new MapOptions();
//        LatLong point = new LatLong(device.getLongitude(), device.getLatitude());
//        mapOptions.center(point)
//                .mapType(MapTypeIdEnum.ROADMAP)
//                .overviewMapControl(false)
//                .panControl(false)
//                .rotateControl(false)
//                .scaleControl(false)
//                .mapTypeControl(false)
//                .streetViewControl(false)
//                .scrollWheel(true)
//                .zoomControl(true)
//                .zoom(10);
//        map = mapView.createMap(mapOptions, false);
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(point)
//                .visible(true)
//                .animation(Animation.DROP);
//        map.addMarker(new Marker(markerOptions));
//    }

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

    @FXML
    public void onClickCalls() {
        LOG.info("onClickMoreDetail " + deviceInfoPresenter);
        if (deviceInfoPresenter != null)
            deviceInfoPresenter.onDeviceCalls(device.getId());
    }

    @FXML
    public void onClickMessages() {
        LOG.info("onClickMoreDetail " + deviceInfoPresenter);
        if (deviceInfoPresenter != null)
            deviceInfoPresenter.onDeviceMessages(device.getId());
    }

    @FXML
    public void onClickContacts() {
        LOG.info("onClickMoreDetail " + deviceInfoPresenter);
        if (deviceInfoPresenter != null)
            deviceInfoPresenter.onDeviceContacts(device.getId());
    }

    @FXML
    public void onClickInstallApps() {
        LOG.info("onClickMoreDetail " + deviceInfoPresenter);
        if (deviceInfoPresenter != null)
            deviceInfoPresenter.onDeviceInstallApps(device.getId());
    }

    @FXML
    public void onClickLocation() {
        LOG.info("onClickMoreDetail " + deviceInfoPresenter);
        if (deviceInfoPresenter != null)
            deviceInfoPresenter.onDeviceLocations(device.getId());
    }

    @FXML
    public void onClickSystemEvent() {
        LOG.info("onClickMoreDetail " + deviceInfoPresenter);
        if (deviceInfoPresenter != null)
            deviceInfoPresenter.onDeviceServiceEvents(device.getId());
    }

    @FXML
    public void onClickSettings() {
        LOG.info("onClickMoreDetail " + deviceInfoPresenter);
        if (deviceInfoPresenter != null)
            deviceInfoPresenter.onDeviceSetting(device.getId());
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
        int size = rootPane.getChildren().size();
        if (size != 0 && rootPane.getChildren().get(size - 1).isVisible())
            rootPane.getChildren().remove(size - 1);
    }


    @Override
    public void onSaveNewSettings(Settings settings) {
        deviceInfoPresenter.setDeviceSettings(new SettingsRequest(device.getImei(), device.getId(), settings));
    }

    @Override
    public void showSpinner(boolean enable) {
        spinnerPane.setVisible(enable);
    }

    @Override
    public void showMapFlow(List<Location> list) {
        Flow innerFlow = new Flow(DeviceMapController.class);
        flowHandler = innerFlow.createHandler(context);
        context.register("ContentInnerFlow", innerFlow);
        context.register("ContentInnerFlowHandler", flowHandler);
        context.register("point_list", list);
        showFlow(flowHandler);
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
        Flow innerFlow = new Flow(CallController.class);
        flowHandler = innerFlow.createHandler(context);
        context.register("ContentInnerFlow", innerFlow);
        context.register("ContentInnerFlowHandler", flowHandler);
        context.register("call_list", list);
        try {
            centerPane.getChildren().add(flowHandler.start(new AnimatedFlowContainer(Duration.millis(320), ContainerAnimations.ZOOM_OUT)));
        } catch (FlowException e) {
            e.printStackTrace();
        }
    }


    private void showFlow(FlowHandler flowHandler) {
        context.register("ContentInnerFlowHandler", flowHandler);
        final Duration containerAnimationDuration = Duration.millis(320);
        try {
            centerPane.getChildren().add(flowHandler.start(new AnimatedFlowContainer(Duration.millis(320), ContainerAnimations.ZOOM_OUT)));
        } catch (FlowException e) {
            e.printStackTrace();
        }

//        if (flowHandler.getCurrentViewControllerClass().equals(MainMapController.class) ||
//                flowHandler.getCurrentViewControllerClass().equals(SpinnerController.class)) {
//            back.setVisible(false);
//            fadeInBack.playFromStart();
//        } else if (flowHandler.getCurrentViewControllerClass().equals(DeviceInfoController.class)) {
//            back.setVisible(true);
//            fadeInBack.playFromStart();
//        }
    }
}
