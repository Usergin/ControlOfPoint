package data.remote.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OldMan on 18.06.2017.
 */
public class BaseResponse {
    @SerializedName("code")
    @Expose
    int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public BaseResponse(int code) {
        this.code = code;
    }
}
