package gui.fragment_controllers;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;

import javax.annotation.PostConstruct;

/**
 * Created by OldMan on 04.06.2017.
 */

@ViewController(value = "/fxml/map.fxml", title = "Material Design Example")
public class MapController {
    @FXML
    private MapView mapView;

    @PostConstruct
    public void init() {
        ArcGISMap map = new ArcGISMap(Basemap.Type.TOPOGRAPHIC, 59.961697, 30.278961, 10);

//        // create the MapView
        mapView.setMap(map);
//        StackPane stackPane = new StackPane();
//        stackPane.getChildren().add(mapView);
    }
}
