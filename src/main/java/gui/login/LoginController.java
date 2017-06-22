package gui.login;

import business.login.LoginInteractor;
import business.login.LoginInteractorImpl;
import com.jfoenix.controls.*;
import com.jfoenix.svg.SVGGlyphLoader;
import com.jfoenix.validation.RequiredFieldValidator;
import data.remote.model.request.Authentication;
import data.remote.model.response.UserResponse;
import de.jensd.fx.glyphs.GlyphsBuilder;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import gui.control_panel.ControlPanelController;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * Created by oldman on 02.06.17.
 */

@ViewController(value = "/fxml/login.fxml")
@Singleton
public class LoginController implements LoginView {
    @FXMLViewFlowContext
    private ViewFlowContext flowContext;
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

    LoginInteractor loginInteractor;

    private static final Logger LOG = Logger.getLogger(LoginController.class);
    private StringProperty username = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();
    BooleanBinding booleanBind = Bindings.or(username.isEmpty(),
            password.isEmpty());

    @PostConstruct
    public void init() throws Exception {
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
        loginInteractor = new LoginInteractorImpl(this);
        //ToDO delete this
        loginInteractor.onAuthentication(new Authentication("operator46", "jLIjfQZ5yojbZGTqxg2pY0VROWQ="));
    }


    @Override
    public void showSnackBar(String message) {
        JFXSnackbar jfxSnackbar = new JFXSnackbar(rootPane);
        jfxSnackbar.show(message, 3000);
    }

    @FXML
    private void login(ActionEvent event) {
//        loginInteractor.onAuthentication(new Authentication(username.get(), getHashedValue(password.get())));
    }

    @Override
    public void onAuthenticationSuccess() {
        PauseTransition pauseTransition = new PauseTransition();
        //ToDo change second
        pauseTransition.setDuration(Duration.seconds(0));
        pauseTransition.setOnFinished(ev -> {
            completeLogin();
        });
        pauseTransition.play();
    }

    @Override
    public void showProgress(boolean val) {
        imgProgress.setVisible(false);
    }

    @Override
    public void showBtnLogin(boolean val) {
        btnLogin.setVisible(false);
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

    private void completeLogin() {
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
            flowContext = new ViewFlowContext();
            flowContext.register("Stage", dashboardStage);
            flow.createHandler(flowContext).start(container);


            JFXDecorator decorator = new JFXDecorator(dashboardStage, container.getView());
            decorator.setCustomMaximize(true);
            Scene scene = new Scene(decorator, 1024, 600);
            final ObservableList<String> stylesheets = scene.getStylesheets();
            stylesheets.addAll(ControlPanelController.class.getResource("/css/jfoenix-fonts.css").toExternalForm(),
                    ControlPanelController.class.getResource("/css/jfoenix-design.css").toExternalForm(),
                    ControlPanelController.class.getResource("/css/main.css").toExternalForm());
            dashboardStage.setMinWidth(640);
            dashboardStage.setMinHeight(480);
            dashboardStage.setScene(scene);
            dashboardStage.show();
        } catch (Exception ex) {
            LOG.error(null, ex);
        }


    }


}