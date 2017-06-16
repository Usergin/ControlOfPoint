package gui.fragment_controllers;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by OldMan on 04.06.2017.
 */

@ViewController(value = "/fxml/map.fxml", title = "Material Design Example")
public class  MapController{
//    @FXMLViewFlowContext
//    private ViewFlowContext context;

//        @FXML
//    private StackPane stackPane;
//
    @FXML
    private MapView mapView;
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        ArcGISMap map = new ArcGISMap(Basemap.Type.NATIONAL_GEOGRAPHIC, 56.075844, -2.681572, 10);
//        // create the MapView
//        mapView.setMap(map);
//        StackPane stackPane = new StackPane();
//        stackPane.getChildren().add(mapView);
//
//    }
    @PostConstruct
    public void init() {
        ArcGISMap map = new ArcGISMap(Basemap.Type.TOPOGRAPHIC, 59.961697, 30.278961, 10);

//        // create the MapView
        mapView.setMap(map);
//        StackPane stackPane = new StackPane();
//        stackPane.getChildren().add(mapView);
    }
}
