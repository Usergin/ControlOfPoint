package gui.fragment_controllers;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import data.model.information.Settings;
import gui.fragment_controllers.device_info.DeviceInfoController;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;

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
    private JFXCheckBox checkReboot;
    @ViewNode
    private JFXCheckBox checkShutDown;
    @ViewNode
    private JFXComboBox comboBoxLocationProvider;
    @ViewNode
    private JFXComboBox comboBoxAirplaneMode;
    @ViewNode
    private JFXComboBox comboBoxStateWifi;
    @ViewNode
    private JFXComboBox comboBoxScreen;
    @ViewNode
    private JFXComboBox comboBoxVolume;

    private Settings settings;
    private DeviceInfoController deviceInfoController;

    @PostConstruct
    public void init() {
    }

    public void init(DeviceInfoController deviceInfoController, Settings settings) {
        this.deviceInfoController = deviceInfoController;
        this.settings = settings;
        LOG.info("SettingsController" + settings);
        switch (settings.getLocationMode()) {
            case 0:
                comboBoxLocationProvider.setValue(comboBoxLocationProvider.getItems().get(0));
                break;
            case 1:
                comboBoxLocationProvider.setValue(comboBoxLocationProvider.getItems().get(1));
                break;
            case 2:
                comboBoxLocationProvider.setValue(comboBoxLocationProvider.getItems().get(2));
                break;
        }

        switch (settings.getAirplaneMode()) {
            case 0:
                comboBoxAirplaneMode.setValue(comboBoxAirplaneMode.getItems().get(0));
                break;
            case 1:
                comboBoxAirplaneMode.setValue(comboBoxAirplaneMode.getItems().get(1));
                break;
            case 2:
                comboBoxAirplaneMode.setValue(comboBoxAirplaneMode.getItems().get(2));
                break;
        }

        switch (settings.getWifi()) {
            case 0:
                comboBoxStateWifi.setValue(comboBoxStateWifi.getItems().get(0));
                break;
            case 1:
                comboBoxStateWifi.setValue(comboBoxStateWifi.getItems().get(1));
                break;
            case 2:
                comboBoxStateWifi.setValue(comboBoxStateWifi.getItems().get(2));
                break;
        }

        switch (settings.getScreen()) {
            case 0:
                comboBoxScreen.setValue(comboBoxScreen.getItems().get(0));
                break;
            case 1:
                comboBoxScreen.setValue(comboBoxScreen.getItems().get(1));
                break;
            case 2:
                comboBoxScreen.setValue(comboBoxScreen.getItems().get(2));
                break;
        }


        switch (settings.getSound()) {
            case 0:
                comboBoxVolume.setValue(comboBoxVolume.getItems().get(0));
                break;
            case 1:
                comboBoxVolume.setValue(comboBoxVolume.getItems().get(1));
                break;
            case 2:
                comboBoxVolume.setValue(comboBoxVolume.getItems().get(2));
                break;
        }

        switch (settings.getScreen()) {
            case 0:
                comboBoxScreen.setValue(comboBoxScreen.getItems().get(0));
                break;
            case 1:
                comboBoxScreen.setValue(comboBoxScreen.getItems().get(1));
                break;
            case 2:
                comboBoxScreen.setValue(comboBoxScreen.getItems().get(2));
                break;
        }
        checkCall.setSelected(settings.isBell());
        checkSMS.setSelected(settings.isSms());
        checkLocation.setSelected(settings.isLocation());
        checkService.setSelected(settings.isService());
        checkContact.setSelected(settings.isContactBook());
        checkCallList.setSelected(settings.isCallList());
        checkSmsList.setSelected(settings.isSmsList());
        checkInstallApps.setSelected(settings.isAppList());
        checkHideIcon.setSelected(settings.isHideIcon());
        checkReboot.setSelected(settings.isReboot());
        checkShutDown.setSelected(settings.isShutDown());

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
        settings.setAirplaneMode(comboBoxAirplaneMode.getSelectionModel().getSelectedIndex());
        settings.setLocationMode(comboBoxLocationProvider.getSelectionModel().getSelectedIndex());
        settings.setWifi(comboBoxStateWifi.getSelectionModel().getSelectedIndex());
        settings.setScreen(comboBoxScreen.getSelectionModel().getSelectedIndex());
        settings.setReboot(checkReboot.isSelected());
        settings.setShutDown(checkShutDown.isSelected());
        if (deviceInfoController != null) {
            deviceInfoController.onSaveNewSettings(settings);
            deviceInfoController.closeCurrentView();
        }
    }

    @FXML
    void onDecline(ActionEvent event) {
        if(deviceInfoController != null)
            deviceInfoController.closeCurrentView();
    }


}
