package gui.fragment_controllers;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jfoenix.controls.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author danml
 */
public class DashboardController implements Initializable {
    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger hamburger;
    private Label lblDash;
//    @FXML
//    private StackPane stackPane;
//
//    @FXML
//    private AnchorPane holderPane;
//    @FXML
//    private AnchorPane sideAnchor;
//    @FXML
//    private Label lblMenu;
//    @FXML
//    private JFXToolbar toolBar;
//    @FXML
//    private HBox toolBarRight;
//    @FXML
//    private VBox overflowContainer;
//    @FXML
//    private ToggleButton menuHome;
//    @FXML
//    private ToggleButton menuAdd;
//    @FXML
//    private ToggleButton menuList;
//    @FXML
//    private ToggleButton menuLogg;
//
//    private static StackPane maps, deviceInfo;
//
//    private AnchorPane list;
//    @FXML
//    private JFXButton btnLogOut;
//    @FXML
//    private JFXButton btnExit;
//    @FXML
//    private JFXButton btnHome;
//    @FXML
//    private JFXButton btnOpenMap;
//    @FXML
//    private JFXButton btnView;
//    @FXML
//    private JFXButton btnLogout;
//    @FXML
//    private JFXButton btnClose;

    JFXRippler fXRippler, fXRippler2;

    public static StackPane rootP;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        fXRippler = new JFXRippler(lblDash);
//        fXRippler2 = new JFXRippler(lblMenu);
//        fXRippler2.setMaskType((JFXRippler.RipplerMask.RECT));
//        sideAnchor.getChildren().add(fXRippler);
//        toolBarRight.getChildren().add(fXRippler2);
        openMenus();
        createPages();
        try {
            VBox box = FXMLLoader.load(getClass().getResource("fxml/menu.fxml"));
            drawer.setSidePane(box);
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
        transition.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED,(e)->{
            transition.setRate(transition.getRate()*-1);
            transition.play();

            if(drawer.isShown())
            {
                drawer.close();
            }else
                drawer.open();
        });

    }

    private void openMenus() {
        JFXPopup popup = new JFXPopup();
//        popup.setContent(overflowContainer);
//        popup.setPopupContent(stackPane);
//        popup.setSource(lblMenu);
//        lblMenu.setOnMouseClicked((MouseEvent e) -> {
//            popup.show(fXRippler, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT, -10, 40);
//        });

    }

    //Set selected node to a content holder
    public void setNode(Node node) {
//        holderPane.getChildren().clear();
//        holderPane.getChildren().add((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    //Load all fxml files to a cahce for swapping
    private void createPages() {
//        try {
//            maps = FXMLLoader.load(getClass().getResource("/fxml/fragment_controllers.fxml"));
//
////            maps = FXMLLoader.load(getClass().getResource("/modules/Overview.fxml"));
////            list = FXMLLoader.load(getClass().getResource("/modules/Profile.fxml"));
//            deviceInfo = FXMLLoader.load(getClass().getResource("/fxml/device_info.fxml"));
//
//            //set up default node on page load
//            setNode(maps);
//        } catch (IOException ex) {
//            Logger.getLogger(gui.fragment_controllers.DashboardController.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

//    @FXML
//    private void openMap(ActionEvent event) {
//        setNode(maps);
//    }
//
//    @FXML
//    private void openDeviceInfo(ActionEvent event) {
//        setNode(deviceInfo);
//    }
//
//    @FXML
//    private void openListStudent(ActionEvent event) {
//        setNode(list);
//    }

    @FXML
    private void logOff(ActionEvent event) {

    }

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

}
