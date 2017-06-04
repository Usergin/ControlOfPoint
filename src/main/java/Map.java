import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by OldMan on 04.06.2017.
 */
public class Map implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private MapView mapView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArcGISMap map = new ArcGISMap(Basemap.Type.NATIONAL_GEOGRAPHIC, 56.075844, -2.681572, 10);

        // create the MapView
        mapView.setMap(map);
//        StackPane stackPane = new StackPane();
//        stackPane.getChildren().add(mapView);

    }
}
