package dagger.application;

import business.control_panel.ControlPanelInteractorImpl;
import business.login.LoginInteractorImpl;
import dagger.Module;
import dagger.Provides;
import data.local.parser.Parser;
import data.local.parser.ParserImpl;
import gui.control_panel.ControlPanelView;
import gui.fragment_controllers.device_info.DeviceInfoView;
import gui.login.LoginController;
import gui.login.LoginView;
import io.datafx.controller.flow.context.ViewFlowContext;
import main.ControlOfPointMain;

/**
 * Created by OldMan on 18.06.2017.
 */

@Module(library = true, injects = {ControlOfPointMain.class, LoginView.class,
        ControlPanelView.class, DeviceInfoView.class}, complete = false)
public class AppModule {
    //    @Provides
//    FXMLLoader provideFxmlLoader(){
//        return new FXMLLoader();
//    }
    @Provides
    ViewFlowContext provideViewFlowContext() {
        return new ViewFlowContext();
    }

    @Provides
    Parser provideParser() {
        return new ParserImpl();
    }
}
