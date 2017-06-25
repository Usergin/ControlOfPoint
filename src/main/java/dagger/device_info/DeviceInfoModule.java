package dagger.device_info;

import business.control_panel.ControlPanelInteractor;
import business.control_panel.ControlPanelInteractorImpl;
import business.device.DeviceInfoInteractor;
import business.device.DeviceInfoInteractorImpl;
import dagger.AppScope;
import dagger.Module;
import dagger.Provides;
import dagger.application.AppModule;
import dagger.network.NetworkModule;
import data.local.parser.Parser;
import data.remote.NetworkService;
import gui.control_panel.ControlPanelPresenter;
import gui.control_panel.ControlPanelPresenterImpl;
import gui.control_panel.ControlPanelView;
import gui.fragment_controllers.device_info.DeviceInfoPresenter;
import gui.fragment_controllers.device_info.DeviceInfoPresenterImpl;
import gui.fragment_controllers.device_info.DeviceInfoView;

/**
 * Created by OldMan on 25.06.2017.
 */
@Module(library = true, overrides = true, includes = {AppModule.class, NetworkModule.class}, injects = {DeviceInfoView.class}, complete = false)
public class DeviceInfoModule {
    @Provides
    @AppScope
    DeviceInfoInteractor provideDeviceInfoInteractor(NetworkService networkService, Parser parser){
        return new DeviceInfoInteractorImpl(networkService, parser);
    }
    @Provides
    @AppScope
    DeviceInfoPresenter provideDeviceInfoPresenter(DeviceInfoInteractor deviceInfoInteractor){
        return new DeviceInfoPresenterImpl(deviceInfoInteractor);
    }
}
