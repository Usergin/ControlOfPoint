package data.remote;

import data.remote.model.request.Authentication;
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
     * Create trip - clientServer_userWantsToOrderTaxi_agree
     */
    public Single<UserResponse> getUser(Authentication authentication) {
        LOG.info(authentication.getLogin() + authentication.getPassword());
        return serverApi.getUser(authentication);
    }

}
