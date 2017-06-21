package dagger.network;

import business.control_panel.ControlPanelInteractor;
import business.login.LoginInteractor;
import dagger.Module;
import dagger.Provides;
import data.remote.NetworkService;
import data.remote.ServerApi;

import javax.inject.Singleton;

/**
 * Created by OldMan on 18.06.2017.
 */

@Module( library = true, injects = {LoginInteractor.class, ControlPanelInteractor.class}, complete = false, includes = {ServerApiModule.class})
public class NetworkModule {
    @Provides
    @Singleton
    public NetworkService provideNetworkService(ServerApi serverApi) {
        return new NetworkService(serverApi);
    }


}
