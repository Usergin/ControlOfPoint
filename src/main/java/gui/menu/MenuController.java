package gui.menu;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import gui.fragment_controllers.MapController;
import gui.ui_components.ButtonController;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Created by OldMan on 04.06.2017.
 */
@ViewController(value = "/fxml/menu.fxml", title = "Material Design Example")
public class MenuController {
    @FXMLViewFlowContext
    private ViewFlowContext context;

    @FXML
    public JFXListView listDevice;
    @FXML
    @ActionTrigger("map")
    private JFXButton map;
    @FXML
    @ActionTrigger("button")
    private JFXButton button;
//    @FXML
//    private JFXButton b3;
    @FXML
    private JFXButton exit;


//    @FXML
//    private void setMapFragment(ActionEvent event) throws IOException {
//        StackPane maps = FXMLLoader.load(getClass().getResource("/fxml/map.fxml"));
////        gui.fragment_controllers.DashboardController.setNode(maps);
////        dashboardController
//    }

//    @FXML
//    private void setDeviceInfoFragment(ActionEvent event) {
////        gui.fragment_controllers.DashboardController.openDeviceInfo(event);
//        Flow contentFlow = (Flow) context.getRegisteredObject("ContentFlow");
//        bindNodeToController(button, ButtonController.class, contentFlow, contentFlowHandler);
//
//    }

    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }

    @PostConstruct
    public void init() {
        Objects.requireNonNull(context, "context");
        FlowHandler contentFlowHandler = (FlowHandler) context.getRegisteredObject("ContentFlowHandler");

        Flow contentFlow = (Flow) context.getRegisteredObject("ContentFlow");
        bindNodeToController(button, ButtonController.class, contentFlow, contentFlowHandler);
        bindNodeToController(map, MapController.class, contentFlow, contentFlowHandler);

    }

    private void bindNodeToController(Node node, Class<?> controllerClass, Flow flow, FlowHandler flowHandler) {
        flow.withGlobalLink(node.getId(), controllerClass);
    }
}