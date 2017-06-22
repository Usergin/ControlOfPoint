package gui.fragment_controllers;

import io.datafx.controller.ViewController;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

/**
 * Created by OldMan on 04.06.2017.
 */

@ViewController(value = "/fxml/map.fxml")
public class MapController {
    private static final Logger LOG = Logger.getLogger(MapController.class);


    @FXMLViewFlowContext
    @Inject
    ViewFlowContext context;
    @FXML
    private WebView mapView;
//    private ObservableList<Device> devicesData;
//    private GraphicsOverlay graphicsOverlay;

    @PostConstruct
    public void init() {
        // create web engine and view
        mapView = new WebView();
        final WebEngine webEngine = mapView.getEngine();
//        Platform.runLater(() -> {
//            webEngine.load(getClass().getResource("html/map.html").toExternalForm());
//        });
//        Objects.requireNonNull(context, "devices");
//        this.devicesData = (ObservableList<Device>) context.getRegisteredObject("devices");
//        LOG.info("devices" + devicesData.size());
//        ArcGISMap map = new ArcGISMap(Basemap.Type.TOPOGRAPHIC, 59.961697, 30.278961, 10);
//        // create spatial reference for WGS 1948
//        graphicsOverlay = new GraphicsOverlay();
//        mapView.getGraphicsOverlays().add(graphicsOverlay);
//
//        PictureMarkerSymbol positionMarker = new PictureMarkerSymbol(new Image("/drawables/navigation.png"));
//        final SpatialReference webMercator = SpatialReferences.getWgs84();
//
//        for (Device device : devicesData) {
//            Point point = new Point(device.getLatitude(),device.getLongitude(),  webMercator);
//
//            LOG.info("devices" + point.getX());
//            placePictureMarkerSymbol(positionMarker, point);
//        }
////        map.getItem().
//        mapView.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                event.setDragDetect(true);
//                LOG.info("event" + event);
//
//            }
//        });
//        mapView.setMap(map);
    }

    @PreDestroy
    public void cleanup() {
//        System.out.println("Cleaning up!");
//        if (mapView != null) {
//            mapView.dispose();
//        }
    }

    /**
     * Adds a Graphic to the Graphics Overlay using a Point and a Picture Marker
     * Symbol.
     *
     * @param markerSymbol PictureMarkerSymbol to be used
     * @param graphicPoint where the Graphic is going to be placed
     */
//    private void placePictureMarkerSymbol(PictureMarkerSymbol markerSymbol, Point graphicPoint) {
//
//        // set size of the image
//        markerSymbol.setHeight(40);
//        markerSymbol.setWidth(40);
//
//        // load symbol asynchronously
//        markerSymbol.loadAsync();
//
//        // add to the graphic overlay once done loading
//        markerSymbol.addDoneLoadingListener(() -> {
//            Map<String, Object> attributes = new HashMap<String, Object>();
//             attributes.put("Added by", "Vijay");
//            attributes.put("Description", "asd");
//            Graphic symbolGraphic = new Graphic(graphicPoint, attributes, markerSymbol);
//            graphicsOverlay.getGraphics().add(symbolGraphic);
//        });
//
//    }
}
