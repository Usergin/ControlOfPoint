package business.device;

import data.local.parser.Parser;
import data.remote.NetworkService;
import data.model.information.*;
import data.remote.model.request.SettingsRequest;
import data.remote.model.response.*;
import io.reactivex.Single;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by OldMan on 25.06.2017.
 */
public class DeviceInfoInteractorImpl implements DeviceInfoInteractor {
    private NetworkService networkService;
    private Parser parser;
    private static final Logger LOG = Logger.getLogger(DeviceInfoInteractorImpl.class);

    public DeviceInfoInteractorImpl(NetworkService networkService, Parser parser) {
        this.networkService = networkService;
        this.parser = parser;
    }

    @Override
    public Single<StatusResponse> setDeviceSettings(SettingsRequest settingsRequest) {
        return networkService.setDeviceSettings(settingsRequest);
    }

    @Override
    public Single<StatusResponse> rmDevice(int id) {
        return networkService.rmDeviceById(id);
    }

    @Override
    public Single<DeviceInfo> getDeviceInfo(int id) {
        return networkService.getDeviceInfo(id)
                .map(DeviceInfoResponse::getData);
    }

    @Override
    public Single<Settings> getDeviceSettings(int id) {
        return networkService.getDeviceSettings(id)
                .map(SettingsResponse::getSettings);
    }

    @Override
    public Single<List<Call>> getDeviceCalls(int id) {
        LOG.debug("parser " + parser);
        return networkService.getDeviceCalls(id)
                .doOnSuccess(calls -> parser.saveObject("call" + id +".xml", calls))
                .map(callResponse -> {
                    List<Call> calls = callResponse.getData();
//                    parser.saveObject("call" + id +".xml", calls);
                    return calls;
                });
//                .map(CallResponse::getData);
//                .doOnSuccess(calls -> parser.saveObject("call" + "id.xml", calls));
    }

    @Override
    public Single<List<Contact>> getDeviceContacts(int id) {
        return networkService.getDeviceContacts(id)
                .map(ContactResponse::getData);
    }

    @Override
    public Single<List<DeviceEvent>> getDeviceEvents(int id) {
        return networkService.getDeviceEvents(id)
                .map(DeviceEventResponse::getData);
    }

    @Override
    public Single<List<BatteryEvent>> getDeviceBatteryEvents(int id) {
        return networkService.getDeviceBatteryEvents(id)
                .map(BatteryEventResponse::getData);
    }

    @Override
    public Single<List<InstallApp>> getDeviceInstallApps(int id) {
        return networkService.getDeviceInstallApps(id)
                .map(InstallAppResponse::getData);
    }

    @Override
    public Single<List<Location>> getDeviceLocations(int id) {
        return networkService.getDeviceLocations(id)
                .map(LocationResponse::getData);
    }

    @Override
    public Single<List<Message>> getDeviceMessages(int id) {
        return networkService.getDeviceMessages(id)
                .map(MessageResponse::getData);
    }

    @Override
    public Single<List<NetworkEvent>> getDeviceNetworkEvents(int id) {
        return networkService.getDeviceNetworkEvents(id)
                .map(NetworkEventResponse::getData);
    }

    @Override
    public Single<List<ServiceEvent>> getDeviceServiceEvents(int id) {
        return networkService.getDeviceServiceEvents(id)
                .map(ServiceEventResponse::getData);
    }
}
