package business.login;

import dagger.Injector;
import dagger.application.AppModule;
import dagger.network.NetworkModule;
import data.local.parser.Parser;
import data.remote.NetworkService;
import data.remote.model.request.Authentication;
import data.remote.model.response.UserResponse;
import gui.login.LoginView;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.util.Arrays;

/**
 * Created by OldMan on 18.06.2017.
 */
public class LoginInteractorImpl implements LoginInteractor {
    @Inject
    NetworkService networkService;
    @Inject
    Parser parser;

    private LoginView loginView;
    private static final Logger LOG = Logger.getLogger(LoginInteractorImpl.class);

    public LoginInteractorImpl(LoginView loginController) {
        Injector.inject(this, Arrays.asList(new AppModule(), new NetworkModule()));
        this.loginView = loginController;
    }

    @Override
    public void onAuthentication(Authentication authentication) {
        LOG.info("onAuthentication " + authentication.getLogin() + " " + networkService);
        if (loginView != null) {
            loginView.showBtnLogin(false);
            loginView.showProgress(true);
            networkService.getUser(authentication)
                    .doOnSuccess(userResponse -> parser.saveObject("user.xml", userResponse.getUser()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(JavaFxScheduler.platform())
                    .subscribe(this::handleSuccess, this::handleError);
            ;
        }
    }

    private void handleError(Throwable throwable) {
        if (loginView != null) {
            loginView.showBtnLogin(true);
            loginView.showProgress(false);
            loginView.showSnackBar("Ошибка: " + throwable.getMessage());
        }
    }

    private void handleSuccess(UserResponse userResponse) {
        if (loginView != null) {
            loginView.showBtnLogin(false);
            loginView.showProgress(false);

            loginView.showSnackBar("Добро пожаловать: " + userResponse.getUser().getRank()
                    + " " + userResponse.getUser().getUsername());
            loginView.onAuthenticationSuccess();

        }
    }

}
