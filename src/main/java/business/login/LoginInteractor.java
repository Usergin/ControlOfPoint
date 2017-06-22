package business.login;

import data.remote.model.request.Authentication;
import data.remote.model.response.UserResponse;
import io.reactivex.Single;

/**
 * Created by OldMan on 18.06.2017.
 */
public interface LoginInteractor {
   void onAuthentication(Authentication authentication);
}
