package dagger.login;

import business.login.LoginInteractor;
import business.login.LoginInteractorImpl;
import dagger.AppScope;
import dagger.Module;
import dagger.Provides;
import dagger.application.AppModule;
import dagger.network.NetworkModule;
import data.local.parser.Parser;
import data.remote.NetworkService;
import gui.login.LoginPresenter;
import gui.login.LoginPresenterImpl;
import gui.login.LoginView;

/**
 * Created by OldMan on 18.06.2017.
 */
@Module(library = true, overrides = true, includes = {AppModule.class, NetworkModule.class}, injects = {LoginView.class}
        , complete = false)
public class LoginModule {
    @Provides
    @AppScope
    LoginInteractor provideLoginInteractor(NetworkService networkService, Parser parser) {
        return new LoginInteractorImpl(networkService, parser);
    }

    @Provides
    @AppScope
    LoginPresenter provideLoginPresenter(LoginInteractor loginInteractor) {
        return new LoginPresenterImpl(loginInteractor);
    }
}
