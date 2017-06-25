package data.remote.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import data.remote.model.information.DeviceInfo;

/**
 * Created by OldMan on 25.06.2017.
 */
public class DeviceInfoResponse extends BaseResponse {
    @SerializedName("info")
    @Expose
    private DeviceInfo data;

    public DeviceInfoResponse(int code, DeviceInfo deviceInfo) {
        super(code);
        this.data = deviceInfo;
    }

    public DeviceInfo getData() {
        return data;
    }

    public void setData(DeviceInfo data) {
        this.data = data;
    }
}