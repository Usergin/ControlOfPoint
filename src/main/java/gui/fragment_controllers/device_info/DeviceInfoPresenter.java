package gui.fragment_controllers.device_info;

import data.remote.model.request.SettingsRequest;

/**
 * Created by OldMan on 25.06.2017.
 */
public interface DeviceInfoPresenter {
    void setDeviceInfoView(DeviceInfoView deviceInfoView);

    void setDeviceSettings(SettingsRequest request);
    void rmDevice(int id);
    void onDeviceInfo(int id);
    void onDeviceSetting(int id);
    void onDeviceCalls(int id);
    void onDeviceContacts(int id);
    void onDeviceEvents(int id);
    void onDeviceBatteryEvents(int id);
    void onDeviceInstallApps(int id);
    void onDeviceLocations(int id);
    void onDeviceMessages(int id);
    void onDeviceNetworkEvents(int id);
    void onDeviceServiceEvents(int id);
}
