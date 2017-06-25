package gui.fragment_controllers;

import data.model.Device;
import javafx.collections.ObservableList;

/**
 * Created by OldMan on 24.06.2017.
 */
public interface MapView {
    void setMarkers( ObservableList<Device> devicesData);

}
