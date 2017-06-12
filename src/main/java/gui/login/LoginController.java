package gui.login;

import com.esri.arcgisruntime.mapping.view.MapView;
import com.jfoenix.controls.*;
import com.jfoenix.svg.SVGGlyphLoader;
import com.jfoenix.validation.RequiredFieldValidator;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import gui.fragment_controllers.ControlPanelController;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by oldman on 02.06.17.
 */

@ViewController(value = "/fxml/login.fxml")
public class LoginController {
    @FXMLViewFlowContext
    private ViewFlowContext flowContext;

    private Label label;
    @ViewNode
    private JFXTextField txtUsername;
    @ViewNode
    private JFXPasswordField txtPassword;
    @ViewNode
    private JFXButton btnLogin;
    @ViewNode
    private ImageView imgProgress;

    private static final Logger LOG = Logger.getLogger(LoginController.class);
    private ResourceBundle bundle;
    private StringProperty username = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();

    private void handleButtonAction(ActionEvent event) {
        LOG.info("handleButtonAction!");
        label.setText("Hello World!");
    }

    @PostConstruct
    public void init() throws Exception  {
//        bundle = ResourceBundle.getBundle("resources/string", Locale.getDefault());
        handleValidation();
        imgProgress.setVisible(false);
        username.bind(txtUsername.textProperty());
        password.bind(txtPassword.textProperty());
    }

    private void clearAfterLogout() {
        ImageView userImageView = (ImageView)flowContext.getRegisteredObject("userImageView");
        userImageView.setImage(new Image("icon.png"));

        Label userLabel = (Label) flowContext.getRegisteredObject("userLabel");
        userLabel.setText("Username");

        JFXButton homeButton = (JFXButton)flowContext.getRegisteredObject("homeButton");
        if (homeButton != null) {
            homeButton.setVisible(false);
        }

        JFXListView sideBarList = (JFXListView) flowContext.getRegisteredObject("sideBarList");
        if (sideBarList != null) {
            sideBarList.getItems().clear();
            Label loginLabel = (Label) flowContext.getRegisteredObject("loginLabel");
            Label signupLabel = (Label) flowContext.getRegisteredObject("signupLabel");
            sideBarList.getItems().addAll(loginLabel,signupLabel);
        }
    }
    @FXML
    private void login(ActionEvent event) {
        LOG.info(username.get() + password.get());;

        imgProgress.setVisible(true);
        PauseTransition pauseTransition = new PauseTransition();
        pauseTransition.setDuration(Duration.seconds(1));
        pauseTransition.setOnFinished(ev -> {
            completeLogin();
        });
        pauseTransition.play();
    }

    private void handleValidation() {
        RequiredFieldValidator fieldValidator = new RequiredFieldValidator();
        txtUsername.getValidators().add(fieldValidator);
        fieldValidator.setMessage("Введите данные");
        fieldValidator.setIcon(new FontAwesomeIconView(FontAwesomeIcon.TIMES));

        txtUsername.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oldVal, Boolean newVal) -> {
            if (!newVal) {
                txtUsername.validate();
            }
        });
        RequiredFieldValidator fieldValidator2 = new RequiredFieldValidator();
        txtPassword.getValidators().add(fieldValidator2);
        fieldValidator2.setMessage("Введите данные");
        fieldValidator2.setIcon(new FontAwesomeIconView(FontAwesomeIcon.TIMES));
        txtPassword.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                txtPassword.validate();
            }
        });

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
        }
//        } catch (IOException ex) {
//            LOG.error(null, ex);      }
        catch (Exception ex) {
            LOG.error(null, ex);
        }


    }


}