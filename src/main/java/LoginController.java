import com.esri.arcgisruntime.mapping.view.MapView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import org.apache.log4j.Logger;

/**
 * Created by oldman on 02.06.17.
 */
public class LoginController implements Initializable {

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
        try {
            imgProgress.setVisible(false);
            Parent root = FXMLLoader.load(getClass().getResource("fxml/dashboard.fxml"));

            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("");
            dashboardStage.getIcons().add(new Image(getClass().getResourceAsStream("drawables/logo-vs-rf.png")));

            JFXDecorator decorator = new JFXDecorator(dashboardStage, root, false , false,true);
            Scene scene = new Scene(decorator);
            dashboardStage.setScene(scene);
            dashboardStage.setResizable(false);
            dashboardStage.show();  // layer

        } catch (IOException ex) {
            LOG.error(null, ex);      }

//        new Thread(() -> {
//            try {
//                SVGGlyphLoader.loadGlyphsFont(LoginController.class.getResourceAsStream("/fonts/icomoon.svg"),
//                        "icomoon.svg");
//            } catch (IOException ioExc) {
//                ioExc.printStackTrace();
//            }
//        }).start();

//        Flow flow = new Flow(MainController.class);
//        DefaultFlowContainer container = new DefaultFlowContainer();
//        flowContext = new ViewFlowContext();
//        flowContext.register("Stage", stage);
//        flow.createHandler(flowContext).start(container);
//
//        JFXDecorator decorator = new JFXDecorator(stage, container.getView());
//        decorator.setCustomMaximize(true);
//        Scene scene = new Scene(decorator, 800, 850);
//        final ObservableList<String> stylesheets = scene.getStylesheets();
//        stylesheets.addAll(LoginController.class.getResource("/css/jfoenix-fonts.css").toExternalForm(),
//                LoginController.class.getResource("/css/jfoenix-design.css").toExternalForm(),
//                LoginController.class.getResource("/css/jfoenix-main-demo.css").toExternalForm());
//        stage.setMinWidth(700);
//        stage.setMinHeight(800);
//        stage.setScene(scene);
//        stage.show();
    }


}