package gui.fragment_controllers.device_info;

import data.model.information.*;

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

    void showMessageFlow(List<Message> messages);

    void showContactFlow(List<Contact> contacts);

    void showInstallAppFlow(List<InstallApp> installApps);

    void showBatteryEventFlow(List<BatteryEvent> batteryEvents);

    void showDeviceStatusFlow(List<DeviceEvent> deviceEvents);

    void showNetworkEventFlow(List<NetworkEvent> networkEvents);

    void showServiceEventFlow(List<ServiceEvent> serviceEvents);
}
