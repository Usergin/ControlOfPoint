package gui.fragment_controllers.map;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.object.*;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;

import java.util.Objects;

/**
 * Created by oldman on 27.06.17.
 */
@ViewController(value = "/fxml/map.fxml")
public class MiniMapController extends BaseMapController{
    @FXMLViewFlowContext
    ViewFlowContext context;
    @ViewNode
    private GoogleMapView mapView;
    private GoogleMap map;

    @Override
    public void mapInitialized() {
        Objects.requireNonNull(context, "devices");
        double longitude = (double) context.getRegisteredObject("longitude");
        double latitude = (double) context.getRegisteredObject("latitude");
        MapOptions mapOptions = new MapOptions();
        mapOptions.center(new LatLong(latitude, longitude))
                .mapType(MapTypeIdEnum.HYBRID)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .mapTypeControl(false)
                .streetViewControl(false)
                .scrollWheel(true)
                .zoomControl(true)
                .zoom(9);
        map = mapView.createMap(mapOptions, false);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position((new LatLong(latitude, longitude)))
                .visible(true)
                .animation(Animation.DROP);
        map.addMarker(new Marker(markerOptions));

    }
}
