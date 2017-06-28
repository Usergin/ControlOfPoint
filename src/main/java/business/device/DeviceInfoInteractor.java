package business.device;

import data.model.information.*;
import data.remote.model.request.SettingsRequest;
import data.remote.model.response.StatusResponse;
import io.reactivex.Single;

import java.util.List;

/**
 * Created by OldMan on 25.06.2017.
 */
public interface DeviceInfoInteractor {

    Single<StatusResponse> setDeviceSettings(SettingsRequest settingsRequest);
    Single<StatusResponse> rmDevice(int id);
    Single<DeviceInfo> getDeviceInfo(int id);
    Single<Settings> getDeviceSettings(int id);
    Single<List<Call>> getDeviceCalls(int id);
    Single<List<Contact>> getDeviceContacts(int id);
    Single<List<DeviceEvent>> getDeviceEvents(int id);
    Single<List<BatteryEvent>> getDeviceBatteryEvents(int id);
    Single<List<InstallApp>> getDeviceInstallApps(int id);
    Single<List<Location>> getDeviceLocations(int id);
    Single<List<Message>> getDeviceMessages(int id);
    Single<List<NetworkEvent>> getDeviceNetworkEvents(int id);
    Single<List<ServiceEvent>> getDeviceServiceEvents(int id);
}
