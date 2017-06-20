package main;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.svg.SVGGlyphLoader;
import dagger.Injector;
import dagger.application.AppModule;
import gui.fragment_controllers.ControlPanelController;
import gui.login.LoginController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;

/**
 * Created by oldman on 02.06.17.
 */
@Singleton
public class ControlOfPointMain extends Application {
    private static final Logger LOG = Logger.getLogger(ControlOfPointMain.class);
    @FXMLViewFlowContext
    @Inject
    ViewFlowContext viewFlowContext;
    private final int MIN_WIDTH = 850;
    private final int MIN_HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {
        LOG.info("Application started");
        Injector.inject(this, Arrays.asList(new AppModule()));
        new Thread(() -> {
            try {
                //he just loaded some svg from a font file
                SVGGlyphLoader.loadGlyphsFont(ControlOfPointMain.class.getResourceAsStream("/fonts/icomoon.svg"), "icomoon.svg");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }).start();
        LOG.info("Application started" + viewFlowContext);

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