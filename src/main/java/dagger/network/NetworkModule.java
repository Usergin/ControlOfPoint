package dagger.network;

import business.control_panel.ControlPanelInteractor;
import dagger.Module;
import dagger.Provides;
import data.remote.NetworkService;
import data.remote.ServerApi;
import gui.control_panel.ControlPanelController;
import gui.fragment_controllers.device_info.DeviceInfoController;
import gui.fragment_controllers.device_info.DeviceInfoView;
import gui.login.LoginController;

import javax.inject.Singleton;

/**
 * Created by OldMan on 18.06.2017.
 */

@Module(library = true,overrides = true, injects = {LoginController.class, ControlPanelController.class,  DeviceInfoController.class}, complete = false, includes = {ServerApiModule.class})
public class NetworkModule {
    @Provides
    @Singleton
    public NetworkService provideNetworkService(ServerApi serverApi) {
        return new NetworkService(serverApi);
    }


}
