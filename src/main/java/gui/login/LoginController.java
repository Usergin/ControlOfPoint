package gui.login;

import com.jfoenix.controls.*;
import com.jfoenix.svg.SVGGlyphLoader;
import dagger.Injector;
import dagger.application.AppModule;
import dagger.login.LoginModule;
import data.model.User;
import data.remote.model.request.Authentication;
import gui.control_panel.ControlPanelController;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

/**
 * Created by oldman on 02.06.17.
 */

@ViewController(value = "/fxml/login.fxml")
@Singleton
public class LoginController implements LoginView {
    @FXMLViewFlowContext
    @Inject
    ViewFlowContext flowContext;
    @Inject
    LoginPresenter loginPresenter;
    @ViewNode
    private StackPane rootPane;
    @ViewNode
    private JFXTextField txtUsername;
    @ViewNode
    private JFXPasswordField txtPassword;
    @ViewNode
    private JFXButton btnLogin;
    @ViewNode
    private ImageView imgProgress;
    private static final String ERROR = "error";
    private static final String EM1 = "1em";

    private static final Logger LOG = Logger.getLogger(LoginController.class);
    private StringProperty username = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();
    BooleanBinding booleanBind = Bindings.or(username.isEmpty(),
            password.isEmpty());

    @PostConstruct
    public void init() throws Exception {
        Injector.inject(this, Arrays.asList(new LoginModule(), new AppModule()));
        System.out.println("init " + loginPresenter);
        loginPresenter.setLoginView(this);

        txtUsername.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                txtUsername.validate();
            }
        });
        txtPassword.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                txtPassword.validate();
            }
        });
        imgProgress.setVisible(false);
        btnLogin.disableProperty().bind(booleanBind);
        username.bind(txtUsername.textProperty());
        password.bind(txtPassword.textProperty());
        //ToDO delete this
        loginPresenter.onAuthentication(new Authentication("operator46", "jLIjfQZ5yojbZGTqxg2pY0VROWQ="));
    }


    @Override
    public void showSnackBar(String message) {
        JFXSnackbar jfxSnackbar = new JFXSnackbar(rootPane);
        jfxSnackbar.show(message, 3000);
    }

    @FXML
    void login(ActionEvent event) {
        loginPresenter.onAuthentication(new Authentication(username.get(), getHashedValue(password.get())));
    }

    @Override
    public void onAuthenticationSuccess(User user) {
        PauseTransition pauseTransition = new PauseTransition();
        //ToDo change second
        pauseTransition.setDuration(Duration.seconds(2));
        pauseTransition.setOnFinished(ev -> {
            completeLogin(user);
        });
        pauseTransition.play();
    }

    @Override
    public void showProgress(boolean val) {
        imgProgress.setVisible(val);
    }

    @Override
    public void showBtnLogin(boolean val) {
        btnLogin.setVisible(val);
    }

    private String getHashedValue(String inputData) {
        String sResp = null;
        try {
            byte byteHash[];
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            sha1.update(inputData.getBytes("utf-8"));
            byteHash = sha1.digest();
            sha1.reset();
            return Base64.getEncoder().encodeToString(byteHash);
        } catch (Exception e) {
            System.err.println("getHashedValue failed: " + e.getMessage());
            return null;
        }
    }

    private void completeLogin(User user) {
        btnLogin.getScene().getWindow().hide();
        imgProgress.setVisible(false);
        try {
            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("");
            new Thread(() -> {
                try {
                    SVGGlyphLoader.loadGlyphsFont(gui.login.LoginController.class.getResourceAsStream("/fonts/icomoon.svg"),
                            "icomoon.svg");
                } catch (IOException ioExc) {
                    ioExc.printStackTrace();
                }
            }).start();

            Flow flow = new Flow(ControlPanelController.class);
            DefaultFlowContainer container = new DefaultFlowContainer();
            flowContext.register("Stage", dashboardStage);
            flowContext.register("user", user);
            flow.createHandler(flowContext).start(container);

            JFXDecorator decorator = new JFXDecorator(dashboardStage, container.getView());
            decorator.setCustomMaximize(true);
            Scene scene = new Scene(decorator, 1300, 750);
            final ObservableList<String> stylesheets = scene.getStylesheets();
            stylesheets.addAll(ControlPanelController.class.getResource("/css/jfoenix-fonts.css").toExternalForm(),
                    ControlPanelController.class.getResource("/css/jfoenix-design.css").toExternalForm(),
                    ControlPanelController.class.getResource("/css/main.css").toExternalForm());
            dashboardStage.setMinWidth(640);
            dashboardStage.setMinHeight(480);
            dashboardStage.setScene(scene);
            dashboardStage.getIcons().addAll( new Image(getClass().getResourceAsStream("/drawables/logo-vs-rf.png")));
            dashboardStage.show();
        } catch (Exception ex) {
            LOG.error(null, ex);
        }


    }


}