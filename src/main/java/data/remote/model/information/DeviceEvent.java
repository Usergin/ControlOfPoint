package data.remote.model.information;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by OldMan on 25.06.2017.
 */
public class DeviceEvent extends BaseInfo{
    @SerializedName("status")
    @Expose
    private String status;

    public DeviceEvent() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
