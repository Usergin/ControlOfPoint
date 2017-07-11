package gui.fragment_controllers.map;

import com.jfoenix.controls.JFXDatePicker;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import data.model.information.Location;
import gui.fragment_controllers.device_info.DeviceInfoController;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.reactivex.Single;
import javafx.scene.paint.Color;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by oldman on 27.06.17.
 */

@ViewController(value = "/fxml/map.fxml")
public class DeviceMapController extends BaseMapController {
    private static final Logger LOG = Logger.getLogger(DeviceMapController.class);
    @FXMLViewFlowContext
    ViewFlowContext context;
    @ViewNode
    private GoogleMapView mapView;
    private GoogleMap map;
    private InfoWindow infoWindow;
    private List<Marker> markerList = new ArrayList<>();
    JFXDatePicker datePicker;
    private DeviceInfoController deviceController;

    @Override
    public void mapInitialized() {
        Objects.requireNonNull(context, "devices");
        List<Location> latLongList = (List<Location>) context.getRegisteredObject("point_list");
        MapOptions mapOptions = new MapOptions();
        mapOptions.center(new LatLong(59.993831, 30.254757))
                .mapType(MapTypeIdEnum.HYBRID)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .mapTypeControl(false)
                .streetViewControl(false)
                .scrollWheel(true)
                .zoomControl(true)
                .zoom(7);
        map = mapView.createMap(mapOptions, false);
        if (latLongList.size() == 0)
            setMarkers(latLongList);
        datePicker = new JFXDatePicker();
        datePicker.setDefaultColor(Color.valueOf("#03A9F4"));

        datePicker.setOnAction(event -> deviceController.handleActionEvent(event));
        datePicker.setPrefWidth(20);
        mapView.getChildren().add(datePicker);
    }

    public Single setMarkers(List<Location> locationList) {
       if (mapView != null) {
            mapView.getMap().clearMarkers();
            map.clearMarkers();
            markerList.clear();
        }
        if (locationList != null) {
            for (Location location : locationList) {
                LatLong point = new LatLong(location.getLongitude(), location.getLatitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(point)
                        .title("<font color=\"#009688\"><b>" + location.getFormatDate() + "</b></font>" + "<br>" + location.getMethod() + "<br>" + location.getAccuracy())
                        .visible(true)
                        .animation(Animation.DROP);

                Marker marker = new Marker(markerOptions);
                markerList.add(marker);
                map.addUIEventHandler(marker, UIEventType.click, jsObject -> showInfo(marker));
            }
            map.addMarkers(markerList);
        }
        return Single.just(true);
    }

    private void showInfo(Marker marker) {
        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
        infoWindowOptions.content(marker.getTitle());
        if (infoWindow != null)
            infoWindow.close();
        infoWindow = new InfoWindow(infoWindowOptions);
        infoWindow.open(map, marker);
    }

    public void setDeviceController(DeviceInfoController deviceController) {
        this.deviceController = deviceController;
    }

}
