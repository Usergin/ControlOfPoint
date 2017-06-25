package dagger.control_panel;

import business.control_panel.ControlPanelInteractor;
import business.control_panel.ControlPanelInteractorImpl;
import business.login.LoginInteractor;
import business.login.LoginInteractorImpl;
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
import gui.login.LoginController;
import gui.login.LoginPresenter;
import gui.login.LoginPresenterImpl;

/**
 * Created by OldMan on 23.06.2017.
 */
@Module(library = true, overrides = true, includes = {AppModule.class, NetworkModule.class}, injects = {ControlPanelView.class}, complete = false)
public class ControlPanelModule {
    @Provides
    @AppScope
    ControlPanelInteractor provideControlPanelInteractor(NetworkService networkService, Parser parser){
        return new ControlPanelInteractorImpl(networkService, parser);
    }
    @Provides
    @AppScope
    ControlPanelPresenter provideControlPanelPresenter(ControlPanelInteractor controlPanelInteractor){
        return new ControlPanelPresenterImpl(controlPanelInteractor);
    }
}
