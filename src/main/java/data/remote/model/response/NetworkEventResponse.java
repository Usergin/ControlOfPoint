package data.remote.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import data.remote.model.information.NetworkEvent;

import java.util.List;

/**
 * Created by OldMan on 25.06.2017.
 */
public class NetworkEventResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private List<NetworkEvent> data;

    public NetworkEventResponse(int code, List<NetworkEvent> data) {
        super(code);
        this.data = data;
    }

    public List<NetworkEvent> getData() {
        return data;
    }

    public void setData(List<NetworkEvent> data) {
        this.data = data;
    }
}
