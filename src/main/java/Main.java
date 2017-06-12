import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.svg.SVGGlyphLoader;
import gui.fragment_controllers.ControlPanelController;
import gui.login.LoginController;
import gui.main.MainController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;

/**
 * Created by oldman on 02.06.17.
 */
public class Main extends Application {
    private static final Logger LOG = Logger.getLogger(Main.class);
    @FXMLViewFlowContext
    private ViewFlowContext viewFlowContext;
    private final int MIN_WIDTH = 850;
    private final int MIN_HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {
        LOG.info("Application started");

        new Thread(()->{
            try {
                //he just loaded some svg from a font file
                SVGGlyphLoader.loadGlyphsFont(Main.class.getResourceAsStream("/fonts/icomoon.svg"),"icomoon.svg");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }).start();

        viewFlowContext = new ViewFlowContext();
        viewFlowContext.register("stage", primaryStage);

        Flow flow = new Flow(LoginController.class);
        DefaultFlowContainer container = new DefaultFlowContainer();
        flow.createHandler(viewFlowContext).start(container);

        JFXDecorator decorator = new JFXDecorator(primaryStage, container.getView());
        decorator.setMaximized(false);
        Scene scene = new Scene(decorator);
        final ObservableList<String> stylesheets = scene.getStylesheets();
        stylesheets.addAll(ControlPanelController.class.getResource("/css/jfoenix-fonts.css").toExternalForm(),
                ControlPanelController.class.getResource("/css/jfoenix-design.css").toExternalForm(),
                ControlPanelController.class.getResource("/css/login.css").toExternalForm());

        primaryStage.setMinWidth(MIN_WIDTH);
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setFullScreen(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}