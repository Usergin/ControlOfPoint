package business.control_panel;

import dagger.Injector;
import dagger.application.AppModule;
import dagger.network.NetworkModule;
import data.local.parser.Parser;
import data.model.Device;
import data.remote.NetworkService;
import data.remote.model.response.DeviceListResponse;
import data.remote.model.response.DeviceResponse;
import gui.control_panel.ControlPanelView;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.util.Arrays;

/**
 * Created by oldman on 21.06.17.
 */
public class ControlPanelInteractorImpl implements ControlPanelInteractor {
    @Inject
    NetworkService networkService;
    @Inject
    Parser parser;
    private ControlPanelView controlPanelView;
    private static final Logger LOG = Logger.getLogger(ControlPanelInteractorImpl.class);

    public ControlPanelInteractorImpl(ControlPanelView controlPanelView) {
        Injector.inject(this, Arrays.asList(new AppModule(), new NetworkModule()));
        this.controlPanelView = controlPanelView;
        if(controlPanelView!= null){
            controlPanelView.showDeviceMenu(false);
            controlPanelView.showSpinnerFlow();
            getNewDeviceList();
        }

    }

    @Override
    public void getNewDeviceList() {
        LOG.info("getNewDeviceList");
        networkService.getDevices()
                .map(DeviceListResponse::getDevices)
                .map(FXCollections::observableArrayList)
                .subscribeOn(Schedulers.computation())
                .observeOn(JavaFxScheduler.platform())
                .subscribe(this::handleSuccessDeviceList, this::handleError);
//                .doOnSuccess(devices -> parser.saveObject("devices.xml", devices));

    }

    private void handleError(Throwable throwable) {
        if(controlPanelView != null) {
            controlPanelView.showSnackBar("Ошибка: " + throwable.getMessage());
            controlPanelView.showErrorFlow();
        }
    }

    private void handleSuccessDeviceList(ObservableList<Device> devices) {
        if (controlPanelView != null) {
           controlPanelView.setDeviceList(devices);
            controlPanelView.showMapFlow();
            controlPanelView.showDeviceMenu(true);

        }
    }

    @Override
    public void getLocalDeviceList() {

    }

    @Override
    public void getDeviceById(int id) {
        networkService.getDeviceById(id)
                .map(DeviceResponse::getDevice);
//                .doOnSuccess(devices -> parser.saveObject("device.xml", devices));
    }

}
