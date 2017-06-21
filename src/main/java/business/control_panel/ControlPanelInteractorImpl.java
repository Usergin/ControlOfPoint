package business.control_panel;

import dagger.Injector;
import dagger.application.AppModule;
import dagger.network.NetworkModule;
import data.local.parser.Parser;
import data.model.Device;
import data.remote.NetworkService;
import data.remote.model.response.DeviceListResponse;
import data.remote.model.response.DeviceResponse;
import gui.control_panel.ControlPanelController;
import io.reactivex.Single;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

/**
 * Created by oldman on 21.06.17.
 */
public class ControlPanelInteractorImpl implements ControlPanelInteractor {
    @Inject
    NetworkService networkService;
    @Inject
    Parser parser;
    private ControlPanelController controlPanelController;

    public ControlPanelInteractorImpl(ControlPanelController controlPanelController) {
        Injector.inject(this, Arrays.asList(new AppModule(), new NetworkModule()));
        this.controlPanelController = controlPanelController;
    }

    @Override
    public Single<List<Device>> getNewDeviceList() {
        return networkService.getDevices()
                .map(DeviceListResponse::getDevices);
//                .doOnSuccess(devices -> parser.saveObject("devices.xml", devices));

    }

    @Override
    public Single<List<Device>> getLocalDeviceList() {
        return null;
    }

    @Override
    public Single<Device> getDeviceById(int id) {
        return  networkService.getDeviceById(id)
                .map(DeviceResponse::getDevice);
//                .doOnSuccess(devices -> parser.saveObject("device.xml", devices));
    }
}
