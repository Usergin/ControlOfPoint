package gui.fragment_controllers.map;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.util.MarkerImageFactory;
import data.model.Device;
import data.remote.model.information.Location;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by oldman on 27.06.17.
 */

@ViewController(value = "/fxml/map.fxml")
public class DeviceMapController extends BaseMapController {
    private static final Logger LOG = Logger.getLogger(MainMapController.class);
    @FXMLViewFlowContext
    ViewFlowContext context;
    @ViewNode
    private GoogleMapView mapView;
    private GoogleMap map;
    private InfoWindow infoWindow;
    private List<Marker> markerList = new ArrayList<>();

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
        setMarkers(latLongList);
    }

    private void setMarkers(List<Location> locationList) {
        LOG.info("setMarkers" + locationList);
        if (mapView != null)
            mapView.getMap().clearMarkers();
        for (Location location : locationList) {
            LatLong point = new LatLong(location.getLongitude(), location.getLatitude());
            MarkerOptions markerOptions = new MarkerOptions();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            try {
                sdf.parse(location.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            markerOptions.position(point)
                    .title("<font color=\"#009688\"><b>" + location.getDate() + "</b></font>" + "<br>" + location.getMethod() + "<br>" + location.getAccuracy())
                    .visible(true)
                    .animation(Animation.DROP);

            Marker marker = new Marker(markerOptions);
            markerList.add(marker);
            map.addUIEventHandler(marker, UIEventType.click, jsObject -> showInfo(marker));
       }
        map.addMarkers(markerList);
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
