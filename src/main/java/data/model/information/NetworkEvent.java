package data.model.information;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OldMan on 25.06.2017.
 */
public class NetworkEvent extends BaseInfo {
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("ip")
    @Expose
    private String ip;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}