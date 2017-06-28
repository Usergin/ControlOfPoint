package gui.fragment_controllers.device_info;

import data.model.information.Call;
import data.model.information.DeviceInfo;
import data.model.information.Location;
import data.model.information.Settings;

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
    void showSettingsView(Settings settings);
    void closeCurrentView();
    void onSaveNewSettings(Settings settings);
}
