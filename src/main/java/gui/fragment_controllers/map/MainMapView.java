package gui.fragment_controllers.map;

import com.sun.deploy.panel.ControlPanel;
import data.model.Device;
import gui.control_panel.ControlPanelView;
import javafx.collections.ObservableList;

/**
 * Created by OldMan on 24.06.2017.
 */
public interface MainMapView {
   void showShortDeviceInfo(Device device);
}
