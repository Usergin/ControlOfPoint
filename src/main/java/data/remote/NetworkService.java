package data.remote;

import data.remote.model.request.Authentication;
import data.remote.model.response.DeviceListResponse;
import data.remote.model.response.DeviceResponse;
import data.remote.model.response.UserResponse;
import io.reactivex.Single;
import main.ControlOfPointMain;
import org.apache.log4j.Logger;

import javax.inject.Inject;

/**
 * Created by OldMan on 18.06.2017.
 */
public class NetworkService {

    @Inject
    ServerApi serverApi;
    private static final Logger LOG = Logger.getLogger(ControlOfPointMain.class);

    public NetworkService(ServerApi serverApi) {
        this.serverApi = serverApi;
    }

    /**
     * Get user - login&password
     */
    public Single<UserResponse> getUser(Authentication authentication) {
        LOG.info(authentication.getLogin() + authentication.getPassword());
        return serverApi.getUser(authentication);
    }


    /**
     * Get user - login&password
     */
    public Single<DeviceListResponse> getDevices() {
        LOG.info("getDevices");
        return serverApi.getDevices();
    }

    public Single<DeviceResponse> getDeviceById(int id) {
        LOG.info("getDevices");
        return serverApi.getDeviceById(id);
    }
}
