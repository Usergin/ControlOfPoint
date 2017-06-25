package data.remote.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import data.remote.model.information.DeviceEvent;

import java.util.List;

/**
 * Created by OldMan on 25.06.2017.
 */
public class DeviceEventResponse extends BaseResponse {
    @SerializedName("info")
    @Expose
    private List<DeviceEvent> data;

    public DeviceEventResponse(int code, List<DeviceEvent> data) {
        super(code);
        this.data = data;
    }

    public List<DeviceEvent> getData() {
        return data;
    }

    public void setData(List<DeviceEvent> data) {
        this.data = data;
    }
}
