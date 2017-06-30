package gui.login;

import business.login.LoginInteractor;
import data.remote.model.request.Authentication;
import data.remote.model.response.UserResponse;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by OldMan on 23.06.2017.
 */
public class LoginPresenterImpl implements LoginPresenter {
    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginInteractor loginInteractor) {
        this.loginInteractor = loginInteractor;
    }

    @Override
    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void onAuthentication(Authentication authentication) {
        if (loginView != null) {
            loginView.showBtnLogin(false);
            loginView.showProgress(true);
            loginInteractor.sendAuth(authentication)
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
            loginView.showSnackBar("Ошибка: " + throwable);
        }
    }

    private void handleSuccess(UserResponse userResponse) {
        if (loginView != null) {
            loginView.showBtnLogin(false);
            loginView.showProgress(false);

            loginView.showSnackBar("Добро пожаловать: " + userResponse.getUser().getRank()
                    + " " + userResponse.getUser().getUsername());
            if (userResponse.getCode() == 1)
                loginView.onAuthenticationSuccess(userResponse.getUser());

        }
    }
}
