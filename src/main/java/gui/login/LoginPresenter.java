package gui.login;

import data.remote.model.request.Authentication;

/**
 * Created by OldMan on 23.06.2017.
 */
public interface LoginPresenter {
    void onAuthentication(Authentication authentication);
    void setLoginView(LoginView loginView);
}
