package gui.control_panel;

import business.control_panel.ControlPanelInteractor;
import data.model.Device;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.RxBus;

/**
 * Created by OldMan on 23.06.2017.
 */
public class ControlPanelPresenterImpl implements ControlPanelPresenter {
    private ControlPanelView controlPanelView;
    private ControlPanelInteractor controlPanelInteractor;

    public ControlPanelPresenterImpl(ControlPanelInteractor controlPanelInteractor) {
        this.controlPanelInteractor = controlPanelInteractor;
    }

    @Override
    public void setControlPanelView(ControlPanelView panelView) {
        this.controlPanelView = panelView;
    }

    @Override
    public void onNewDeviceList() {
        if (controlPanelView != null) {
            controlPanelView.showDeviceMenu(false);
            controlPanelView.showSpinner(true);
            controlPanelInteractor.getNewDeviceList()
                    .map(FXCollections::observableArrayList)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(JavaFxScheduler.platform())
                    .subscribe(this::handleSuccessDeviceList, this::handleError);
            //                .doOnSuccess(devices -> parser.saveObject("devices.xml", devices));
        }
    }

    @Override
    public void onLocalDeviceList() {

    }

    @Override
    public void onDeviceById(int id) {
        if (controlPanelView != null) {
            controlPanelView.showDeviceMenu(false);
            controlPanelView.showSpinner(true);
            controlPanelInteractor.getDeviceById(id)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(JavaFxScheduler.platform())
                    .subscribe(this::handleSuccessDevice, this::handleError);
        }
    }

    private void handleSuccessDevice(Device device) {
        RxBus.instanceOf().setShortDeviceInfo(device);
        if (controlPanelView != null) {
            controlPanelView.showSpinner(false);
            controlPanelView.showDeviceMenu(true);
            controlPanelView.showDeviceFlow(device);
        }
    }

    private void handleError(Throwable throwable) {
        if (controlPanelView != null) {
            controlPanelView.showSnackBar("Ошибка: " + throwable.getMessage());
            controlPanelView.showErrorFlow();
            controlPanelView.showDeviceMenu(true);

        }
    }

    private void handleSuccessDeviceList(ObservableList<Device> devices) {
        RxBus.instanceOf().setDeviceList(devices);
        if (controlPanelView != null) {
            controlPanelView.showSpinner(false);
            controlPanelView.setDeviceList(devices);
            controlPanelView.showDeviceMenu(true);
            controlPanelView.showMapFlow();

        }
    }
}
