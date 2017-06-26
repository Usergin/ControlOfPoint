package business.login;

import data.local.parser.Parser;
import data.remote.NetworkService;
import data.remote.model.request.Authentication;
import data.remote.model.response.UserResponse;
import gui.login.LoginView;
import io.reactivex.Single;
import org.apache.log4j.Logger;

/**
 * Created by OldMan on 18.06.2017.
 */
public class LoginInteractorImpl implements LoginInteractor {
    private NetworkService networkService;
    private Parser parser;
    private static final Logger LOG = Logger.getLogger(LoginInteractorImpl.class);

    public LoginInteractorImpl(NetworkService networkService, Parser parser) {
        this.networkService = networkService;
        this.parser = parser;
    }

    @Override
    public Single<UserResponse> sendAuth(Authentication authentication) {
         return networkService.getUser(authentication)
                .doOnSuccess(userResponse -> {
                    LOG.info("sendAuth " + userResponse.getUser());
                    parser.saveObject("user.xml", userResponse.getUser());
                });
    }
}

