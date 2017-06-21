package business.control_panel;

import data.model.Device;
import io.reactivex.Single;

import java.util.List;

/**
 * Created by oldman on 20.06.17.
 */
public interface ControlPanelInteractor {
    Single<List<Device>> getNewDeviceList();
    Single<List<Device>> getLocalDeviceList();
    Single<Device> getDeviceById(int id);
}
