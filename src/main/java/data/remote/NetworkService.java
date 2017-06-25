package data.remote;

import data.remote.model.request.Authentication;
import data.remote.model.request.SettingsRequest;
import data.remote.model.response.*;
import io.reactivex.Single;
import main.ControlOfPointMain;
import org.apache.log4j.Logger;

import javax.inject.Inject;

/**
 * Created by OldMan on 18.06.2017.
 */
public class NetworkService {

    @Inject
    ServerApi serverApi;
    private static final Logger LOG = Logger.getLogger(ControlOfPointMain.class);

    public NetworkService(ServerApi serverApi) {
        this.serverApi = serverApi;
    }


    /**
     * Get user - login&password
     */
    public Single<UserResponse> getUser(Authentication authentication) {
        LOG.info(authentication.getLogin() + authentication.getPassword());
        return serverApi.getUser(authentication);
    }

    public Single<DeviceListResponse> getDeviceList() {
        LOG.info("DeviceListResponse");
        return serverApi.getDevices();
    }

    public Single<DeviceResponse> getDeviceById(int id) {
        LOG.info("DeviceResponse");
        return serverApi.getDeviceById(id);
    }

    public Single<StatusResponse> rmDeviceById(int id) {
        LOG.info("StatusResponse");
        return serverApi.rmDeviceById(id);
    }

    public Single<StatusResponse> rmAllDevice() {
        LOG.info("StatusResponse");
        return serverApi.rmAllDevice();
    }

    public Single<DeviceInfoResponse> getDeviceInfo(int id) {
        LOG.info("DeviceInfoResponse");
        return serverApi.getDeviceInfoById(id);
    }

    public Single<SettingsResponse> getDeviceSettings(int id) {
        LOG.info("SettingsResponse");
        return serverApi.getDeviceSettings(id);
    }

    public Single<StatusResponse> setDeviceSettings(SettingsRequest settingsRequest) {
        LOG.info("StatusResponse");
        return serverApi.setDeviceSettings(settingsRequest);
    }

    public Single<InstallAppResponse> getDeviceInstallApps(int id) {
        LOG.info("InstallAppResponse");
        return serverApi.getDeviceInstallApps(id);
    }

    public Single<BatteryEventResponse> getDeviceBatteryEvents(int id) {
        LOG.info("BatteryEventResponse");
        return serverApi.getDeviceBatteryEvent(id);
    }

    public Single<CallResponse> getDeviceCalls(int id) {
        LOG.info("CallResponse");
        return serverApi.getDeviceCalls(id);
    }

    public Single<ContactResponse> getDeviceContacts(int id) {
        LOG.info("ContactResponse");
        return serverApi.getDeviceContacts(id);
    }

    public Single<DeviceEventResponse> getDeviceEvents(int id) {
        LOG.info("DeviceEventResponse");
        return serverApi.getDeviceStatus(id);
    }

    public Single<LocationResponse> getDeviceLocations(int id) {
        LOG.info("LocationResponse");
        return serverApi.getDeviceLocations(id);
    }

    public Single<MessageResponse> getDeviceMessages(int id) {
        LOG.info("MessageResponse");
        return serverApi.getDeviceMessages(id);
    }

    public Single<NetworkEventResponse> getDeviceNetworkEvents(int id) {
        LOG.info("NetworkEventResponse");
        return serverApi.getDeviceNetworkEvents(id);
    }

    public Single<ServiceEventResponse> getDeviceServiceEvents(int id) {
        LOG.info("ServiceEventResponse");
        return serverApi.getDeviceServiceEvents(id);
    }
}
