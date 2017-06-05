package gui.login;

import com.esri.arcgisruntime.mapping.view.MapView;
import com.jfoenix.controls.*;
import com.jfoenix.svg.SVGGlyphLoader;
import com.jfoenix.validation.RequiredFieldValidator;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import gui.fragment_controllers.ControlPanelController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.animation.PauseTransition;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by oldman on 02.06.17.
 */
public class LoginController implements Initializable {
    @FXMLViewFlowContext
    private ViewFlowContext flowContext;

    private Label label;
    @FXML
    private JFXTextField txtUsername;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXButton btnLogin;
    @FXML
    private StackPane rootPane;
    @FXML
    private ImageView imgProgress;
    private MapView mapView;

    private static final Logger LOG = Logger.getLogger(LoginController.class);
    private ResourceBundle bundle;

    private void handleButtonAction(ActionEvent event) {
        LOG.info("handleButtonAction!");
        label.setText("Hello World!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        bundle = ResourceBundle.getBundle("resources/string", Locale.getDefault());
        handleValidation();
        imgProgress.setVisible(false);
    }

    @FXML
    private void login(ActionEvent event) {

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

//            Parent root = FXMLLoader.load(getClass().getResource("fxml/dashboard.fxml"));
//
            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("");
//            dashboardStage.getIcons().add(new Image(getClass().getResourceAsStream("drawables/logo-vs-rf.png")));
//
//            JFXDecorator decorator = new JFXDecorator(dashboardStage, root, false , true,true);
//            Scene scene = new Scene(decorator);
//            final ObservableList<String> stylesheets = scene.getStylesheets();
//            stylesheets.addAll(LoginController.class.getResource("/css/jfoenix-fonts.css").toExternalForm(),
//                    LoginController.class.getResource("/css/jfoenix-design.css").toExternalForm(),
//                    LoginController.class.getResource("/css/jfoenix-main-demo.css").toExternalForm());
//           dashboardStage.setScene(scene);
//            dashboardStage.setResizable(false);
//            dashboardStage.show();  // layer
//
            new Thread(() -> {
                try {
                    SVGGlyphLoader.loadGlyphsFont(gui.login.LoginController.class.getResourceAsStream("/fonts/icomoon.svg"),
                            "icomoon.svg");
                } catch (IOException ioExc) {
                    ioExc.printStackTrace();
                }
            }).start();

            JFXHamburger show = new JFXHamburger();
            show.setPadding(new Insets(10, 5, 10, 5));
            JFXRippler rippler = new JFXRippler(show, JFXRippler.RipplerMask.CIRCLE, JFXRippler.RipplerPos.BACK);

            JFXListView<Label> list = new JFXListView<>();
            for (int i = 1; i < 5; i++) {
                list.getItems().add(new Label("Item " + i));
            }

            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getChildren().add(rippler);
            AnchorPane.setLeftAnchor(rippler, 200.0);
            AnchorPane.setTopAnchor(rippler, 210.0);

            Flow flow = new Flow(ControlPanelController.class);
            DefaultFlowContainer container = new DefaultFlowContainer();
            flowContext = new ViewFlowContext();
            flowContext.register("Stage", dashboardStage);
            flow.createHandler(flowContext).start(container);


            JFXDecorator decorator = new JFXDecorator(dashboardStage, container.getView());
            decorator.setCustomMaximize(true);
            Scene scene = new Scene(decorator, 800, 850);
            final ObservableList<String> stylesheets = scene.getStylesheets();
            stylesheets.addAll(gui.login.LoginController.class.getResource("/css/jfoenix-fonts.css").toExternalForm(),
                    gui.login.LoginController.class.getResource("/css/jfoenix-design.css").toExternalForm(),
                    gui.login.LoginController.class.getResource("/css/main.css").toExternalForm());
            dashboardStage.setMinWidth(700);
            dashboardStage.setMinHeight(800);
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