package data.remote.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import data.model.information.InstallApp;

import java.util.List;

/**
 * Created by oldman on 26.06.17.
 */
public class InstallAppResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private List<InstallApp> data;

    public InstallAppResponse(int code, List<InstallApp> data) {
        super(code);
        this.data = data;
    }

    public List<InstallApp> getData() {
        return data;
    }

    public void setData(List<InstallApp> data) {
        this.data = data;
    }
}
