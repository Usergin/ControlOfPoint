package gui.menu;

import com.jfoenix.controls.JFXListView;
import gui.control_panel.ControlPanelView;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Platform;
import javafx.fxml.FXML;
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
    private ControlPanelView controlPanel;

    @FXML
    private void submit() {
        int menuIndex = toolbarPopupList.getSelectionModel().getSelectedIndex();
        LOG.info("submit " + toolbarPopupList.getSelectionModel().getSelectedIndex());
        if (menuIndex == 3) {
            LOG.info("exit");
            Platform.exit();
        } else if (menuIndex == 2) {
            LOG.info("logout");
        } else if (menuIndex == 1) {
            LOG.info("about");
        } else if (menuIndex == 0) {
            if (controlPanel != null)
                controlPanel.onUpdateDeviceList();
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

    public void setControlPanelContext(ControlPanelView panelContext) {
        this.controlPanel = panelContext;
    }

}
