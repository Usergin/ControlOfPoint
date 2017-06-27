package gui.menu;

import com.jfoenix.controls.JFXListView;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * Created by OldMan on 12.06.2017.
 */
public class MainMenuController {
    @FXMLViewFlowContext
    private ViewFlowContext context;
    @FXML
    private JFXListView<?> toolbarPopupList;

    private static final Logger LOG = Logger.getLogger(MainMenuController.class);

    @FXML
    private void submit() {
        LOG.info("submit " + toolbarPopupList.getSelectionModel().getSelectedIndex());
        if (toolbarPopupList.getSelectionModel().getSelectedIndex() == 2) {
            LOG.info("exit");
            Platform.exit();
        } else if (toolbarPopupList.getSelectionModel().getSelectedIndex() == 1) {
            LOG.info("logout");
        } else if (toolbarPopupList.getSelectionModel().getSelectedIndex() == 0) {
            LOG.info("about");
        }

    }

    //    @FXML
    private void aboutProgrammes() {
        if (toolbarPopupList.getSelectionModel().getSelectedIndex() == 2) {
            Platform.exit();
        }
    }

    @PostConstruct
    public void init() {
        Objects.requireNonNull(context, "context");
        FlowHandler contentFlowHandler = (FlowHandler) context.getRegisteredObject("ContentFlowHandler");

        Flow contentFlow = (Flow) context.getRegisteredObject("ContentFlow");
//        bindNodeToController(logout, LoginController.class, contentFlow, contentFlowHandler);
//        bindNodeToController(aboutProgrammes, MainMapController.class, contentFlow, contentFlowHandler);
    }

    private void bindNodeToController(Node node, Class<?> controllerClass, Flow flow, FlowHandler flowHandler) {
        flow.withGlobalLink(node.getId(), controllerClass);
    }
}
