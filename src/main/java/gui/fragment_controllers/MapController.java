package gui.fragment_controllers;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.GMapMouseEvent;
import com.lynden.gmapsfx.javascript.event.MouseEventHandler;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.util.MarkerImageFactory;
import data.model.Device;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.util.Objects;

/**
 * Created by OldMan on 04.06.2017.
 */

@ViewController(value = "/fxml/map.fxml")
public class MapController implements MapComponentInitializedListener, MouseEventHandler {
    private static final Logger LOG = Logger.getLogger(MapController.class);


    @FXMLViewFlowContext
    @Inject
    ViewFlowContext context;
    //    @ViewNode
//    private StackPane root;
    @ViewNode
    private GoogleMapView mapView;
    private ObservableList<Device> devicesData;
    private GoogleMap map;
    private  InfoWindow infoWindow;
    @PostConstruct
    public void init() {
        mapView.addMapInializedListener(this);
        Objects.requireNonNull(context, "devices");
        this.devicesData = (ObservableList<Device>) context.getRegisteredObject("devices");
    }

    @PreDestroy
    public void cleanup() {
        if (mapView != null) {
            mapView.removeMapInitializedListener(this);
            map.clearMarkers();
        }
    }

    @Override
    public void mapInitialized() {
        MapOptions mapOptions = new MapOptions();
        mapOptions.center(new LatLong(59.993831, 30.254757))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .mapTypeControl(false)
                .streetViewControl(true)
                .zoomControl(true)
                .zoom(10);
        map = mapView.createMap(mapOptions, false);
        for (Device device : devicesData) {
            LatLong point = new LatLong(device.getLongitude(), device.getLatitude());
            LOG.info("mapInitialized " + point);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(point)
                    .title("<font color=\"#009688\"><b>" +device.getModel() +"</b></font>" + "<br>"  + device.getImei() + "<br>" + device.getVersion_os())
                    .visible(true)
//                    .icon(MarkerImageFactory.createMarkerImage("/drawables/navigation.png", "png"))
                    .animation(Animation.DROP);

            Marker marker = new Marker(markerOptions);
            map.addUIEventHandler(marker, UIEventType.click, jsObject -> showInfo(marker));
            map.addUIEventHandler(marker, UIEventType.dblclick, jsObject -> onDeviceInfo(marker));
            map.addMarker(marker);
        }
    }

    @Override
    public void handle(GMapMouseEvent mouseEvent) {
        LOG.info("latLong " + mouseEvent);
    }

    private void showInfo(Marker marker) {
        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
        infoWindowOptions.content(marker.getTitle());
        if(infoWindow != null)
            infoWindow.close();
        infoWindow = new InfoWindow(infoWindowOptions);
        infoWindow.open(map, marker);
    }

    private void onDeviceInfo(Marker marker) {
        LOG.info("onDeviceInfo " + marker);

    }

}
