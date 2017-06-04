import javafx.application.Application;
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

    @Override
    public void start(Stage primaryStage) throws Exception {
        LOG.info("Application started");
        Parent root = FXMLLoader.load(getClass().getResource("fxml/login.fxml"));
        Scene scene = new Scene(root);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("drawables/logo-vs-rf.png")));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}