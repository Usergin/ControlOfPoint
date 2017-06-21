package data.remote.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import data.model.Device;

/**
 * Created by oldman on 21.06.17.
 */
public class DeviceResponse extends BaseResponse {

    @SerializedName("device")
    @Expose
    private Device device;

    public DeviceResponse(int code, Device devices) {
        super(code);
        this.device = device;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
