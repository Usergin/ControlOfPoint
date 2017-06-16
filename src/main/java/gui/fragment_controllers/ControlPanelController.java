package gui.fragment_controllers;

import com.jfoenix.controls.*;
import gui.menu.MainMenuController;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.container.AnimatedFlowContainer;
import io.datafx.controller.flow.container.ContainerAnimations;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;

import static io.datafx.controller.flow.container.ContainerAnimations.SWIPE_LEFT;

/**
 * Created by OldMan on 04.06.2017.
 */

@ViewController(value = "/fxml/control_panel.fxml")
public class ControlPanelController {

    @FXMLViewFlowContext
    private ViewFlowContext context;

    @FXML
    private StackPane centerPane;

//    @FXML
//    private StackPane titleBurgerContainer;
    @FXML
    private JFXHamburger device_menu;

    @FXML
    private StackPane optionsBurger;
    @FXML
    private JFXRippler ripple_device_menu;
//    @FXML
//    private JFXDrawer drawer;

    private JFXPopup toolbarPopup;
    private JFXPopup popup;
    private static final Logger LOG = Logger.getLogger(ControlPanelController.class);

    /**
     * init fxml when loaded.
     */
    @PostConstruct
    public void init() throws Exception {

        JFXListView<Label> list = new JFXListView<>();
        for (int i = 1; i < 50; i++) {
            list.getItems().add(new Label("Item" + i));
        }
        JFXPopup popup = new JFXPopup(list);
        ripple_device_menu.setOnMouseClicked(e -> popup.show(device_menu, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT));


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ui/MainMenu.fxml"));
        loader.setController(new MainMenuController());
        toolbarPopup = new JFXPopup(loader.load());

        optionsBurger.setOnMouseClicked(e -> toolbarPopup.show(optionsBurger,
                JFXPopup.PopupVPosition.TOP,
                JFXPopup.PopupHPosition.RIGHT,
                -12,
                15));

        // create the inner flow and content
        context = new ViewFlowContext();
        // set the default controller
        Flow innerFlow = new Flow(MapController.class);

        final FlowHandler flowHandler = innerFlow.createHandler(context);
        context.register("ContentFlowHandler", flowHandler);
        context.register("ContentFlow", innerFlow);
        centerPane.getChildren().add(flowHandler.start(new AnimatedFlowContainer(Duration.millis(320), ContainerAnimations.ZOOM_IN)));

        final Duration containerAnimationDuration = Duration.millis(320);

    }
    @FXML
    private void update() {
        LOG.info("submit update");

    }


}
