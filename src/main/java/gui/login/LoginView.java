package gui.login;

/**
 * Created by OldMan on 22.06.2017.
 */
public interface LoginView {
    void showSnackBar(String message);
    void onAuthenticationSuccess();
    void showProgress(boolean val);
    void showBtnLogin(boolean val);
}
