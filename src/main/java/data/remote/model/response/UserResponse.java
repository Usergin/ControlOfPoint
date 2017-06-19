package data.remote.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import data.model.User;

/**
 * Created by OldMan on 18.06.2017.
 */
public class UserResponse extends BaseResponse{
    @SerializedName("info")
    @Expose
    private User user;

    public UserResponse(int code, User user) {
        super(code);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
