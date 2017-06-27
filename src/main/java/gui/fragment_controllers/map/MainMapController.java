package gui.fragment_controllers.map;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.event.GMapMouseEvent;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import data.model.Device;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;
import utils.RxBus;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by OldMan on 04.06.2017.
 */

@ViewController(value = "/fxml/map.fxml")
public class MainMapController extends BaseMapController implements MainMapView {
    private static final Logger LOG = Logger.getLogger(MainMapController.class);
    @ViewNode
    private GoogleMapView mapView;
    private ObservableList<Device> devicesData;
    private GoogleMap map;
    private InfoWindow infoWindow;
    private List<Marker> markerList = new ArrayList<>();

    @Override
    public void mapInitialized() {
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
        setContent();
    }

    @Override
    public void setContent() {
        RxBus.instanceOf().getDeviceList().subscribe(this::setMarkers);
        RxBus.instanceOf().getShortDeviceInfo().subscribe(deviceConsumer -> {
            mapView.panBy(deviceConsumer.getLatitude(), deviceConsumer.getLongitude());
            Marker marker = markerList.stream().filter(marker1 -> marker1.getTitle().contains(deviceConsumer.getImei())).findFirst().get();
            showInfo(marker);
        });
    }

    @PreDestroy
    public void cleanup() {
        if (mapView != null)
            mapView.removeMapInitializedListener(this);
        if (map != null)
            map.clearMarkers();

    }

    @Override
    public void handle(GMapMouseEvent mouseEvent) {
        LOG.info("latLong " + mouseEvent);
    }

    @Override
    public void showShortDeviceInfo(Device device) {
        mapView.panBy(device.getLatitude(), device.getLongitude());
        Marker marker = markerList.stream().filter(marker1 -> marker1.getTitle().contains(device.getImei())).findFirst().get();
        showInfo(marker);
    }

    private void setMarkers(ObservableList<Device> devicesData) {
        LOG.info("setMarkers" + devicesData);
        if (mapView != null)
            mapView.getMap().clearMarkers();
        for (Device device : devicesData) {
            LatLong point = new LatLong(device.getLongitude(), device.getLatitude());
            MarkerOptions markerOptions = new MarkerOptions();

            markerOptions.position(point)
                    .title("<font color=\"#009688\"><b>" + device.getModel() + "</b></font>" + "<br>" + device.getImei() + "<br>" + device.getVersion_os()
                            + "<br>id:" + device.getId())
                    .visible(true)
                    .animation(Animation.DROP);

            Marker marker = new Marker(markerOptions);
            markerList.add(marker);
            map.addUIEventHandler(marker, UIEventType.click, jsObject -> showInfo(marker));
            map.addUIEventHandler(marker, UIEventType.dblclick, jsObject -> onDeviceInfo(marker));
        }
        map.addMarkers(markerList);
    }

    private void onDeviceInfo(Marker marker) {
        String s = marker.getTitle();
        RxBus.instanceOf().setDeviceInfo(marker.getTitle().substring(s.indexOf("id:") + 3, s.length()));
    }

    private void showInfo(Marker marker) {
        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
        infoWindowOptions.content(marker.getTitle());
        if (infoWindow != null)
            infoWindow.close();
        infoWindow = new InfoWindow(infoWindowOptions);
        infoWindow.open(map, marker);
    }


}
