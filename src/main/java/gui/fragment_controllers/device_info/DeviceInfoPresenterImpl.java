package gui.fragment_controllers.device_info;

import business.device.DeviceInfoInteractor;
import data.remote.model.information.*;
import data.remote.model.request.SettingsRequest;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * Created by OldMan on 25.06.2017.
 */
public class DeviceInfoPresenterImpl implements DeviceInfoPresenter {
    private static final Logger LOG = Logger.getLogger(DeviceInfoController.class);
    private DeviceInfoView deviceInfoView;
    private DeviceInfoInteractor deviceInfoInteractor;

    public DeviceInfoPresenterImpl(DeviceInfoInteractor deviceInfoInteractor) {
        this.deviceInfoInteractor = deviceInfoInteractor;
    }

    @Override
    public void setDeviceInfoView(DeviceInfoView deviceInfoView) {
        this.deviceInfoView = deviceInfoView;
    }

    @Override
    public void setDeviceSettings(SettingsRequest request) {

    }

    @Override
    public void rmDevice(int id) {

    }

    @Override
    public void onDeviceInfo(int id) {
        if(deviceInfoView!=null) {
            deviceInfoView.showSpinner(true);
            deviceInfoInteractor.getDeviceInfo(id)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(JavaFxScheduler.platform())
                    .subscribe(this::handleSuccessDeviceInfo, this::handleError);
        }
    }

    private void handleSuccessDeviceInfo(DeviceInfo deviceInfo) {
        if(deviceInfoView != null) {
            deviceInfoView.showSpinner(false);
            deviceInfoView.showDeviceInfoPopup(deviceInfo);
        }
    }

    private void handleError(Throwable throwable) {
        if (deviceInfoView != null) {
            deviceInfoView.showSpinner(false);
            deviceInfoView.showSnackBar("Ошибка: " + throwable.getMessage());
        }
    }

    @Override
    public void onDeviceSetting(int id) {
        if(deviceInfoView!=null) {
            deviceInfoView.showSpinner(true);
            deviceInfoInteractor.getDeviceSettings(id)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(JavaFxScheduler.platform())
                    .subscribe(this::handleSuccessDeviceSettings, this::handleError);
        }
    }

    private void handleSuccessDeviceSettings(Settings settings) throws IOException {
        if(deviceInfoView != null) {
            deviceInfoView.showSpinner(false);
            deviceInfoView.showSettings(settings);
            LOG.info(settings);
        }
    }

    @Override
    public void onDeviceCalls(int id) {
        if(deviceInfoView!=null) {
            deviceInfoView.showSpinner(true);
            deviceInfoInteractor.getDeviceCalls(id)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(JavaFxScheduler.platform())
                    .subscribe(this::handleSuccessDeviceCalls, this::handleError);
        }
    }

    private void handleSuccessDeviceCalls(List<Call> calls) {
        if(deviceInfoView != null) {
            deviceInfoView.showSpinner(false);
            LOG.info(calls);
        }
    }

    @Override
    public void onDeviceContacts(int id) {
        if(deviceInfoView!=null) {
            deviceInfoView.showSpinner(true);
            deviceInfoInteractor.getDeviceContacts(id)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(JavaFxScheduler.platform())
                    .subscribe(this::handleSuccessDeviceContacts, this::handleError);
        }
    }

    private void handleSuccessDeviceContacts(List<Contact> contacts) {
        if(deviceInfoView != null) {
            deviceInfoView.showSpinner(false);
            LOG.info(contacts);
        }
    }

    @Override
    public void onDeviceEvents(int id) {
        if(deviceInfoView!=null) {
            deviceInfoView.showSpinner(true);
            deviceInfoInteractor.getDeviceEvents(id)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(JavaFxScheduler.platform())
                    .subscribe(this::handleSuccessDeviceEvents, this::handleError);
        }
    }

    private void handleSuccessDeviceEvents(List<DeviceEvent> deviceEvents) {
        if(deviceInfoView != null) {
            deviceInfoView.showSpinner(false);
            LOG.info(deviceEvents);
        }
    }

    @Override
    public void onDeviceBatteryEvents(int id) {
        if(deviceInfoView!=null) {
            deviceInfoView.showSpinner(true);
            deviceInfoInteractor.getDeviceBatteryEvents(id)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(JavaFxScheduler.platform())
                    .subscribe(this::handleSuccessDeviceBatteryEvents, this::handleError);
        }
    }

    private void handleSuccessDeviceBatteryEvents(List<BatteryEvent> batteryEvents) {
        if(deviceInfoView != null) {
            deviceInfoView.showSpinner(false);
            LOG.info(batteryEvents);
        }
    }

    @Override
    public void onDeviceInstallApps(int id) {
        if(deviceInfoView!=null) {
            deviceInfoView.showSpinner(true);
            deviceInfoInteractor.getDeviceInstallApps(id)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(JavaFxScheduler.platform())
                    .subscribe(this::handleSuccessDeviceInstallApps, this::handleError);
        }
    }

    private void handleSuccessDeviceInstallApps(List<InstallApp> installApps) {
        if(deviceInfoView != null) {
            deviceInfoView.showSpinner(false);
            LOG.info(installApps);
        }
    }

    @Override
    public void onDeviceLocations(int id) {
        if(deviceInfoView!=null) {
            deviceInfoView.showSpinner(true);
            deviceInfoInteractor.getDeviceLocations(id)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(JavaFxScheduler.platform())
                    .subscribe(this::handleSuccessDeviceInfo, this::handleError);
        }
    }

    private void handleSuccessDeviceInfo(List<Location> locations) {
        if(deviceInfoView != null)
            deviceInfoView.showMapFlow(locations);
    }

    @Override
    public void onDeviceMessages(int id) {
        if(deviceInfoView!=null) {
            deviceInfoView.showSpinner(true);
            deviceInfoInteractor.getDeviceMessages(id)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(JavaFxScheduler.platform())
                    .subscribe(this::handleSuccessDeviceMessqges, this::handleError);
        }
    }

    private void handleSuccessDeviceMessqges(List<Message> messages) {
        if(deviceInfoView != null) {
            deviceInfoView.showSpinner(false);
            LOG.info(messages);
        }
    }

    @Override
    public void onDeviceNetworkEvents(int id) {
        if(deviceInfoView!=null) {
            deviceInfoView.showSpinner(true);
            deviceInfoInteractor.getDeviceNetworkEvents(id)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(JavaFxScheduler.platform())
                    .subscribe(this::handleSuccessDeviceNetworkEvents, this::handleError);
        }
    }

    private void handleSuccessDeviceNetworkEvents(List<NetworkEvent> networkEvents) {
        if(deviceInfoView != null) {
            deviceInfoView.showSpinner(false);
            LOG.info(networkEvents);
        }
    }

    @Override
    public void onDeviceServiceEvents(int id) {
        if(deviceInfoView!=null) {
            deviceInfoView.showSpinner(true);
            deviceInfoInteractor.getDeviceServiceEvents(id)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(JavaFxScheduler.platform())
                    .subscribe(this::handleSuccessDeviceServiceEvents, this::handleError);
        }
    }

    private void handleSuccessDeviceServiceEvents(List<ServiceEvent> serviceEvents) {
        if(deviceInfoView != null) {
            deviceInfoView.showSpinner(false);
            LOG.info(serviceEvents);
        }
    }

}
