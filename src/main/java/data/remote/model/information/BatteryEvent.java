package data.remote.model.information;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by OldMan on 25.06.2017.
 */
public class BatteryEvent extends BaseInfo{
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("battery_status")
    @Expose
    private String batteryStatus;
    @SerializedName("type_charging")
    @Expose
    private String typeCharging;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(String battery_status) {
        this.batteryStatus = battery_status;
    }

    public String getTypeCharging() {
        return typeCharging;
    }

    public void setTypeCharging(String type_charging) {
        this.typeCharging = type_charging;
    }
}

