package gui.control_panel;

import data.model.Device;
import javafx.collections.ObservableList;

/**
 * Created by OldMan on 22.06.2017.
 */
public interface ControlPanelView {
    void setDeviceList(ObservableList<Device> devices);
    void showSnackBar(String message);
    void showDeviceMenu(boolean val);
    void showSpinner(boolean enable);
    void showMapFlow();
    void onDeviceInfo();
    void showDeviceFlow(Device device);
    void showErrorFlow();
    void onUpdateDeviceList();
}
