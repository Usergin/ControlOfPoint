package data.remote.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import data.model.Device;

import java.util.List;

/**
 * Created by oldman on 21.06.17.
 */
public class DeviceListResponse extends BaseResponse {
    @SerializedName("deviceList")
    @Expose
    private List<Device> devices;

    public DeviceListResponse(int code, List<Device> devices) {
        super(code);
        this.devices = devices;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}
