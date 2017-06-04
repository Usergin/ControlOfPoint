import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by OldMan on 04.06.2017.
 */
public class MenuController  implements Initializable {
    @FXML
    public JFXListView listDevice;
    @FXML
    private JFXButton btnMaps;
    @FXML
    private JFXButton b2;
//    @FXML
//    private JFXButton b3;
    @FXML
    private JFXButton exit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void changeColor(ActionEvent event) {
//        JFXButton btn = (JFXButton) event.getSource();
//        System.out.println(btn.getText());
//        switch(btn.getText())
//        {
//            case "Color 1":DashboardController.setNode();rootP.setStyle("-fx-background-color:#00FF00");
//                break;
//            case "Color 2":DashboardController.rootP.setStyle("-fx-background-color:#0000FF");
//                break;
////            case "Color 3":FXMLDocumentController.rootP.setStyle("-fx-background-color:#FF0000");
////                break;
//        }
    }

    @FXML
    private void setMapFragment(ActionEvent event) throws IOException {
        StackPane maps = FXMLLoader.load(getClass().getResource("/fxml/map.fxml"));
//        DashboardController.setNode(maps);
//        dashboardController
    }

    @FXML
    private void setDeviceInfoFragment(ActionEvent event) {
//        DashboardController.openDeviceInfo(event);
    }

    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }

}