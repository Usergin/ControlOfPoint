package gui.control_panel;

/**
 * Created by OldMan on 23.06.2017.
 */
public interface ControlPanelPresenter {
    void setControlPanelView(ControlPanelView controlPanelView);
    void onNewDeviceList();
    void onLocalDeviceList();
    void onDeviceById(int id);

}
