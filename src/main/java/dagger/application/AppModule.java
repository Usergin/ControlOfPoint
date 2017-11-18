package dagger.application;

import dagger.Module;
import dagger.Provides;
import data.local.parser.Parser;
import data.local.parser.ParserImpl;
import gui.fragment_controllers.SettingsController;
import gui.fragment_controllers.device_info.DeviceInfoView;
import gui.login.LoginView;
import io.datafx.controller.flow.context.ViewFlowContext;
import main.ControlOfPointMain;

/**
 * Created by OldMan on 18.06.2017.
 */

@Module(library = true, injects = {ControlOfPointMain.class, LoginView.class,
        DeviceInfoView.class, SettingsController.class}, complete = false)
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
