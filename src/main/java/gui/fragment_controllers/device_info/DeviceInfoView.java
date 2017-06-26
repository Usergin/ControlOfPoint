package gui.fragment_controllers.device_info;

import data.model.Device;
import data.remote.model.information.Call;
import data.remote.model.information.DeviceInfo;
import data.remote.model.information.Location;
import data.remote.model.information.Settings;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

/**
 * Created by OldMan on 25.06.2017.
 */
public interface DeviceInfoView {
    void showSpinner(boolean b);
    void showMapFlow(List<Location> lists);
    void showCallFlow(List<Call> lists);
    void showSnackBar(String s);
    void showDeviceInfoPopup(DeviceInfo deviceInfo);
    void showSettings(Settings settings) throws IOException;
}
