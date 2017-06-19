package business.login;

import dagger.Injector;
import dagger.application.AppModule;
import dagger.network.NetworkModule;
import data.local.parser.Parser;
import data.remote.NetworkService;
import data.remote.model.request.Authentication;
import data.remote.model.response.UserResponse;
import gui.login.LoginController;
import io.reactivex.Single;
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
    private LoginController loginController;
    private static final Logger LOG = Logger.getLogger(LoginInteractorImpl.class);

    public LoginInteractorImpl(LoginController loginController) {
        Injector.inject(this, Arrays.asList(new AppModule(), new NetworkModule()));
        this.loginController = loginController;
    }

    @Override
    public Single<UserResponse> getUser(Authentication authentication) {
        LOG.info("getUser " + authentication.getLogin() + " " + networkService);
       return networkService.getUser(authentication)
               .doOnSuccess(userResponse -> parser.saveObject("user.xml", userResponse.getUser()));
    }
}
