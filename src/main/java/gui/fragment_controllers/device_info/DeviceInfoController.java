package gui.fragment_controllers.device_info;

import com.jfoenix.controls.*;
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
import gui.fragment_controllers.SettingsController;
import gui.fragment_controllers.SpinnerController;
import gui.map.MapController;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.container.AnimatedFlowContainer;
import io.datafx.controller.flow.container.ContainerAnimations;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.apache.log4j.Logger;
import utils.RxBus;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by OldMan on 04.06.2017.
 */

@ViewController(value = "/fxml/device_info.fxml")
public class DeviceInfoController implements DeviceInfoView, MapComponentInitializedListener, MouseEventHandler {
    private static final Logger LOG = Logger.getLogger(DeviceInfoController.class);
    @Inject
    DeviceInfoPresenter deviceInfoPresenter;
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
    private Label model;
    @ViewNode
    private Label os;
    @ViewNode
    private Label imei;
    @ViewNode
    private GoogleMapView mapView;
    @ViewNode
    private JFXButton btnMoreDetail;
    private GoogleMap map;
    private Device device;
    private InfoWindow infoWindow;
    private FlowHandler flowHandler;
    @FXML
    private JFXDialog settingsDialog;


    @PostConstruct
    public void init() {
//        LOG.info("device info " + deviceInfoPresenter);
        mapView.addMapInializedListener(this);
        Objects.requireNonNull(context, "device");
        this.device = (Device) context.getRegisteredObject("device");
        model.setText(device.getModel());
        imei.setText(device.getImei());
        os.setText(device.getVersion_os());
        spinnerPane.setVisible(false);
        Injector.inject(this, Arrays.asList(new DeviceInfoModule(), new AppModule()));
        deviceInfoPresenter.setDeviceInfoView(this);
    }

    @Override
    public void mapInitialized() {
        MapOptions mapOptions = new MapOptions();
        LatLong point = new LatLong(device.getLongitude(), device.getLatitude());
        mapOptions.center(point)
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .mapTypeControl(false)
                .streetViewControl(false)
                .scrollWheel(true)
                .zoomControl(true)
                .zoom(10);
        map = mapView.createMap(mapOptions, false);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(point)
                .visible(true)
                .animation(Animation.DROP);
        map.addMarker(new Marker(markerOptions));
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
    @PreDestroy
    public void cleanup() {
        if (mapView != null)
            mapView.removeMapInitializedListener(this);
        if (map != null)
            map.clearMarkers();

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
    public void showSettings(Settings settings) throws IOException {
        LOG.info("showSettings " + settings + settingsDialog);
        Flow innerFlow = new Flow(SettingsController.class);
        flowHandler = innerFlow.createHandler(context);
        context.register("settings", settings);
        context.register("ContentInnerFlow", innerFlow);
        context.register("ContentInnerFlowHandler", flowHandler);
        final Duration containerAnimationDuration = Duration.millis(320);
        try {
            rootPane.getChildren().add(flowHandler.start(new AnimatedFlowContainer(Duration.millis(320), ContainerAnimations.ZOOM_OUT)));
        } catch (FlowException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showSpinner(boolean enable) {
        spinnerPane.setVisible(enable);
    }

    @Override
    public void showMapFlow(List<Location> lists) {
        Flow innerFlow = new Flow(MapController.class);
        flowHandler = innerFlow.createHandler(context);
        context.register("ContentInnerFlow", innerFlow);
        showFlow(flowHandler);
    }

    @Override
    public void showCallFlow(List<Call> lists) {
//        if (devicePopup.isShowing())
//            devicePopup.hide();
//        Flow innerFlow = new Flow(DeviceInfoController.class).withLink(DeviceInfoController.class, "back", MapController.class);
//        flowHandler = innerFlow.createHandler(context);
//        context.register("device", device);
//        context.register("ContentFlow", innerFlow);
//        showFlow(flowHandler);
    }


    private void showFlow(FlowHandler flowHandler) {
        context.register("ContentInnerFlowHandler", flowHandler);
        final Duration containerAnimationDuration = Duration.millis(320);
        try {
            centerPane.getChildren().add(flowHandler.start(new AnimatedFlowContainer(Duration.millis(320), ContainerAnimations.ZOOM_OUT)));
        } catch (FlowException e) {
            e.printStackTrace();
        }

//        if (flowHandler.getCurrentViewControllerClass().equals(MapController.class) ||
//                flowHandler.getCurrentViewControllerClass().equals(SpinnerController.class)) {
//            back.setVisible(false);
//            fadeInBack.playFromStart();
//        } else if (flowHandler.getCurrentViewControllerClass().equals(DeviceInfoController.class)) {
//            back.setVisible(true);
//            fadeInBack.playFromStart();
//        }
    }
}
