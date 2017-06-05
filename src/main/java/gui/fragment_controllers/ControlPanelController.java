package gui.fragment_controllers;

import com.jfoenix.controls.*;
import datafx.ExtendedAnimatedFlowContainer;
import gui.menu.MenuController;
import gui.menu.SideMenuController;
import gui.ui_components.ButtonController;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import javax.annotation.PostConstruct;

import static io.datafx.controller.flow.container.ContainerAnimations.SWIPE_LEFT;

/**
 * Created by OldMan on 04.06.2017.
 */

@ViewController(value = "/fxml/control_panel.fxml", title = "Material Design Example")
public class ControlPanelController {

    @FXMLViewFlowContext
    private ViewFlowContext context;

    @FXML
    private StackPane root;

    @FXML
    private StackPane titleBurgerContainer;
    @FXML
    private JFXHamburger titleBurger;

    @FXML
    private StackPane optionsBurger;
    @FXML
    private JFXRippler optionsRippler;
//    @FXML
//    private JFXDrawer drawer;

    private JFXPopup toolbarPopup;

    /**
     * init fxml when loaded.
     */
    @PostConstruct
    public void init() throws Exception {
        // init the title hamburger icon
//        drawer.setOnDrawerOpening(e -> {
//            final Transition animation = titleBurger.getAnimation();
//            animation.setRate(1);
//            animation.play();
//        });
//        drawer.setOnDrawerClosing(e -> {
//            final Transition animation = titleBurger.getAnimation();
//            animation.setRate(-1);
//            animation.play();
//        });
//        titleBurgerContainer.setOnMouseClicked(e -> {
//            if (drawer.isHidden() || drawer.isHidding()) {
//                drawer.open();
//            } else {
//                drawer.close();
//            }
//        });

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ui/MainPopup.fxml"));
        loader.setController(new InputController());
        toolbarPopup = new JFXPopup(loader.load());

        optionsBurger.setOnMouseClicked(e -> toolbarPopup.show(optionsBurger,
                JFXPopup.PopupVPosition.TOP,
                JFXPopup.PopupHPosition.RIGHT,
                -12,
                15));

        // create the inner flow and content
        context = new ViewFlowContext();
        // set the default controller
        Flow innerFlow = new Flow(ButtonController.class);

        final FlowHandler flowHandler = innerFlow.createHandler(context);
        context.register("ContentFlowHandler", flowHandler);
        context.register("ContentFlow", innerFlow);
        final Duration containerAnimationDuration = Duration.millis(320);
//        drawer.setContent(flowHandler.start(new ExtendedAnimatedFlowContainer(containerAnimationDuration, SWIPE_LEFT)));
//        context.register("ContentPane", drawer.getContent().get(0));

        // side controller will add links to the content flow
        Flow sideMenuFlow = new Flow(SideMenuController.class);
        final FlowHandler sideMenuFlowHandler = sideMenuFlow.createHandler(context);
//        drawer.setSidePane(sideMenuFlowHandler.start(new ExtendedAnimatedFlowContainer(containerAnimationDuration,
//                SWIPE_LEFT)));
    }

public static final class InputController {
    @FXML
    private JFXListView<?> toolbarPopupList;

    // close application
    @FXML
    private void submit() {
        if (toolbarPopupList.getSelectionModel().getSelectedIndex() == 1) {
            Platform.exit();
        }
    }
}
}
