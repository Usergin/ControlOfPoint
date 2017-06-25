package business.control_panel;

import data.local.parser.Parser;
import data.model.Device;
import data.remote.NetworkService;
import data.remote.model.response.DeviceListResponse;
import data.remote.model.response.DeviceResponse;
import io.reactivex.Single;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by oldman on 21.06.17.
 */
public class ControlPanelInteractorImpl implements ControlPanelInteractor {
    private NetworkService networkService;
    private Parser parser;
    private static final Logger LOG = Logger.getLogger(ControlPanelInteractorImpl.class);

    public ControlPanelInteractorImpl(NetworkService networkService, Parser parser) {
        this.networkService = networkService;
        this.parser = parser;
    }

    @Override
    public Single<List<Device>> getNewDeviceList() {
        LOG.info("getNewDeviceList");
        return networkService.getDeviceList()
                .map(DeviceListResponse::getDevices);
//                .doOnSuccess(devices -> parser.saveObject("devices.xml", devices));

    }

    @Override
    public void getLocalDeviceList() {

    }

    @Override
    public Single<Device> getDeviceById(int id) {
        return networkService.getDeviceById(id)
                .map(DeviceResponse::getDevice);
//                .doOnSuccess(devices -> parser.saveObject("device.xml", devices));
    }

}
