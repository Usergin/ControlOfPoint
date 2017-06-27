package gui.fragment_controllers.map;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.GMapMouseEvent;
import com.lynden.gmapsfx.javascript.event.MouseEventHandler;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by oldman on 27.06.17.
 */


@ViewController(value = "/fxml/map.fxml")
public abstract class BaseMapController implements MapComponentInitializedListener, MouseEventHandler {
    @ViewNode
    private GoogleMapView mapView;

    @Override
    abstract public void mapInitialized();

    public void setContent(){}

    @PostConstruct
    public void init(){
        mapView.addMapInializedListener(this);
    }

    @PreDestroy
    public void cleanup(){
        if (mapView != null)
            mapView.removeMapInitializedListener(this);

    }

    @Override
    public void handle(GMapMouseEvent mouseEvent){}
}
